package nl.han.oose.sapporo.dto;

public class WaterSourceDTO {
    private int id;
    private int waterYield;
    private int maximumWater;
    private int purchasePrice;
    private String name;

    public WaterSourceDTO(int id, int waterYield, int maximumWater, int purchasePrice, String name) {
        this.id = id;
        this.waterYield = waterYield;
        this.maximumWater = maximumWater;
        this.purchasePrice = purchasePrice;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaterYield() {
        return waterYield;
    }

    public void setWaterYield(int waterYield) {
        this.waterYield = waterYield;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaximumWater() {
        return maximumWater;
    }

    public void setMaximumWater(int maximumWater) {
        this.maximumWater = maximumWater;
    }
}
