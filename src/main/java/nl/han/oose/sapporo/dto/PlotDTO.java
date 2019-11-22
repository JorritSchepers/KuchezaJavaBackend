package nl.han.oose.sapporo.dto;

public class PlotDTO {
    private int ID;
    private int x;
    private int y;
    private float price;

    public PlotDTO(int ID, int x, int y, float price) {
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
}
