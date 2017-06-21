package services;

import models.FingerprintTemplate;
import models.Messages;
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
    public String getImage() {
        return bioMiniSDK.getImgBase64();
    }

    @Override
    public void saveImage(String path) {
        bioMiniSDK.saveImg(path);
    }

    @Override
    public int captureSingle() {
        return bioMiniSDK.captureSingle();
    }

    @Override
    public FingerprintTemplate captureAndGetTemplate() {

        String img = null;
        int res = 0;
        boolean fail = false;
        FingerprintTemplate fingerprintTemplate = new FingerprintTemplate();

        res = captureSingle();
        fingerprintTemplate.setMessage(Messages.getMessage(res));

        fail = res != 0;

        img = getImage();
        if (img != null && !fail){
            fingerprintTemplate.setTemplate(img);
            fingerprintTemplate.setMessage(Messages.Success);
        }else{
            fingerprintTemplate.setMessage(Messages.GetImageFail);
        }

        return fingerprintTemplate;

    }


}
