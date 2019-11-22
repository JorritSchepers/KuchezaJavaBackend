package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class FarmDTO {

    private int farmID;
    private int ownerID;
    ArrayList<PlotDTO> plots;

    public FarmDTO(){}

    public FarmDTO(int farmID, int ownerID){
        this.farmID = farmID;
        this.ownerID = ownerID;
    }

    public int getFarmID() { return farmID;}

    public int getOwnerID() { return ownerID;}

    public ArrayList<PlotDTO> getPlots(){
        return plots;
    }
}
