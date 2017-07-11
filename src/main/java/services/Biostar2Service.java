package services;

import models.UserTemplate;
import models.biostarAPI.*;

/**
 * Created by Користувач on 21.06.2017.
 */
public interface Biostar2Service {

    LoggedInUser authClient(UserLoginRequestData userLogin);

    VerifyFingerprintResult verifyFingerprint(VerifyFingerprintOption fingerprint);

    void setDeviceId(String deviceId);

    UpdateResponse updateUserFingerprint(long user_id, String t0, String t1);

    GetUserFingerprintTemplateList userFingerprintTemplateList(int user_id);

    AddResponse createNewUser(CreateUser createUser);

    String scanFingerprint();
}
