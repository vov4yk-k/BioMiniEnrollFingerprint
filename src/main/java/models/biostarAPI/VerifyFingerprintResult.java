package models.biostarAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Користувач on 21.06.2017.
 */
public class VerifyFingerprintResult {

    private String message;
    private String status_code;
    private boolean verify_result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public boolean isVerify_result() {
        return verify_result;
    }

    public void setVerify_result(boolean verify_result) {
        this.verify_result = verify_result;
    }

    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return "Message:"+message+", Status:"+status_code+", Result:"+verify_result+"  "+dateFormat.format(date);
    }
}
