import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.suprema.UFE33.UFMatcherClass;
import com.suprema.UFE33.UFScannerClass;
import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

//import sun.misc.BASE64Encoder;

/**
 * Created by Користувач on 19.06.2017.
 */
public class BioMiniSDK {

    private int nInitFlag;
    private int nCaptureFlag;
    private UFScannerClass libScanner=null;
    private UFMatcherClass libMatcher=null;

    private int nScannerNumber;
    private Pointer hMatcher;
    private int nSecurityLevel;
    private int nFastMode;
    private DefaultListModel listModel;
    private DefaultListModel listLogModel;
    private int nLogListCnt;

    private byte[][] byteTemplateArray= null;
    private int[]   intTemplateSizeArray=null;
    public final int MAX_TEMPLATE_SIZE=384;
    private  PointerByReference refTemplateArray = null;

    public int nC=0;

    UFScannerClass.UFS_SCANNER_PROC pScanProc  = new UFScannerClass.UFS_SCANNER_PROC()
    {
        public int callback(String szScannerId,int bSensorOn,PointerByReference pParam) //interface ..must be implemeent

        {
            nC ++;

            System.out.println(nC+"==========================================");  //
            System.out.println("==>ScanProc calle scannerID:"+szScannerId);  //
            System.out.println("sensoron value is "+bSensorOn);
            System.out.println("void * pParam  value is "+pParam.getValue());
            System.out.println(nC+"==========================================");  //

            updateScannerList();

            return 1;
        }
    };

    BioMiniSDK(){
        listModel = new DefaultListModel();
        listLogModel = new DefaultListModel();
    }

    public static void main(String[] args) {
        BioMiniSDK bioMiniSDK = new BioMiniSDK();
        bioMiniSDK.init();
        for (Object model : bioMiniSDK.listModel.toArray()){
            System.out.println(model.toString());
        }
        int res = bioMiniSDK.captureSingle();
        System.out.println(res);
    }

    public void init(){

        if(nInitFlag!=0){

            System.out.println("already init..");

            return;
        }

        nCaptureFlag=0;

        try{

            libScanner = (UFScannerClass) Native.loadLibrary("UFScanner",UFScannerClass.class);
            libMatcher = (UFMatcherClass)Native.loadLibrary("UFMatcher",UFMatcherClass.class);
        }catch(Exception ex){

            System.out.println("loadLlibrary : UFScanner,UFMatcher fail!!");
            System.out.println("loadLlibrary : UFScanner,UFMatcher fail!!");

            return;
        }
        int nRes =0;

        nRes = libScanner.UFS_Init();

        if(nRes ==0){

            System.out.println("UFS_Init() success!!");

            nInitFlag=1;

            //getJTextField_status().setText("UFS_Init() success,nInitFlag value set 1");
            System.out.println("Scanner Init success!!");

            nRes = testCallScanProcCallback();

            if(nRes==0){

                System.out.println("==>UFS_SetScannerCallback pScanProc ...");

                IntByReference refNumber = new IntByReference();
                nRes = libScanner.UFS_GetScannerNumber(refNumber);

                if(nRes ==0){
                    nScannerNumber = refNumber.getValue();
                    System.out.println("UFS_GetScannerNumber() scanner number :"+ nScannerNumber);

                    PointerByReference refMatcher = new PointerByReference();

                    nRes = libMatcher.UFM_Create(refMatcher);

                    if(nRes==0){

                        updateScannerList(); //list upate ==> getcurrentscannerhandle�� list�� ������//
                        System.out.println("after upadtelist");
                        initVariable(1);
                        System.out.println("after initVariable");
                        initArray(100,1024); //array size,template size

                        hMatcher = refMatcher.getValue();

                        IntByReference refValue = new IntByReference();
                        IntByReference refFastMode = new IntByReference();

                        //security level (1~7)

                        nRes = libMatcher.UFM_GetParameter(hMatcher,302,refValue); //302 : security level :UFM_

                        if(nRes==0){
                            nSecurityLevel = refValue.getValue() ;//
                            System.out.println("get security level,302(security) value is "+refValue.getValue());
                        }else{
                            System.out.println("get security level fail! code: "+nRes);
                            System.out.println("get security level fail! code: "+nRes);
                        }

                        /*if(nRes==0 && jComboBox_security_level.getItemCount()>0){
                            for(int i=0;i<6;i++){

                                if(i ==refValue.getValue()-1){ //i : list index(zero based) ,refValue
                                    jComboBox_security_level.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }*/

                        //fast mode

                        nRes = libMatcher.UFM_SetParameter(hMatcher,libMatcher.UFM_PARAM_FAST_MODE,refFastMode);
                        if(nRes==0){
                            nFastMode = refFastMode.getValue();
                            System.out.println("get fastmode,301(fastmode) value is "+refFastMode.getValue());
                            //MsgBox("get fastmode,301(fastmode) value is "+refFastMode.getValue());
                        }else{
                            System.out.println("get fastmode value fail! code: "+nRes);
                            System.out.println("get fastmode value fail! code: "+nRes);
                        }
                        /*if(nFastMode==1){
                            jCheckBox_fastmode.setSelected(true);
                        }*/

                        int  nSelectedIdx = 0;//jComboBox_MatchType.getSelectedIndex();

                        if(hMatcher!=null){
                            switch(nSelectedIdx){

                                case	0:
                                    nRes = libMatcher.UFM_SetTemplateType(hMatcher,libMatcher.UFM_TEMPLATE_TYPE_SUPREMA); //2001 Suprema type

                                    break;
                                case	1:
                                    nRes = libMatcher.UFM_SetTemplateType(hMatcher,libMatcher.UFM_TEMPLATE_TYPE_ISO19794_2); //2002 iso type

                                    break;
                                case	2:
                                    nRes = libMatcher.UFM_SetTemplateType(hMatcher,libMatcher.UFM_TEMPLATE_TYPE_ANSI378); //2003 ansi type

                                    break;
                            }
                        }

                    }else{

                        System.out.println("UFM_Create fail!! code :"+nRes);

                        return;
                    }

                }else{
                    System.out.println("GetScannerNumber fail!! code :"+nRes);
                    System.out.println("GetScannerNumber fail!! code :"+nRes);
                    return;

                }

            }else{

                System.out.println("UFS_SetScannerCallback() fail,code :"+ nRes);

            }

        }

        if(nRes!=0){
            System.out.println("Init() fail!!");
            System.out.println("Init fail!! return code:"+nRes);
            System.out.println("Scanner Init fail!!");
        }
    }

