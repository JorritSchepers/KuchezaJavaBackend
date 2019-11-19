package nl.han.oose.sapporo.dto;

public class PlantDTO {
    int id;
    String name;
    int waterUsage;
    int growingTime;
    int profit;
    int purchasePrice;
    int age;

    public PlantDTO(int id, String name, int waterUsage, int growingTime, int profit, int purchasePrice, int age) {
        this.id = id;
        this.name = name;
        this.waterUsage = waterUsage;
        this.growingTime = growingTime;
        this.profit = profit;
        this.purchasePrice = purchasePrice;
        this.age = age;
    }
}
