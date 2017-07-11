package models.biostarAPI;



import java.util.Base64;

/**
 * Created by Користувач on 21.06.2017.
 */
public class VerifyFingerprintOption {

    private String security_level;//Verify success criteria('DEFAULT', 'LOWER', '', 'NORMAL', 'HIGH', 'HIGHER'),
    private String template0;//fingerprint template base64 string,
    private String template1;//(base64, optional): fingerprint template base64 string

    public VerifyFingerprintOption(String security_level, String template0, String template1) {
        this.security_level = security_level;
        this.template0 = template0;
        this.template1 = template1;
    }

    public VerifyFingerprintOption(String template0, String template1) {
        this.security_level = "DEFAULT";
        this.template0 = template0;
        this.template1 = template1;
    }

    public VerifyFingerprintOption(String template) {
        this.security_level = "DEFAULT";
        this.template0 = "{"+template+"}";
        this.template1 = "{"+template+"}";
    }

    public VerifyFingerprintOption(){}

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getTemplate0() {
        return template0;
    }

    public void setTemplate0(String template0) {
        this.template0 = template0;
    }

    public String getTemplate1() {
        return template1;
    }

    public void setTemplate1(String template1) {
        this.template1 = template1;
    }
}