    public int testCallScanProcCallback() {
        int nRes =0;

        PointerByReference refParam = new PointerByReference();
        refParam.getPointer().setInt(0,1);

        nRes = libScanner.UFS_SetScannerCallback(pScanProc,refParam);
        if(nRes==0){
            System.out.println("==>UFS_SetScannerCallback pScanProc ..."+pScanProc);

        }
        return nRes;
    }

    public void updateScannerList() {

        int nSelectedIdx =0;
        int nRes =0;
        PointerByReference hTempScanner = new PointerByReference();
        Pointer hScanner =null;
        IntByReference refType = new IntByReference();

        byte[] bScannerId = new byte[512];

        String szListLog = null;
        int nNumber=0;

        System.out.println("==update Scanner list==");

        IntByReference refNumber = new IntByReference();

        nRes = libScanner.UFS_GetScannerNumber(refNumber);

        if(nRes ==0){

            System.out.println("UFS_GetScannerNumber() res value is "+nRes);

            nNumber = refNumber.getValue();
        }else{

            return;
        }

        if(nNumber<=0){

            if(listModel.getSize()>0){
                listModel.clear();
                System.out.println("list clear");
            }
            return;
        }


        if(listModel.getSize()>0){
            listModel.clear();
            System.out.println("list clear");
        }

        for(int j=0;j<nNumber;j++){
            nRes = libScanner.UFS_GetScannerHandle(j,hTempScanner);
            hScanner=null;
            hScanner = hTempScanner.getValue();

            if(nRes ==0 && hScanner!=null){

                nRes = libScanner.UFS_GetScannerID(hScanner,bScannerId);

                nRes=libScanner.UFS_GetScannerType(hScanner, refType);

                szListLog="ID : "+Native.toString(bScannerId)+" Type : "+refType.getValue();

                listModel.insertElementAt(szListLog, j);

            }
        }

        /*nSelectedIdx = jList1_scanner_list.getSelectedIndex();
        if (nSelectedIdx == -1) {
            nSelectedIdx = 0;
        }

        jList1_scanner_list.setSelectedIndex(nSelectedIdx);
        jList1_scanner_list.ensureIndexIsVisible(nSelectedIdx);*/
    }

