package models;

/**
 * Created by Користувач on 20.06.2017.
 */
public class FingerprintTemplate {

    String template;
    Messages message;

    public FingerprintTemplate(String template, Messages message) {
        this.template = template;
        this.message = message;
    }

    public FingerprintTemplate() {
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages message) {
        if(this.message != null) return;
        this.message = message;
    }
}
