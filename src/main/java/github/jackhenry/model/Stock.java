package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stock {
    @XmlElement
    private int drugId;
    @XmlElement
    private int quantity;
    @XmlElement
    private int threshold;
    @XmlElement
    private Timestamp expirationDate;

    public Stock(int drugId, int quantity, int threshold, Timestamp expirationDate) {
        this.drugId = drugId;
        this.quantity = quantity;
        this.threshold = threshold;
        this.expirationDate = expirationDate;
    }

    public int getDrugId() {
        return this.drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
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
                rs.getTimestamp("expiration_date"));
    }
}