    public void initVariable(int nFlag) {

        if(nFlag==1){ //UFS_Init\//
            nInitFlag = 1;
        }else{
            nInitFlag = 0;
        }

        nCaptureFlag=0;

        nLogListCnt =0;

        listLogModel.clear();

        String szComboItem =null;
        for(int i=0;i<6;i++){
            szComboItem=String.valueOf(i);
            //jComboBox_timeout.insertItemAt(szComboItem, i);
        }

        //jComboBox_timeout.setSelectedIndex(0);

        ///////////////////////////////////////////////////
        szComboItem =null;

        for(int i=0;i<256;i++){
            szComboItem=String.valueOf(i);
            //jComboBox_bri.insertItemAt(szComboItem, i);
        }
        //jComboBox_bri.setSelectedIndex(100); //100

        /////////////////////////////////////////////////
        szComboItem =null;

        for(int i=0;i<8;i++){
            szComboItem=String.valueOf(i);
            //jComboBox_sens.insertItemAt(szComboItem, i);
        }
        //jComboBox_sens.setSelectedIndex(4); //4

        /////////////////////////////////////////////////
        szComboItem =null;
        for(int i=0;i<6;i++){
            szComboItem=String.valueOf((i*10));
            //jComboBox_enroll.insertItemAt(szComboItem, i);
        }
        //jComboBox_enroll.setSelectedIndex(5);

        szComboItem =null;
        for(int i=0;i<4;i++){
            szComboItem=String.valueOf(i);
            //jComboBox_detect_fake.insertItemAt(szComboItem, i);
        }
       /* jComboBox_detect_fake.setSelectedIndex(2);

        ///////////////////////////////////////////////
        jComboBox_ScanType.insertItemAt("suprema*", 0);
        jComboBox_ScanType.insertItemAt("iso_19794_2", 1);
        jComboBox_ScanType.insertItemAt("ansi378", 2);
        jComboBox_ScanType.setSelectedIndex(0);
        ///////////////////////////////////////////////
        jComboBox_MatchType.insertItemAt("suprema*", 0);
        jComboBox_MatchType.insertItemAt("iso_19794_2", 1);
        jComboBox_MatchType.insertItemAt("ansi378", 2);
        jComboBox_MatchType.setSelectedIndex(0);*/
        ///////////////////////////////////////////////
        szComboItem =null;
        int nTempRate =10;
        for(int i=1;i<8;i++){
            szComboItem=String.valueOf(i);
            if(i==1){
                szComboItem = szComboItem+"(FAR "+1+"/"+nTempRate*10+")";
            }else{
                szComboItem = szComboItem+"("+1+"/"+nTempRate*10+")";
            }
            //jComboBox_security_level.insertItemAt(szComboItem, i-1);
            nTempRate=nTempRate*10;
        }
        //jComboBox_security_level.setSelectedIndex(4); //7


        Pointer hScanner =null;
        hScanner = getCurrentScannerHandle();

        IntByReference pValue = new IntByReference();

        pValue.setValue(5000);

        int nRes = libScanner.UFS_SetParameter(hScanner,libScanner.UFS_PARAM_TIMEOUT,pValue);
        if(nRes==0){
            System.out.println("Change combox-timeout,201(timeout) value is "+pValue.getValue());
        }else{
            System.out.println("Change combox-timeout,change parameter value fail! code: "+nRes);
        }

        pValue.setValue(100);
        nRes = libScanner.UFS_SetParameter(hScanner,libScanner.UFS_PARAM_BRIGHTNESS,pValue);
        if(nRes==0){
            System.out.println("Change combox-brightness,202 value is "+pValue.getValue());
        }else{
            System.out.println("Change combox-brightness,change parameter value fail! code: "+nRes);
        }

        pValue.setValue(2);
        nRes = libScanner.UFS_SetParameter(hScanner,libScanner.UFS_PARAM_DETECT_FAKE,pValue);
        if(nRes==0){
            System.out.println("Change combox-detect_fake,312(fake detect) value is "+pValue.getValue());
        }else{
            System.out.println("Change combox-detect_fake,change parameter value fail! code: "+nRes);
        }

        pValue.setValue(4);
        nRes = libScanner.UFS_SetParameter(hScanner,libScanner.UFS_PARAM_SENSITIVITY,pValue);
        if(nRes==0){
            System.out.println("Change combox-sensitivity,203 value is "+pValue.getValue());
        }else{
            System.out.println("Change combox-sensitivity,change parameter value fail! code: "+nRes);
        }

        nRes = libScanner.UFS_SetTemplateType(hScanner,libScanner.UFS_TEMPLATE_TYPE_SUPREMA); //2001 Suprema type
        if(nRes==0){
            System.out.println("Change combox-Scan TemplateType:2001");
        }else{
            System.out.println("Change combox-Scan TemplateType,change parameter value fail! code: "+nRes);
        }
    }

