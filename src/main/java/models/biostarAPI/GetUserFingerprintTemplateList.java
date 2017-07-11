package models.biostarAPI;

/**
 * Created by Користувач on 26.06.2017.
 */
public class GetUserFingerprintTemplateList {

    private FingerprintTemplate[] fingerprint_template_list;

    public FingerprintTemplate[] getFingerprint_template_list() {
        return fingerprint_template_list;
    }

    public void setFingerprint_template_list(FingerprintTemplate[] fingerprint_template_list) {
        this.fingerprint_template_list = fingerprint_template_list;
    }
}
