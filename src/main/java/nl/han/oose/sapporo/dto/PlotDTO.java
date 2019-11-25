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

    private int getID() {
        return ID;
    }
}
