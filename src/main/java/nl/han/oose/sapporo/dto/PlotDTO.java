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
    private int age;

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

    public PlotDTO(int ID, int x, int y, int animalID, int waterManagerID, int plantID, float price, boolean purchased, int age) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalID = animalID;
        this.waterManagerID = waterManagerID;
        this.plantID = plantID;
        this.price = price;
        this.purchased = purchased;
        this.age = age;
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

    public PlotDTO(int ID, int x, int y, int animalID, int waterManagerID, int plantID, float price, int age) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalID = animalID;
        this.waterManagerID = waterManagerID;
        this.plantID = plantID;
        this.price = price;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public int getWaterManagerID() {
        return waterManagerID;
    }

    public void setWaterManagerID(int waterManagerID) {
        this.waterManagerID = waterManagerID;
    }

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
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
