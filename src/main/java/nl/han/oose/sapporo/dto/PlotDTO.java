package nl.han.oose.sapporo.dto;

import java.util.Objects;

public class PlotDTO {
    private int ID;
    private int x;
    private int y;
    private boolean purchased;
    private int animalID;
    private int waterManagerID;
    private int plantID;
    private int waterAvailable;
    private float price;
    private int age;
    private String status = "";

    public PlotDTO() { }

    public PlotDTO(int x, int y, float price, boolean purchased) {
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
    }

    public PlotDTO(int ID, int x, int y, int animalID, int waterManagerID, int plantID, float price, boolean purchased, int age, int waterAvailable) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalID = animalID;
        this.waterManagerID = waterManagerID;
        this.plantID = plantID;
        this.price = price;
        this.purchased = purchased;
        this.age = age;
        this.waterAvailable = waterAvailable;
    }

    public PlotDTO(int ID, int x, int y, int animalID, int waterManagerID, int plantID, float price, boolean purchased, int age, int waterAvailable, String status) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.animalID = animalID;
        this.waterManagerID = waterManagerID;
        this.plantID = plantID;
        this.price = price;
        this.purchased = purchased;
        this.age = age;
        this.waterAvailable = waterAvailable;
        this.status =status;
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

    public PlotDTO(int ID, int x, int y, float price, boolean purchased, int waterAvailable) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
        this.waterAvailable = waterAvailable;
    }

    public PlotDTO(int ID, int x, int y, float price, int waterAvailable) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
        this.waterAvailable = waterAvailable;
    }

    public PlotDTO(int ID, int x, int y, int price, boolean purchased, int objectAge) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
        this.age = objectAge;
    }

    public PlotDTO(int plotID, int x, int y, int price, boolean purchased, int objectAge, int waterAvailable, String status) {
        this.ID = plotID;
        this.x = x;
        this.y = y;
        this.price = price;
        this.age = objectAge;
        this.purchased = purchased;
        this.waterAvailable = waterAvailable;
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public int getWaterAvailable() { return waterAvailable; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlotDTO plotDTO = (PlotDTO) o;
        return ID == plotDTO.ID &&
                x == plotDTO.x &&
                y == plotDTO.y &&
                purchased == plotDTO.purchased &&
                animalID == plotDTO.animalID &&
                waterManagerID == plotDTO.waterManagerID &&
                plantID == plotDTO.plantID &&
                waterAvailable == plotDTO.waterAvailable &&
                Float.compare(plotDTO.price, price) == 0 &&
                age == plotDTO.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, x, y, purchased, animalID, waterManagerID, plantID, waterAvailable, price, age);
    }
}
