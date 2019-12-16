package nl.han.oose.sapporo.dto;

public class PlantDTO {
    private int ID;
    private String name;
    private int waterUsage;
    private int growingTime;
    private float profit;
    private float purchasePrice;
    private int maximumWater;

    public PlantDTO() { }

    public PlantDTO(int ID, String name, int waterUsage, int growingTime, float profit, float purchasePrice, int maximumWater) {
        this.ID = ID;
        this.name = name;
        this.waterUsage = waterUsage;
        this.growingTime = growingTime;
        this.profit = profit;
        this.purchasePrice = purchasePrice;
        this.maximumWater = maximumWater;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public int getID() {
        return ID;
    }

    public float getProfit() {
        return profit;
    }
}
