package models.biostarAPI;

/**
 * Created by Користувач on 10.07.2017.
 */
public class CreateUser {
    private ListUserItemAccessGroup[] access_groups;
    private String email;
    private String expiry_datetime;
    private String login_id;
    private String name;
    private String password;
    private UserPermission permission;
    private String phone_number;
    private String pin;
    private String security_level;
    private String start_datetime;
    private String status;
    private SimpleUserGroup user_group;
    private String user_id;

    public ListUserItemAccessGroup[] getAccess_groups() {
        return access_groups;
    }

    public void setAccess_groups(ListUserItemAccessGroup[] access_groups) {
        this.access_groups = access_groups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpiry_datetime() {
        return expiry_datetime;
    }

    public void setExpiry_datetime(String expiry_datetime) {
        this.expiry_datetime = expiry_datetime;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserPermission getPermission() {
        return permission;
    }

    public void setPermission(UserPermission permission) {
        this.permission = permission;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SimpleUserGroup getUser_group() {
        return user_group;
    }

    public void setUser_group(SimpleUserGroup user_group) {
        this.user_group = user_group;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
