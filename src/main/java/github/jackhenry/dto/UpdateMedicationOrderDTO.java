package github.jackhenry.dto;

public class UpdateMedicationOrderDTO extends CreateMedicationOrderDTO {
    private int id;

    public int getOrderId() {
        return this.id;
    }

    public void setOrderId(int id) {
        this.id = id;
    }

}
