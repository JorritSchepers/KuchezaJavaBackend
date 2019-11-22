package nl.han.oose.sapporo.dto;

public class PlotDTO {
    private int ID;
    private int x;
    private int y;
    private float price;
    private boolean purchased;

    public PlotDTO() {

    }

    public PlotDTO(int x, int y, float price, boolean purchased) {
        this.x = x;
        this.y = y;
        this.price = price;
        this.purchased = purchased;
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
