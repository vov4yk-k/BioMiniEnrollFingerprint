package com.suprema.UFE33;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface UFMatcherClass extends StdCallLibrary {
    int UFMOK = 0;
    int UFMERROR = -1;
    int UFMERR_NO_LICENSE = -101;
    int UFMERR_LICENSE_NOT_MATCH = -102;
    int UFMERR_LICENSE_EXPIRED = -103;
    int UFMERR_NOT_SUPPORTED = -111;
    int UFMERR_INVALID_PARAMETERS = -112;
    int UFM_ERR_MATCH_TIMEOUT = -401;
    int UFM_ERR_MATCH_ABORTED = -402;
    int UFM_ERR_TEMPLATE_TYPE = -411;
    int UFM_PARAM_FAST_MODE = 301;
    int UFM_PARAM_SECURITY_LEVEL = 302;
    int UFM_PARAM_USE_SIF = 311;
    int UFM_TEMPLATE_TYPE_SUPREMA = 2001;
    int UFM_TEMPLATE_TYPE_ISO19794_2 = 2002;
    int UFM_TEMPLATE_TYPE_ANSI378 = 2003;

    int UFM_Create(PointerByReference var1);

    int UFM_Delete(Pointer var1);

    int UFM_GetParameter(Pointer var1, int var2, IntByReference var3);

    int UFM_SetParameter(Pointer var1, int var2, IntByReference var3);

    int UFM_Verify(Pointer var1, byte[] var2, int var3, byte[] var4, int var5, IntByReference var6);

    int UFM_Identify_J(Pointer var1, byte[] var2, int var3, PointerByReference var4, int[] var5, int var6, int var7, IntByReference var8);

    int UFM_IdentifyMT_J(Pointer var1, byte[] var2, int var3, PointerByReference var4, int[] var5, int var6, int var7, IntByReference var8);

    int UFM_AbortIdentify(Pointer var1);

    int UFM_IdentifyInit(Pointer var1, byte[] var2, int var3);

    int UFM_IdentifyNext(Pointer var1, byte[] var2, int var3, IntByReference var4);

    int UFM_RotateTemplate(Pointer var1, byte[] var2, int var3);

    int UFM_GetErrorString(int var1, byte[] var2);

    int UFM_GetTemplateType(Pointer var1, IntByReference var2);

    int UFM_SetTemplateType(Pointer var1, int var2);
}