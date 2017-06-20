package controllers;

import models.Device;
import models.FingerprintTemplate;

import java.util.HashSet;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface MainController {

    FingerprintTemplate getTemplate();

    String saveImage();

    public HashSet<Device> deviceList();
}
