package controllers;

import models.Device;
import models.UserTemplate;
import models.biostarAPI.*;


import java.util.HashSet;

/**
 * Created by Користувач on 20.06.2017.
 */
public interface MainController {

    UserTemplate getTemplate();

    void saveImage(String path);

    HashSet<Device> deviceList();

    void changeDevice(Device device);

    LoggedInUser authBiostar2Srv(UserLoginRequestData userLoginRequestData);

    VerifyFingerprintResult verifyFingerprint();

    UpdateResponse updateUserFingerprint(long user_id);

    AddResponse createNewUser(CreateUser createUser);

    String scanFingerprint();

}
