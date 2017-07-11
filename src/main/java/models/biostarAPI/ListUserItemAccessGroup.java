package models.biostarAPI;

/**
 * Created by Користувач on 10.07.2017.
 */
public class ListUserItemAccessGroup {

    private long id;
    private String included_by_user_group;
    private String name;

    public ListUserItemAccessGroup() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIncluded_by_user_group() {
        return included_by_user_group;
    }

    public void setIncluded_by_user_group(String included_by_user_group) {
        this.included_by_user_group = included_by_user_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
