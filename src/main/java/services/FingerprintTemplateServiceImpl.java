package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Користувач on 20.06.2017.
 */
@Service
public class FingerprintTemplateServiceImpl implements FingerprintTemplateService {

    @Autowired
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
