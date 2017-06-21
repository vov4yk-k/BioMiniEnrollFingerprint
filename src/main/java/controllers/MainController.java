package controllers;

import models.Device;
import models.FingerprintTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface MainController {

    FingerprintTemplate getTemplate();

    String saveImage();

    HashSet<Device> deviceList();

    void changeDevice(Device device);
}
