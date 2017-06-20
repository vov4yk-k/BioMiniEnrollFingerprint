package services;

import controllers.BioMiniSDK;

/**
 * Created by Користувач on 20.06.2017.
 */
public class FingerprintTemplateServiceImpl implements FingerprintTemplateService {

    BioMiniSDK bioMiniSDK;

    public FingerprintTemplateServiceImpl(BioMiniSDK bioMiniSDK) {
        this.bioMiniSDK = bioMiniSDK;
    }

    @Override
    public String getImmage() {
        return bioMiniSDK.getImgBase64();
    }

    @Override
    public void saveImmage(String path) {
        bioMiniSDK.saveImg(path);
    }

    @Override
    public int captureSingle() {
        return bioMiniSDK.captureSingle();
    }
}
