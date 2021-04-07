package github.jackhenry.dto;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddStockDTO {
    @XmlElement
    private int quantity;
    @XmlElement
    private int threshold;
    @XmlElement
    private int id;
    @XmlElement
    private Timestamp expirationDate;

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

    public int getDrugId() {
        return this.id;
    }

    public void setDrugId(int id) {
        this.id = id;
    }

    public Timestamp getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }


}
