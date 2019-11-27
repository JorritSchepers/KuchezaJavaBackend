package nl.han.oose.sapporo.dto;

public class PlotDTO {
    private int ID;
    private int x;
    private int y;
    private boolean purchased;
    private int animalID;
    private int waterManagerID;
    private int plantID;
    private float price;

    public PlotDTO() { }

    public PlotDTO(int x, int y, float price, boolean purchased) {
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
    }

    public PlotDTO(int ID, int x, int y, int animalID, int waterManagerID, int plantID, float price, boolean purchased) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalID = animalID;
        this.waterManagerID = waterManagerID;
        this.plantID = plantID;
        this.price = price;
        this.purchased = purchased;
    }

    public PlotDTO(int ID, int x, int y, int animalID, int waterManagerID, int plantID, float price) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalID = animalID;
        this.waterManagerID = waterManagerID;
        this.plantID = plantID;
        this.price = price;
    }

    public PlotDTO(int ID, int x, int y, float price, boolean purchased) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
    }

    public PlotDTO(int ID, int x, int y, float price) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
