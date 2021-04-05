package github.jackhenry.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateDrugDTO {
    private int id;
    private String drugName;
    private String concentration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }


}
