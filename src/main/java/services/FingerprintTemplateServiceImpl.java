package services;

import models.UserTemplate;
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
        return bioMiniSDK.getTemplateBase64();
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
    public UserTemplate captureAndGetTemplate() {

        String img = null;
        int res = 0;
        boolean fail = false;
        UserTemplate userTemplate = new UserTemplate();

        res = captureSingle();
        userTemplate.setMessage(Messages.getMessage(res));

        fail = res != 0;

        img = getImage();
        if (img != null && !fail){
            userTemplate.setTemplate(img);
            userTemplate.setMessage(Messages.Success);
        }else{
            userTemplate.setMessage(Messages.GetImageFail);
        }

        return userTemplate;

    }

}