    public void initArray(int nArrayCnt,int nMaxTemplateSize) {
        if(byteTemplateArray!=null){
            byteTemplateArray=null;

        }

        if(intTemplateSizeArray!=null){
            intTemplateSizeArray=null;

        }

        byteTemplateArray= new byte[nArrayCnt][MAX_TEMPLATE_SIZE];

        intTemplateSizeArray=new int[nArrayCnt];

        refTemplateArray= new PointerByReference();

    }

    public Pointer getCurrentScannerHandle() {
        Pointer hScanner = null;
        int nRes =0;
        int nNumber =0;

        PointerByReference refScanner = new PointerByReference();
        IntByReference refScannerNumber = new IntByReference();

//		�Ʒ� success!!//
        nRes = libScanner.UFS_GetScannerNumber(refScannerNumber);

        if(nRes==0){

            nNumber = refScannerNumber.getValue();

            if(nNumber <=0){

                return null;
            }

        }else{

            return null;
        }

        int index = 0;//jList1_scanner_list.getSelectedIndex();
        if (index == -1) {
            index = 0;
        } else {

        }


        //jList1_scanner_list.setSelectedIndex(index);
        //jList1_scanner_list.ensureIndexIsVisible(index);

        nRes = libScanner.UFS_GetScannerHandle(index,refScanner);

        hScanner = refScanner.getValue();

        if(nRes ==0 && hScanner!=null){
            return hScanner;
        }
        return null;
    }

    public int captureSingle() {

        int nRes =0;
        Pointer hScanner = null;

        System.out.println("call GetCurrentScannerHandle()");

        hScanner = getCurrentScannerHandle();

        if(hScanner!=null){

            System.out.println("GetScannerHandle return hScanner pointer: "+hScanner);

            System.out.println("get Scanner handle success pointer:"+hScanner);

        }else{

            System.out.println("GetScannerHandle fail!!");

            System.out.println("get Scanner handle fail!!");

            return -1;
        }

        System.out.println("Start single image capturing");

        nRes = libScanner.UFS_CaptureSingleImage(hScanner);

        if(nRes==0){
            System.out.println("==>UFS_CaptureSingleImage return value is.."+nRes);

            nCaptureFlag=1;

            drawCurrentFingerImage();

        }else{

            System.out.println("SingleImage fail!! code:"+nRes);

            byte[] refErr = new byte[512];

            nRes = libScanner.UFS_GetErrorString(nRes,refErr);
            if(nRes ==0){
                System.out.println("==>UFS_GetErrorString err is "+Native.toString(refErr));
            }

            System.out.println("caputure single img fail!!");
        }

        return nRes;
    }

    public void drawCurrentFingerImage() {
		/*test draw image*/
        IntByReference refResolution = new IntByReference();
        IntByReference refHeight     = new IntByReference();
        IntByReference refWidth      = new IntByReference();
        Pointer hScanner =null;

        hScanner = getCurrentScannerHandle();

        libScanner.UFS_GetCaptureImageBufferInfo(hScanner, refWidth, refHeight, refResolution);

        byte[] pImageData = new byte[refWidth.getValue()*refHeight.getValue()];

        libScanner.UFS_GetCaptureImageBuffer(hScanner,pImageData);

        //imgPanel.drawFingerImage(refWidth.getValue(),refHeight.getValue(),pImageData);
        System.out.println(pImageData);
       // String encoded = Base64.encodeBase64String(pImageData);
        //System.out.println(encoded);
        getImg(hScanner);
    }

   public void getImg(Pointer hScanner){

       byte[] bTemplate = new byte[512];
       PointerByReference refError;
       IntByReference refTemplateSize = new IntByReference();

       IntByReference refTemplateQuality = new IntByReference();

       IntByReference refVerify = new IntByReference();

       int nRes = libScanner.UFS_Extract(hScanner,bTemplate,refTemplateSize,refTemplateQuality);
       System.out.println("quality: "+refTemplateQuality.getValue());

       String path = "D:/xx.bmp";
       libScanner.UFS_SaveCaptureImageBufferToBMP(hScanner,path);

       byte[] img = new byte[512];
       libScanner.UFS_GetCaptureImageBuffer(hScanner,img);

       String encoded = Base64.encodeBase64String(img);
        System.out.println(encoded);
       
   }
}
