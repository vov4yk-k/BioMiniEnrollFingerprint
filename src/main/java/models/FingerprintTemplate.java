package models;

/**
 * Created by Користувач on 20.06.2017.
 */
public class FingerprintTemplate {

    String template;
    String message;

    public FingerprintTemplate(String template, String message) {
        this.template = template;
        this.message = message;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
