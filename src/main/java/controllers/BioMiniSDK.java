package controllers;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.suprema.UFE33.UFMatcherClass;
import com.suprema.UFE33.UFScannerClass;
import models.Device;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Created by Користувач on 19.06.2017.
 */
public class BioMiniSDK {

    private int nInitFlag;
    private int nCaptureFlag;
    private UFScannerClass libScanner = null;
    private UFMatcherClass libMatcher = null;

    private int nScannerNumber;
    private Pointer hMatcher;
    private int nSecurityLevel;
    private int nFastMode;

    private DefaultListModel listModel;
    private DefaultListModel listLogModel;
    private int nLogListCnt;

    private byte[][] byteTemplateArray = null;
    private int[] intTemplateSizeArray = null;
    public final int MAX_TEMPLATE_SIZE = 384;
    private PointerByReference refTemplateArray = null;

    public int nC = 0;

    private HashSet<Device> devices;
    private Device currentDevice;
    private Logger logger;
    private FileHandler fh;

    UFScannerClass.UFS_SCANNER_PROC pScanProc = new UFScannerClass.UFS_SCANNER_PROC() {
        public int callback(String szScannerId, int bSensorOn, PointerByReference pParam) //interface ..must be implemeent

        {
            nC++;

            System.out.println(nC + "==========================================");  //
            System.out.println("==>ScanProc calle scannerID:" + szScannerId);  //
            System.out.println("sensoron value is " + bSensorOn);
            System.out.println("void * pParam  value is " + pParam.getValue());
            System.out.println(nC + "==========================================");  //

            updateScannerList();

            return 1;
        }
    };

    BioMiniSDK() {
        listModel = new DefaultListModel();
        listLogModel = new DefaultListModel();
        devices = new HashSet<>();
        initLoger();
        init();
    }

