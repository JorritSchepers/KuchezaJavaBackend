package nl.han.oose.sapporo.dto;

public class AnimalDTO {
    private int id;
    private String name;
    private int waterUsage;
    private int maximumWater;
    private int productionTime;
    private float profit;
    private float purchasePrice;

    public AnimalDTO() {
    }

    public AnimalDTO(int id, String name, int waterUsage, int maximumWater, int productionTime, float profit, float purchasePrice) {
        this.id = id;
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

    public int getId() {
        return id;
    }

}
