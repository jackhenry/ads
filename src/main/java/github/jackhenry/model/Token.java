package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Token {
    @XmlElement
    public String token;
    @XmlElement
    public int accountId;

    public Token(String token, int accountId) {
        this.token = token;
        this.accountId = accountId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public static Token resultToToken(ResultSet rs) throws SQLException {
        return new Token(rs.getString("token"), rs.getInt("account_id"));
    }
}
