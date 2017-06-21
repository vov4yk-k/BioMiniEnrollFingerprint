package services;

import models.FingerprintTemplate;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface FingerprintTemplateService {

    String getImage();

    void saveImage(String path);

    int captureSingle();

    FingerprintTemplate captureAndGetTemplate();
}
