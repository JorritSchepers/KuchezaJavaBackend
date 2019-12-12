package nl.han.oose.sapporo.dto;

public class AnimalDTO {

    private int ID;
    private String name;
    private int waterUsage;
    private int maximumWater;
    private int productionTime;
    private float profit;
    private float purchasePrice;

    public AnimalDTO() { }

    public AnimalDTO(int ID, String name, int waterUsage, int maximumWater, int productionTime, float profit, float purchasePrice) {
        this.ID = ID;
        this.name = name;
        this.waterUsage = waterUsage;
        this.maximumWater = maximumWater;
        this.productionTime = productionTime;
        this.profit = profit;
        this.purchasePrice = purchasePrice;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public int getID() {
        return ID;
    }
}
