package controllers;

import models.Device;
import models.UserTemplate;
import models.Messages;
import models.biostarAPI.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.Biostar2Service;
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
    Biostar2Service biostar2Service;

    @Autowired
    public MainRestController(DeviceService deviceService, FingerprintTemplateService fingerprintTemplateService, Biostar2Service biostar2Service) {
        this.deviceService = deviceService;
        this.fingerprintTemplateService = fingerprintTemplateService;
        this.biostar2Service = biostar2Service;
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








    public String t(){
        return "RSMREaEAVUYoAtFNByMDMawNLINxowUjRQFPkD+FMZWIQ8VQOokUBYEFBh/FsEyNFwXAW4g7hpGVCCMGsaeIMYbhPocPx9FoByUH0UIKNEfRmIYbSGBKkTgI4TSHIYkRoowdSaGnjRsJ8USUE0tBX6UhC2E3CTTMkZCCKIzQjQQ2DYA1hhvNoZGJD84BaY0jThEuBRJOEBw9Fk4gqZ4WDxEtDAXPUXqGGw/wK4MykCGQhCFQMSqD///wABIjRP////4AASMzRP///+AAEjM0Vf//7gABIzRFVf/d4AASM0VVb8zdABIzRFZmzM3QEjNEVWZszN0BI0RVZma8zNAjRFVmZmu8zANEVWZmZru8wTRVZnZma7u8JFZmZ2Zmq7vDVmZ3dmZpqqpnd3d3Zm+Zqpd3d3d2ZvmZmYiHd3dm/5mZmIh3d3Zv8A==";
    }

    public VerifyFingerprintOption t4(){
        String t0 = "RSYRE6dKVUYjwhABiBED4WsJKcWhUIgmBkGwDC9GQaYHDIZxbwMNxwEUhQ8H0W4GJ8gRVA8IyDFxBAtIoRUGJIjgUYoZyREOCjcJUUIEFglwZIYbybFlkCiJ0aoKOspRnQQFCnF2CCuKwUUJP4rxPIcdy5ERjxaLoWsGIouwTg4czBFqEilMUaUMCIzReQQkzOGskCONYUkXKg6hOggcjvFjojKP8JICCQ/xeYcmESGUCy2RMTSJG9GQVzcbkeAlJiER4KiT////8AD////////eAAD//////M3uAAH////8zN7gARL////Mze4AEiP/+7zM3eABIjNPu8zN3gASI0T7u8ze4AEjNE+ru83eASI0RPq7u83gEjNERKq7vNASI0REWaq7zQEjRFVVmqu83hI0RVVpmqvN4TRFVVaZqqvOJEVVVf+ZqrwEVmZmb/+ZmrJWZmZm//mZqmVnZmZv//mZlmd2Zm/w";
        String t1 = "RC0RE6xKVUZHCfuGHhaaiVAicYdIJ/uMWifthxYqnoQbNJyGTDVzj0U8cwsTPSCDLz4Vi2pAXYUoQY8GNEOQkEpD9wpxSNyFCEuqhVNLYYl+TVYLOFEXkSpSmodCUm4ONlWVlE5X6Y2GWlSKgFtRiEZc9ZAPXayFRGBnl1BoU4k2bI2kYXXNBBF3r4eHeNIMd33PA0l+0YtYf0mIP4TyFDeEOcI+iUqPcYtKhk2QPoNXkkSEI5O3AhaUuQL//83gAP//////zN7gAf////+8ze4BEv///7vM3uASI///u7zN7gEiM//7u8ze4BIjNE+7u83uASM0RPq7vN4BEjNET6q7vOASM0RFWqq7zgEjRFVVqqu84BM0RVVZmqvNAjRVVWaZqqvQJEVVVWmaqr0TVVVmZvmaq8JFZmZmb/mZqVZ3ZmZm/5mZh2d3ZmZv+ZmYd3d2Zmf/+IiHd3dmZnA=";
                   //RSsOE7BKU0IigTECjylC4VKHLkNhp4clA3GwDAyD4XCFDQTBGAUPBSFvCCeFUVGQCkWRc4AkRhBQiwLGMR2JC0ZRGYMZRmEPixWGwGYHNwbRQYQbRuFlDyeHAaoKBkgheYMsCCFCiBbI0WyIIkjgTw8oiXGkjCRJoayQBkoBHoQJykF7BCRKUEUVKcvBOgkczBFipgsNcXuDMg3RkAMCzfB+iiYOUZMJLU6RMQceDtEhQx8O8DsuIU8gpREgz9EwCh6P8DARHVBRJYwmENAwAxNQ4IACLRFgMAQw0vCKBv//3e4B/////N3uAR///7zN3gES//u7zd4AEv+7u83eABI/qrvM3gEjNJqrzN4BI0SaqrzeEjNEqqq83hI0VZmqvN4TNFWZmrvOE0VVmZmrziRVVpmZqsA1VmaZmaqwRWZmiZmaqFZ2Z/iImYh3d3/4mIiId3d//5mIiIh3f//5iIiId/8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=
        VerifyFingerprintOption verifyFingerprintOption = new VerifyFingerprintOption("DEFAULT",t0,t0);
        return verifyFingerprintOption;
    }

    public UserTemplate t3(){
        String t0 = "RSYRE6dKVUYjwhABiBED4WsJKcWhUIgmBkGwDC9GQaYHDIZxbwMNxwEUhQ8H0W4GJ8gRVA8IyDFxBAtIoRUGJIjgUYoZyREOCjcJUUIEFglwZIYbybFlkCiJ0aoKOspRnQQFCnF2CCuKwUUJP4rxPIcdy5ERjxaLoWsGIouwTg4czBFqEilMUaUMCIzReQQkzOGskCONYUkXKg6hOggcjvFjojKP8JICCQ/xeYcmESGUCy2RMTSJG9GQVzcbkeAlJiER4KiT////8AD////////eAAD//////M3uAAH////8zN7gARL////Mze4AEiP/+7zM3eABIjNPu8zN3gASI0T7u8ze4AEjNE+ru83eASI0RPq7u83gEjNERKq7vNASI0REWaq7zQEjRFVVmqu83hI0RVVpmqvN4TRFVVaZqqvOJEVVVf+ZqrwEVmZmb/+ZmrJWZmZm//mZqmVnZmZv//mZlmd2Zm/w";
        String t1 = "RC0RE6xKVUZHCfuGHhaaiVAicYdIJ/uMWifthxYqnoQbNJyGTDVzj0U8cwsTPSCDLz4Vi2pAXYUoQY8GNEOQkEpD9wpxSNyFCEuqhVNLYYl+TVYLOFEXkSpSmodCUm4ONlWVlE5X6Y2GWlSKgFtRiEZc9ZAPXayFRGBnl1BoU4k2bI2kYXXNBBF3r4eHeNIMd33PA0l+0YtYf0mIP4TyFDeEOcI+iUqPcYtKhk2QPoNXkkSEI5O3AhaUuQL//83gAP//////zN7gAf////+8ze4BEv///7vM3uASI///u7zN7gEiM//7u8ze4BIjNE+7u83uASM0RPq7vN4BEjNET6q7vOASM0RFWqq7zgEjRFVVqqu84BM0RVVZmqvNAjRVVWaZqqvQJEVVVWmaqr0TVVVmZvmaq8JFZmZmb/mZqVZ3ZmZm/5mZh2d3ZmZv+ZmYd3d2Zmf/+IiHd3dmZnA=";
                    //RSsOE7BKU0IigTECjylC4VKHLkNhp4clA3GwDAyD4XCFDQTBGAUPBSFvCCeFUVGQCkWRc4AkRhBQiwLGMR2JC0ZRGYMZRmEPixWGwGYHNwbRQYQbRuFlDyeHAaoKBkgheYMsCCFCiBbI0WyIIkjgTw8oiXGkjCRJoayQBkoBHoQJykF7BCRKUEUVKcvBOgkczBFipgsNcXuDMg3RkAMCzfB+iiYOUZMJLU6RMQceDtEhQx8O8DsuIU8gpREgz9EwCh6P8DARHVBRJYwmENAwAxNQ4IACLRFgMAQw0vCKBv//3e4B/////N3uAR///7zN3gES//u7zd4AEv+7u83eABI/qrvM3gEjNJqrzN4BI0SaqrzeEjNEqqq83hI0VZmqvN4TNFWZmrvOE0VVmZmrziRVVpmZqsA1VmaZmaqwRWZmiZmaqFZ2Z/iImYh3d3/4mIiId3d//5mIiIh3f//5iIiId/8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=
                    //RSUPEqlKU0IPQtFsiClEYVCHLkTxp4YlRRGwDQxFQW+FDoaRbgYnRuFSkAqHsRgEGUfxEYsjh/FUizaIIUMFG4iBZY8miKCtChWIsW8FOokRngUFCZF4BytJoUUIHQpRFRIiSoBODxYKkW2FHErhaxQoyyGkjCQLQa4PCMvRegYCS+CCjSMMMUsYKY1RO4gcjfFlowTOIHwOCY8xewQzDzGUBCaQAZULLlAxM4kbkMFqkiIQ8KoYHtEhI9gh0ZEzlP///e7////////e7g//////ze4AEv///8ze4AEj//+8ze4BIi//u8zd4AEiNLu7zd4BEjNKu7vN4BIzRKq7vN4CIzRKqrvNASM0RZqrvN4SNEVZmqvN4TNFVYmqq84TRFVZmqq84kVVVvmZqrBFZmZviZmrBGZmb/+ZmadWdmb/+ImZhmd2fw==
        UserTemplate userTemplate = new UserTemplate(t1,Messages.Success);
        return userTemplate;
    }

    public UpdateUserFingerprintTemplateList tList3(String t0, String t1){
        UpdateUserFingerprintTemplateList userFingerprintTemplateList = new UpdateUserFingerprintTemplateList();
        FingerprintTemplate fingerprintTemplate = new FingerprintTemplate(t0,t1);
        FingerprintTemplate[] fingerprintTemplateArr = new FingerprintTemplate[1];
        fingerprintTemplateArr[0]=fingerprintTemplate;
        userFingerprintTemplateList.setFingerprint_template_list(fingerprintTemplateArr);
        return userFingerprintTemplateList;
    }

    private String t0(){
        return "RSYRE6dKVUYjwhABiBED4WsJKcWhUIgmBkGwDC9GQaYHDIZxbwMNxwEUhQ8H0W4GJ8gRVA8IyDFxBAtIoRUGJIjgUYoZyREOCjcJUUIEFglwZIYbybFlkCiJ0aoKOspRnQQFCnF2CCuKwUUJP4rxPIcdy5ERjxaLoWsGIouwTg4czBFqEilMUaUMCIzReQQkzOGskCONYUkXKg6hOggcjvFjojKP8JICCQ/xeYcmESGUCy2RMTSJG9GQVzcbkeAlJiER4KiT////8AD////////eAAD//////M3uAAH////8zN7gARL////Mze4AEiP/+7zM3eABIjNPu8zN3gASI0T7u8ze4AEjNE+ru83eASI0RPq7u83gEjNERKq7vNASI0REWaq7zQEjRFVVmqu83hI0RVVpmqvN4TRFVVaZqqvOJEVVVf+ZqrwEVmZmb/+ZmrJWZmZm//mZqmVnZmZv//mZlmd2Zm/w";
    }

    private String t1(){
        return "RC0RE6xKVUZHCfuGHhaaiVAicYdIJ/uMWifthxYqnoQbNJyGTDVzj0U8cwsTPSCDLz4Vi2pAXYUoQY8GNEOQkEpD9wpxSNyFCEuqhVNLYYl+TVYLOFEXkSpSmodCUm4ONlWVlE5X6Y2GWlSKgFtRiEZc9ZAPXayFRGBnl1BoU4k2bI2kYXXNBBF3r4eHeNIMd33PA0l+0YtYf0mIP4TyFDeEOcI+iUqPcYtKhk2QPoNXkkSEI5O3AhaUuQL//83gAP//////zN7gAf////+8ze4BEv///7vM3uASI///u7zN7gEiM//7u8ze4BIjNE+7u83uASM0RPq7vN4BEjNET6q7vOASM0RFWqq7zgEjRFVVqqu84BM0RVVZmqvNAjRVVWaZqqvQJEVVVWmaqr0TVVVmZvmaq8JFZmZmb/mZqVZ3ZmZm/5mZh2d3ZmZv+ZmYd3d2Zmf/+IiHd3dmZnA=";
    }

    private String t0_1(){
        return "RSgRE6dKU0IYg/BaBhzD8AAJIgPxTgoFBBFtCRaEEAiHJ4SxpIYGhYFrCB9GYVCSEUcBDo0cRyBMjzhHcJcJDUexbIcTB7FikS3IUT6FHkiRpYgwyVGahSKJYUEIDQmha4cZSfBKkTUKcTiEH0rRoIwaS5BCkx8NETYKEY0hYqEBTUB3hThNUI+FAU3wd4czTrGSAwGO0HsIJk7Qi4IaT4GSCAIPsHwIM8/wigEhEBEuhQKQgH2IDtCQIgs2ESEuihURcCiGEdGAKAsF0dB9h/////Af///////+4BEv//////3uASI//////d7gEjNE///8zdABIzNP//vM3QASM0Rf/7zM0BIzREX/+7zNATNERV//u8zRIzRFVf/7vM0SNEVVZf+7vNEkRVZmX/q7vBNFVmZv/6q7wkVmZmb/+qu8RWZmZm//qrvEZmZmf/95mqlnd3d3//+ZmoeHd3d///mZqYh3d3f///ma/4d3d///8A==";
    }

    private String t1_1(){
        return "RSsPE7FKU0IkgXFOiClCAaWHIEIRsA0JA9FviSJD4VERH0SwUI0TxQEPDBZFcWWSMYWBP4QhBaCrijTGYJyFJgahRAgWxyAQkxzHYE4REQeBbgcXB8FjlTlHwTiEI0gRo4seiIGtEgQJAXqIHckRSBgjiTCkkSZJsTgJJEphNwc6ypCQiRdKsWMnAwsQfYk4S7GThyxMAJEDBUxxfIggTPGSiwKNMIAKG46xMQoZz8EmBg4P8IACJ1AgMoQlEFCHAwjRYIGCLVFhNAYm0ZAugwgRwC0DM5IwjIcyUpCOB////uAf//////3uASL////83uASI////8zuASM0//+8zgASM0//q7zgEjNEX/q7vgEzRFX5qrvQE0RVb5qqvQI0VWb5mqrRNFVmZpmarSRWZmZpmarDRmZmb4mZrCVmZmb4iZn/Z3Zmb4iZn/Z3d2b/iIiId3d3//+IiIiHd3//+IiIiHd3//+IiIh3d3/w";
    }
}
