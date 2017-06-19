package com.suprema.UFE33;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCall;
import com.sun.jna.win32.StdCallLibrary;

public interface UFScannerClass extends StdCallLibrary {
    int UFS_OK = 0;
    int UFS_STATUS = 0;
    int UFS_ERROR = -1;
    int UFS_ERR_NO_LICENSE = -101;
    int UFS_ERR_LICENSE_NOT_MATCH = -102;
    int UFS_ERR_LICENSE_EXPIRED = -103;
    int UFS_ERR_NOT_SUPPORTED = -111;
    int UFS_ERR_INVALID_PARAMETERS = -112;
    int UFS_ERR_ALREADY_INITIALIZED = -201;
    int UFS_ERR_NOT_INITIALIZED = -202;
    int UFS_ERR_DEVICE_NUMBER_EXCEED = -203;
    int UFS_ERR_LOAD_SCANNER_LIBRARY = -204;
    int UFS_ERR_CAPTURE_RUNNING = -211;
    int UFS_ERR_CAPTURE_FAILED = -212;
    int UFS_ERR_FAKE_FINGER = -221;
    int UFS_ERR_FINGER_ON_SENSOR = -231;
    int UFS_ERR_NOT_GOOD_IMAGE = -301;
    int UFS_ERR_EXTRACTION_FAILED = -302;
    int UFS_ERR_CORE_NOT_DETECTED = -351;
    int UFS_ERR_CORE_TO_LEFT = -352;
    int UFS_ERR_CORE_TO_LEFT_TOP = -353;
    int UFS_ERR_CORE_TO_TOP = -354;
    int UFS_ERR_CORE_TO_RIGHT_TOP = -355;
    int UFS_ERR_CORE_TO_RIGHT = -356;
    int UFS_ERR_CORE_TO_RIGHT_BOTTOM = -357;
    int UFS_ERR_CORE_TO_BOTTOM = -358;
    int UFS_ERR_CORE_TO_LEFT_BOTTOM = -359;
    int UFS_PARAM_TIMEOUT = 201;
    int UFS_PARAM_BRIGHTNESS = 202;
    int UFS_PARAM_SENSITIVITY = 203;
    int UFS_PARAM_SERIAL = 204;
    int UFS_PARAM_DETECT_CORE = 301;
    int UFS_PARAM_TEMPLATE_SIZE = 302;
    int UFS_PARAM_USE_SIF = 311;
    int UFS_PARAM_CHECK_ENROLL_QUALITY = 321;
    int UFS_PARAM_DETECT_FAKE = 312;
    int UFS_SCANNER_TYPE_SFR200 = 1001;
    int UFS_SCANNER_TYPE_SFR300 = 1002;
    int UFS_SCANNER_TYPE_SFR300v2 = 1003;
    int UFS_SCANNER_TYPE_SFR500 = 1004;
    int UFS_TEMPLATE_TYPE_SUPREMA = 2001;
    int UFS_TEMPLATE_TYPE_ISO19794_2 = 2002;
    int UFS_TEMPLATE_TYPE_ANSI378 = 2003;

    int UFS_Init();

    int UFS_Update();

    int UFS_Uninit();

    int UFS_SetScannerCallback(UFScannerClass.UFS_SCANNER_PROC var1, PointerByReference var2);

    int UFS_RemoveScannerCallback();

    int UFS_GetScannerNumber(IntByReference var1);

    int UFS_GetScannerHandle(int var1, PointerByReference var2);

    int UFS_GetScannerHandleByID(String var1, PointerByReference var2);

    int UFS_GetScannerIndex(Pointer var1, IntByReference var2);

    int UFS_GetScannerID(Pointer var1, byte[] var2);

    int UFS_GetScannerType(Pointer var1, IntByReference var2);

    int UFS_GetParameter(Pointer var1, int var2, IntByReference var3);

    int UFS_SetParameter(Pointer var1, int var2, IntByReference var3);

    int UFS_IsSensorOn(Pointer var1, IntByReference var2);

    int UFS_IsFingerOn(Pointer var1, IntByReference var2);

    int UFS_CaptureSingleImage(Pointer var1);

    int UFS_StartCapturing(Pointer var1, UFScannerClass.UFS_CAPTURE_PROC var2, PointerByReference var3);

    int UFS_IsCapturing(Pointer var1, IntByReference var2);

    int UFS_AbortCapturing(Pointer var1);

    int UFS_Extract(Pointer var1, byte[] var2, IntByReference var3, IntByReference var4);

    int UFS_SetEncryptionKey(Pointer var1, String var2);

    int UFS_EncryptTemplate(Pointer var1, byte[] var2, int var3, byte[] var4, IntByReference var5);

    int UFS_DecryptTemplate(Pointer var1, byte[] var2, int var3, byte[] var4, IntByReference var5);

    int UFS_GetCaptureImageBufferInfo(Pointer var1, IntByReference var2, IntByReference var3, IntByReference var4);

    int UFS_GetCaptureImageBuffer(Pointer var1, byte[] var2);

    int UFS_SaveCaptureImageBufferToBMP(Pointer var1, String var2);

    int UFS_SaveCaptureImageBufferTo19794_4(Pointer var1, String var2);

    int UFS_ClearCaptureImageBuffer(Pointer var1);

    int UFS_SaveCaptureImageBufferToWSQ(Pointer var1, float var2, String var3);

    int UFS_GetErrorString(int var1, byte[] var2);

    int UFS_GetTemplateType(Pointer var1, IntByReference var2);

    int UFS_SetTemplateType(Pointer var1, int var2);

    int UFS_SelectTemplate_J(Pointer var1, PointerByReference var2, int[] var3, int var4, PointerByReference var5, int[] var6, int var7);

    public interface UFS_CAPTURE_PROC extends Callback {
        int callback(Pointer var1, int var2, Pointer var3, int var4, int var5, int var6, PointerByReference var7);
    }

    public interface UFS_SCANNER_PROC extends Callback, StdCall {
        int callback(String var1, int var2, PointerByReference var3);
    }
}