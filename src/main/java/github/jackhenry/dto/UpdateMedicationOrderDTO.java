package github.jackhenry.dto;

public class UpdateMedicationOrderDTO extends CreateMedicationOrderDTO {
    private int orderId;

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

}
