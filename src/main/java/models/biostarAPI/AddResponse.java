package models.biostarAPI;

/**
 * Created by Користувач on 10.07.2017.
 */
public class AddResponse {

    private long id;
    private String message;
    private String status_code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
