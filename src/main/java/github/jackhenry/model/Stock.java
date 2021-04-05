package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stock {
    @XmlElement
    public int id;
    @XmlElement
    public int quantity;
    @XmlElement
    public int threshold;
    @XmlElement
    public Timestamp expirationDate;

    public Stock(int id, int quantity, int threshold, Timestamp expirationDate) {
        this.id = id;
        this.quantity = quantity;
        this.threshold = threshold;
        this.expirationDate = expirationDate;
    }

    public int getDrugId() {
        return this.id;
    }

    public void setDrugId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Timestamp getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static Stock resultToStockItem(ResultSet rs) throws SQLException {
        return new Stock(rs.getInt("drug_id"), rs.getInt("quantity"), rs.getInt("threshold"),
                rs.getTimestamp("drug_expiration"));
    }
}
