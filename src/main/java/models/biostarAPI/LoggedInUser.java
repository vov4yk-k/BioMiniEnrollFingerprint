package models.biostarAPI;


import org.apache.http.cookie.Cookie;

/**
 * Created by Користувач on 21.06.2017.
 */
public class LoggedInUser {

        private String account_id;
        private String email;
        private String name;
        private String password_strength_level;
        private CloudPermission[] permissions;
        private String photo;
        private CloudRole[] roles;
        private String status;
        private int unread_notification_count;
        private SimpleUserGroup user_group;
        private String user_id;
        private Cookie cookie;

        public void setCookie(Cookie cookie){
            this.cookie = cookie;
        }

        public Cookie getCookie(){
            return this.cookie;
        }

        public String getAccount_id() {
                return account_id;
        }

        public void setAccount_id(String account_id) {
                this.account_id = account_id;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPassword_strength_level() {
                return password_strength_level;
        }

        public void setPassword_strength_level(String password_strength_level) {
                this.password_strength_level = password_strength_level;
        }

        public CloudPermission[] getPermissions() {
                return permissions;
        }

        public void setPermissions(CloudPermission[] permissions) {
                this.permissions = permissions;
        }

        public String getPhoto() {
                return photo;
        }

        public void setPhoto(String photo) {
                this.photo = photo;
        }

        public CloudRole[] getRoles() {
                return roles;
        }

        public void setRoles(CloudRole[] roles) {
                this.roles = roles;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public int getUnread_notification_count() {
                return unread_notification_count;
        }

        public void setUnread_notification_count(int unread_notification_count) {
                this.unread_notification_count = unread_notification_count;
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
