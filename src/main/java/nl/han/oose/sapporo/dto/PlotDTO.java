package nl.han.oose.sapporo.dto;

import java.util.Objects;

public class PlotDTO {
    private int ID;
    private int x;
    private int y;
    private int animalId;
    private int waterManagerId;
    private int plantID;
    private float price;

    public PlotDTO(int ID, int x, int y, int animalId, int waterManagerId, int plantID, float price) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalId = animalId;
        this.waterManagerId = waterManagerId;
        this.plantID = plantID;
        this.price = price;
    }

    public PlotDTO(int ID, int x, int y, float price) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
    }

    private int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlotDTO)) return false;
        PlotDTO plotDTO = (PlotDTO) o;
        return getID() == plotDTO.getID() &&
                x == plotDTO.x &&
                y == plotDTO.y &&
                Float.compare(plotDTO.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), x, y, price);
    }
}
