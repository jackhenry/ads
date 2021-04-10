package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account {
    @XmlElement
    private int accountId;
    @XmlElement
    private String username;
    @XmlElement
    private String firstname;
    @XmlElement
    private String lastname;

    public Account(int accountId, String username, String firstname, String lastname) {
        this.accountId = accountId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public static Account resultToAccount(ResultSet rs) throws SQLException {
        return new Account(rs.getInt("account_id"), rs.getString("username"),
                rs.getString("firstname"), rs.getString("lastname"));
    }

}
