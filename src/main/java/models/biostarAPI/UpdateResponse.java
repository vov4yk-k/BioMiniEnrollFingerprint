package models.biostarAPI;

/**
 * Created by Користувач on 22.06.2017.
 */
public class UpdateResponse {
    private String message;
    private String status_code;

    public UpdateResponse(String message) {
        this.message = message;
    }

    public UpdateResponse() {
    }

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
}
