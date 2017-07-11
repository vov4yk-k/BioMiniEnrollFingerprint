package models.biostarAPI;

/**
 * Created by Користувач on 22.06.2017.
 */
public class FingerprintTemplate {

    private boolean is_prepare_for_duress;
    private String template0;
    private String template1;

    public FingerprintTemplate(String template) {
        this.is_prepare_for_duress = true;
        this.template0 = template;
        this.template1 = template;
    }

    public FingerprintTemplate(String template0, String template1) {
        this.template0 = template0;
        this.template1 = template1;
    }

    public boolean isIs_prepare_for_duress() {
        return is_prepare_for_duress;
    }

    public void setIs_prepare_for_duress(boolean is_prepare_for_duress) {
        this.is_prepare_for_duress = is_prepare_for_duress;
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
