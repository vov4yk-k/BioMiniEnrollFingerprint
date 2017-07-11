package models;


/**
 * Created by Користувач on 20.06.2017.
 */

public class Device {

    private int id;
    private String model;
    private int timeOut = 5000;
    private int brightness = 100;
    private int sensivity = 4;
    private int templateType = 2001;
    private int detectFake = 2;
    private int enrollQuality = 80;


    public Device(int id, String model, int timeOut, int brightness, int sensivity, int templateType, int detectFake, int enrollQuality) {
        this.id = id;
        this.model = model;
        this.timeOut = timeOut;
        this.brightness = brightness;
        this.sensivity = sensivity;
        this.templateType = templateType;
        this.detectFake = detectFake;
        this.enrollQuality = enrollQuality;
    }

    public Device(int id, String model) {
        this.id = id;

        this.model = model;
    }

    public Device() {

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getSensivity() {
        return sensivity;
    }

    public void setSensivity(int sensivity) {
        this.sensivity = sensivity;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }

    public int getDetectFake() {
        return detectFake;
    }

    public void setDetectFake(int detectFake) {
        this.detectFake = detectFake;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return this.model.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        String objModel = ((Device) obj).getModel();
        return this.model.equals(objModel);
    }
}
