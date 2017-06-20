package controllers;

import models.Device;
import models.FingerprintTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.DeviceService;
import services.DeviceServiceImpl;
import services.FingerprintTemplateService;
import services.FingerprintTemplateServiceImpl;

import java.util.HashSet;

/**
 * Created by Користувач on 20.06.2017.
 */
@RestController
public class MainRestController implements MainController {

    DeviceService deviceService;
    FingerprintTemplateService fingerprintTemplateService;

    public MainRestController() {
        BioMiniSDK bioMiniSDK = new BioMiniSDK();
        this.deviceService = new DeviceServiceImpl(bioMiniSDK);
        this.fingerprintTemplateService = new FingerprintTemplateServiceImpl(bioMiniSDK);
    }

    @Override
    @RequestMapping("/getTemplate")
    public FingerprintTemplate getTemplate() {
        fingerprintTemplateService.captureSingle();
        fingerprintTemplateService.getImmage();
        return new FingerprintTemplate(fingerprintTemplateService.getImmage(),"Done!");
    }

    @Override
    public String saveImage() {
        return null;
    }

    @Override
    @RequestMapping("/deviceList")
    public HashSet<Device> deviceList() {
        return deviceService.deviceList();
    }

}
