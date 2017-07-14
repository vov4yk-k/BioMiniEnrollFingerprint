package controllers;

import com.sun.jna.ptr.IntByReference;
import models.Device;
import models.UserTemplate;
import models.Messages;
import models.biostar2API.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.BS2SrvService;
import services.Biostar2SDK;
import services.DeviceService;
import services.FingerprintTemplateService;

import java.util.HashSet;

/**
 * Created by Користувач on 20.06.2017.
 */
@RestController
public class MainRestController implements MainController {

    DeviceService deviceService;
    FingerprintTemplateService fingerprintTemplateService;
    BS2SrvService biostar2Service;
    Biostar2SDK biostar2SDK;
    @Autowired
    public MainRestController(DeviceService deviceService, FingerprintTemplateService fingerprintTemplateService, BS2SrvService biostar2Service, Biostar2SDK biostar2SDK) {
        this.deviceService = deviceService;
        this.fingerprintTemplateService = fingerprintTemplateService;
        this.biostar2Service = biostar2Service;
        this.biostar2SDK = biostar2SDK;
    }

    @Override
    @RequestMapping("/getTemplate")
    public UserTemplate getTemplate() {
        return fingerprintTemplateService.captureAndGetTemplate();
    }

    @Override
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void saveImage(@RequestBody String path) {
        fingerprintTemplateService.saveImage(path);
    }

    @Override
    @RequestMapping("/deviceList")
    public HashSet<Device> deviceList() {
        return deviceService.deviceList();
    }

    @Override
    @RequestMapping(value = "/changeDevice", method = RequestMethod.POST)
    @ResponseBody
    public void changeDevice(@RequestBody Device device){
        deviceService.changeDevice(device);
    }

    @Override
    @RequestMapping(value = "/authClient", method = RequestMethod.POST)
    @ResponseBody
    public LoggedInUser authBiostar2Srv(@RequestBody UserLoginRequestData userLoginRequestData){
        return biostar2Service.authClient(userLoginRequestData);
    }

    @RequestMapping(value = "/setDeviceID", method = RequestMethod.POST)
    @ResponseBody
    public void setVerifyingDeviceID(@RequestBody String id) {
        biostar2Service.setDeviceId(id);
    }

    @Override
    @RequestMapping("/verifyFingerprint")
    public VerifyFingerprintResult verifyFingerprint() {
        VerifyFingerprintResult verifyFingerprintResult = null;
        UserTemplate userTemplate = fingerprintTemplateService.captureAndGetTemplate();
        if(userTemplate.getMessage() == Messages.Success) {
            VerifyFingerprintOption verifyFingerprintOption = new VerifyFingerprintOption();
            verifyFingerprintOption.setSecurity_level("DEFAULT");
            verifyFingerprintOption.setTemplate0(userTemplate.getTemplate());
            userTemplate = fingerprintTemplateService.captureAndGetTemplate();
            verifyFingerprintOption.setTemplate1(userTemplate.getTemplate());
            verifyFingerprintResult = biostar2Service.verifyFingerprint(verifyFingerprintOption);
        }
        return  verifyFingerprintResult;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/updateUserFingerprint/{user_id}")
    public UpdateResponse updateUserFingerprint(@PathVariable long user_id) {

        String t0, t1;

        UserTemplate userTemplate = fingerprintTemplateService.captureAndGetTemplate();
        t0=userTemplate.getTemplate();

        userTemplate = fingerprintTemplateService.captureAndGetTemplate();
        t1=userTemplate.getTemplate();

        return biostar2Service.updateUserFingerprint(user_id, t0, t1);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/userFingerprintTemplateList/{user_id}")
    public GetUserFingerprintTemplateList userFingerprintTemplateList(@PathVariable int user_id){
        return biostar2Service.userFingerprintTemplateList(user_id);
    }


    @Override
    @RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
    @ResponseBody
    public AddResponse createNewUser(@RequestBody CreateUser createUser) {
        return biostar2Service.createNewUser(createUser);
    }

    @RequestMapping("/scanFingerprintNetDevice")
    public String scanFingerprint(){
        return biostar2Service.scanFingerprint();
    }





}
