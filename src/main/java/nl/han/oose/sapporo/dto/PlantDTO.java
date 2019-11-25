package nl.han.oose.sapporo.dto;

public class PlantDTO {
    private int id;
    private String name;
    private int waterUsage;
    private int growingTime;
    private float profit;
    private float purchasePrice;
    private int age;

    public PlantDTO() {
    }

    public PlantDTO(int id, String name, int waterUsage, int growingTime, float profit, float purchasePrice, int age) {
        this.id = id;
        this.name = name;
        this.waterUsage = waterUsage;
        this.growingTime = growingTime;
        this.profit = profit;
        this.purchasePrice = purchasePrice;
        this.age = age;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public int getId() {
        return id;
    }
}