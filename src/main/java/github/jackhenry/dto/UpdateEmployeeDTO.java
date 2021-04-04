package github.jackhenry.dto;

public class UpdateEmployeeDTO extends CreateEmployeeDTO {
    private String id;
    private String accountId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
