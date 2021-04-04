package github.jackhenry.dto;

public class UpdateEmployeeDTO extends CreateEmployeeDTO {
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
