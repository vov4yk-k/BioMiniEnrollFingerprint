package services;

import models.UserTemplate;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface FingerprintTemplateService {

    String getImage();

    void saveImage(String path);

    int captureSingle();

    UserTemplate captureAndGetTemplate();
}