    private void initLoger(){
        logger = Logger.getLogger("BioMiniLog");
        try {
            String path = System.getProperty("user.home")+"/BioMiniLogFile.log";
            File file = new File(path);
            file.createNewFile();
            fh = new FileHandler(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public void init() {

        if (nInitFlag != 0) {
            logger.info("already init..");
            return;
        }

        nCaptureFlag = 0;

        try {
            libScanner = (UFScannerClass) Native.loadLibrary("UFScanner", UFScannerClass.class);
            libMatcher = (UFMatcherClass) Native.loadLibrary("UFMatcher", UFMatcherClass.class);
        } catch (Exception ex) {
            logger.info("loadLlibrary : UFScanner,UFMatcher fail!!");
            logger.info("loadLlibrary : UFScanner,UFMatcher fail!!");
            return;
        }
        int nRes = 0;

        nRes = libScanner.UFS_Init();

        if (nRes == 0) {

            logger.info("UFS_Init() success!!");

            nInitFlag = 1;

            logger.info("Scanner Init success!!");

            nRes = testCallScanProcCallback();

            if (nRes == 0) {

                logger.info("==>UFS_SetScannerCallback pScanProc ...");

                IntByReference refNumber = new IntByReference();
                nRes = libScanner.UFS_GetScannerNumber(refNumber);

                if (nRes == 0) {
                    nScannerNumber = refNumber.getValue();
                    logger.info("UFS_GetScannerNumber() scanner number :" + nScannerNumber);

                    PointerByReference refMatcher = new PointerByReference();

                    nRes = libMatcher.UFM_Create(refMatcher);

                    if (nRes == 0) {

                        updateScannerList(); //list upate ==> getcurrentscannerhandle�� list�� ������//
                        logger.info("after upadtelist");
                        initVariable(1);
                        logger.info("after initVariable");
                        initArray(100, 1024); //array size,template size

                        hMatcher = refMatcher.getValue();

                        IntByReference refValue = new IntByReference();
                        IntByReference refFastMode = new IntByReference();

                        //security level (1~7)

                        nRes = libMatcher.UFM_GetParameter(hMatcher, 302, refValue); //302 : security level :UFM_

                        if (nRes == 0) {
                            nSecurityLevel = refValue.getValue();//
                            logger.info("get security level,302(security) value is " + refValue.getValue());
                        } else {
                            logger.info("get security level fail! code: " + nRes);
                            logger.info("get security level fail! code: " + nRes);
                        }

                        //fast mode
                        nRes = libMatcher.UFM_SetParameter(hMatcher, libMatcher.UFM_PARAM_FAST_MODE, refFastMode);
                        if (nRes == 0) {
                            nFastMode = refFastMode.getValue();
                            logger.info("get fastmode,301(fastmode) value is " + refFastMode.getValue());
                        } else {
                            logger.info("get fastmode value fail! code: " + nRes);
                            logger.info("get fastmode value fail! code: " + nRes);
                        }

                        int nSelectedIdx = 0;

                        if (hMatcher != null) {
                            switch (nSelectedIdx) {
                                case 0:
                                    nRes = libMatcher.UFM_SetTemplateType(hMatcher, libMatcher.UFM_TEMPLATE_TYPE_SUPREMA); //2001 Suprema type
                                    break;
                                case 1:
                                    nRes = libMatcher.UFM_SetTemplateType(hMatcher, libMatcher.UFM_TEMPLATE_TYPE_ISO19794_2); //2002 iso type
                                    break;
                                case 2:
                                    nRes = libMatcher.UFM_SetTemplateType(hMatcher, libMatcher.UFM_TEMPLATE_TYPE_ANSI378); //2003 ansi type
                                    break;
                            }
                        }
                    } else {
                        logger.info("UFM_Create fail!! code :" + nRes);
                        return;
                    }
                } else {
                    logger.info("GetScannerNumber fail!! code :" + nRes);
                    logger.info("GetScannerNumber fail!! code :" + nRes);
                    return;
                }
            } else {
                logger.info("UFS_SetScannerCallback() fail,code :" + nRes);
            }
        }

        if (nRes != 0) {
            logger.info("Init() fail!!");
            logger.info("Init fail!! return code:" + nRes);
            logger.info("Scanner Init fail!!");
        }
    }

    public int testCallScanProcCallback() {
        int nRes = 0;

        PointerByReference refParam = new PointerByReference();
        refParam.getPointer().setInt(0, 1);

        nRes = libScanner.UFS_SetScannerCallback(pScanProc, refParam);
        if (nRes == 0) {
            logger.info("==>UFS_SetScannerCallback pScanProc ..." + pScanProc);
        }
        return nRes;
    }

    public void updateScannerList() {

        int nSelectedIdx = 0;
        int nRes = 0;
        PointerByReference hTempScanner = new PointerByReference();
        Pointer hScanner = null;
        IntByReference refType = new IntByReference();

        byte[] bScannerId = new byte[512];

        String szListLog = null;
        int nNumber = 0;

        logger.info("==update Scanner list==");

        IntByReference refNumber = new IntByReference();

        nRes = libScanner.UFS_GetScannerNumber(refNumber);

        if (nRes == 0) {
            logger.info("UFS_GetScannerNumber() res value is " + nRes);
            nNumber = refNumber.getValue();
        } else {
            return;
        }

        if (nNumber <= 0) {
            if (listModel.getSize() > 0) {
                listModel.clear();
                logger.info("list clear");
            }
            return;
        }


        if (listModel.getSize() > 0) {
            listModel.clear();
            logger.info("list clear");
        }

        for (int j = 0; j < nNumber; j++) {
            nRes = libScanner.UFS_GetScannerHandle(j, hTempScanner);
            hScanner = null;
            hScanner = hTempScanner.getValue();

            if (nRes == 0 && hScanner != null) {

                nRes = libScanner.UFS_GetScannerID(hScanner, bScannerId);

                nRes = libScanner.UFS_GetScannerType(hScanner, refType);

                szListLog = "ID : " + Native.toString(bScannerId) + " Type : " + refType.getValue();

                listModel.insertElementAt(szListLog, j);
                addDevice(hScanner,j);
            }
        }

    }

    public void initVariable(int nFlag) {

        if (nFlag == 1) { //UFS_Init\//
            nInitFlag = 1;
        } else {
            nInitFlag = 0;
        }

        nCaptureFlag = 0;

        nLogListCnt = 0;

        listLogModel.clear();

        Pointer hScanner = getCurrentScannerHandle();

        IntByReference pValue = new IntByReference();

        pValue.setValue(currentDevice.getTimeOut());

        int nRes = libScanner.UFS_SetParameter(hScanner, libScanner.UFS_PARAM_TIMEOUT, pValue);
        if (nRes == 0) {
            logger.info("Change timeout,201(timeout) value is " + pValue.getValue());
        } else {
            logger.info("Change timeout,change parameter value fail! code: " + nRes);
        }

        pValue.setValue(currentDevice.getBrightness());
        nRes = libScanner.UFS_SetParameter(hScanner, libScanner.UFS_PARAM_BRIGHTNESS, pValue);
        if (nRes == 0) {
            logger.info("Change brightness,202 value is " + pValue.getValue());
        } else {
            logger.info("Change brightness,change parameter value fail! code: " + nRes);
        }

        pValue.setValue(currentDevice.getDetectFake());
        nRes = libScanner.UFS_SetParameter(hScanner, libScanner.UFS_PARAM_DETECT_FAKE, pValue);
        if (nRes == 0) {
            logger.info("Change detect_fake,312(fake detect) value is " + pValue.getValue());
        } else {
            logger.info("Change detect_fake,change parameter value fail! code: " + nRes);
        }

        pValue.setValue(currentDevice.getSensivity());
        nRes = libScanner.UFS_SetParameter(hScanner, libScanner.UFS_PARAM_SENSITIVITY, pValue);
        if (nRes == 0) {
            logger.info("Change sensitivity,203 value is " + pValue.getValue());
        } else {
            logger.info("Change sensitivity,change parameter value fail! code: " + nRes);
        }

        nRes = libScanner.UFS_SetTemplateType(hScanner, currentDevice.getTemplateType()); //2001 Suprema type
        if (nRes == 0) {
            logger.info("Change Scan TemplateType:2001");
        } else {
            logger.info("Change Scan TemplateType,change parameter value fail! code: " + nRes);
        }
    }

    public void initArray(int nArrayCnt, int nMaxTemplateSize) {
        if (byteTemplateArray != null) {
            byteTemplateArray = null;

        }

        if (intTemplateSizeArray != null) {
            intTemplateSizeArray = null;

        }

        byteTemplateArray = new byte[nArrayCnt][MAX_TEMPLATE_SIZE];

        intTemplateSizeArray = new int[nArrayCnt];

        refTemplateArray = new PointerByReference();

    }

    public Pointer getCurrentScannerHandle() {
        Pointer hScanner = null;
        int nRes = 0;
        int nNumber = 0;

        PointerByReference refScanner = new PointerByReference();
        IntByReference refScannerNumber = new IntByReference();

        //success!!//
        nRes = libScanner.UFS_GetScannerNumber(refScannerNumber);

        if (nRes == 0) {
            nNumber = refScannerNumber.getValue();
            if (nNumber <= 0) {
                return null;
            }
        } else {
            return null;
        }

        int index = currentDevice.getId();
        if (index == -1) {
            index = 0;
        }

        nRes = libScanner.UFS_GetScannerHandle(index, refScanner);

        hScanner = refScanner.getValue();

        if (nRes == 0 && hScanner != null) {
            return hScanner;
        }
        return null;
    }

    public int captureSingle() {

        int nRes = 0;
        Pointer hScanner = null;

        System.out.println("call GetCurrentScannerHandle()");

        hScanner = getCurrentScannerHandle();

        if (hScanner != null) {

            System.out.println("GetScannerHandle return hScanner pointer: " + hScanner);

            System.out.println("get Scanner handle success pointer:" + hScanner);

        } else {

            System.out.println("GetScannerHandle fail!!");

            System.out.println("get Scanner handle fail!!");

            return -1;
        }

        // Clear capture buffer
        libScanner.UFS_ClearCaptureImageBuffer(hScanner);

        System.out.println("Start single image capturing");

        nRes = libScanner.UFS_CaptureSingleImage(hScanner);

        if (nRes == 0) {
            System.out.println("==>UFS_CaptureSingleImage return value is.." + nRes);

            nCaptureFlag = 1;

            drawCurrentFingerImage();

        } else {

            System.out.println("SingleImage fail!! code:" + nRes);

            byte[] refErr = new byte[512];

            nRes = libScanner.UFS_GetErrorString(nRes, refErr);
            if (nRes == 0) {
                System.out.println("==>UFS_GetErrorString err is " + Native.toString(refErr));
            }

            System.out.println("caputure single img fail!!");
        }

        return nRes;
    }

    public void drawCurrentFingerImage() {
        String img = getImgBase64();
        System.out.println(img);
    }

    public void saveImg(String path) {

        byte[] bTemplate = new byte[512];
        PointerByReference refError;
        IntByReference refTemplateSize = new IntByReference();
        IntByReference refTemplateQuality = new IntByReference();
        IntByReference refVerify = new IntByReference();
        Pointer hScanner = getCurrentScannerHandle();

        int nRes = libScanner.UFS_Extract(hScanner, bTemplate, refTemplateSize, refTemplateQuality);

        libScanner.UFS_SaveCaptureImageBufferToBMP(hScanner, path);

    }

    public String getImgBase64() {

        IntByReference refResolution = new IntByReference();
        IntByReference refHeight = new IntByReference();
        IntByReference refWidth = new IntByReference();
        Pointer hScanner = getCurrentScannerHandle();
        String encodedImg = null;

        int nRes = libScanner.UFS_GetCaptureImageBufferInfo(hScanner, refWidth, refHeight, refResolution);

        byte[] pImageData = new byte[refWidth.getValue() * refHeight.getValue()];

        libScanner.UFS_GetCaptureImageBuffer(hScanner, pImageData);

        BufferedImage buffImage = new BufferedImage(refWidth.getValue(), refHeight.getValue(), BufferedImage.TYPE_BYTE_GRAY);
        buffImage.getRaster().setDataElements(0, 0, refWidth.getValue(), refWidth.getValue(), pImageData);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffImage, "bmp", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            encodedImg = Base64.encodeBase64String(imageInByte);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedImg;
    }

    public HashSet<Device> getListModel() {
        return devices;
    }

    public void addDevice(Pointer hScanner, int id) {

        byte[] bScannerId = new byte[512];

        libScanner.UFS_GetScannerID(hScanner, bScannerId);
        String scanerName = Native.toString(bScannerId);

        Device device = new Device(id, scanerName);

        devices.add(device);

        this.currentDevice = device;

    }

    public void setCurrentDevice(Device device){
        this.currentDevice = device;
        initVariable(1);
    }

    public void test(){
        int ufs_res;
        Pointer hScanner = getCurrentScannerHandle();
        IntByReference bFingerOn = new IntByReference();

        while (true) {
            ufs_res = libScanner.UFS_IsFingerOn(hScanner, bFingerOn);
            if (bFingerOn.getValue() == 0) {
                System.out.println("finger is not placed");
            } else {
                System.out.println("placed");
                captureSingle();
                String dd = getImgBase64();
            }
        }
    }


}
