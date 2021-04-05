package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Drug {
    @XmlElement
    public int id;
    @XmlElement
    public String drugName;
    @XmlElement
    public String concentration;

    public Drug(int id, String drugName, String concentration) {
        this.id = id;
        this.drugName = drugName;
        this.concentration = concentration;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugName() {
        return this.drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getConcentration() {
        return this.concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public static Drug resultToDrug(ResultSet rs) throws SQLException {
        return new Drug(rs.getInt("drug_id"), rs.getString("drug_name"),
                rs.getString("concentration"));
    }


}
