package nl.han.oose.sapporo.dto;

import java.util.Objects;

public class PlotDTO {
    private int ID;
    private int x;
    private int y;
    private boolean purchased;
    private int animalId;
    private int waterManagerId;
    private int plantID;
    private float price;

    public PlotDTO() {

    }

    public PlotDTO(int x, int y, float price, boolean purchased) {
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
    }

    public PlotDTO(int ID, int x, int y, int animalId, int waterManagerId, int plantID, float price) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalId = animalId;
        this.waterManagerId = waterManagerId;
        this.plantID = plantID;
        this.price = price;
    }

    public PlotDTO(int iD, int x, int y, float price, boolean purchased) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
    }

    public PlotDTO(int iD, int x, int y, float price) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public float getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
