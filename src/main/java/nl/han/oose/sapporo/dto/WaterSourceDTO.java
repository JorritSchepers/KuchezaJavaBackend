package nl.han.oose.sapporo.dto;

import java.util.Objects;

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

    public int getMaximumWater() {
        return maximumWater;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterSourceDTO that = (WaterSourceDTO) o;
        return id == that.id &&
                waterYield == that.waterYield &&
                maximumWater == that.maximumWater &&
                purchasePrice == that.purchasePrice &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, waterYield, maximumWater, purchasePrice, name);
    }
}
