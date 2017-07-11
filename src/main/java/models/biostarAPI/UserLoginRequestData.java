package models.biostarAPI;

/**
 * Created by Користувач on 22.06.2017.
 */
public class UserLoginRequestData {

    private String name;
    private String user_id;
    private String password;
    private String mobile_app_version;
    private String mobile_device_type;
    private String mobile_os_version;
    private String notification_token;

    public UserLoginRequestData(String name, String user_id, String password){
        this.name = name;
        this.user_id = user_id;
        this.password = password;
    }

    public UserLoginRequestData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile_app_version() {
        return mobile_app_version;
    }

    public void setMobile_app_version(String mobile_app_version) {
        this.mobile_app_version = mobile_app_version;
    }

    public String getMobile_device_type() {
        return mobile_device_type;
    }

    public void setMobile_device_type(String mobile_device_type) {
        this.mobile_device_type = mobile_device_type;
    }

    public String getMobile_os_version() {
        return mobile_os_version;
    }

    public void setMobile_os_version(String mobile_os_version) {
        this.mobile_os_version = mobile_os_version;
    }

    public String getNotification_token() {
        return notification_token;
    }

    public void setNotification_token(String notification_token) {
        this.notification_token = notification_token;
    }
}
