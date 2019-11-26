package nl.han.oose.sapporo.dto;

import java.util.ArrayList;
import java.util.List;

public class FarmDTO {

    private int farmID;
    private int ownerID;
    List<PlotDTO> plots;

    public FarmDTO(){}

    public FarmDTO(int farmID, int ownerID){
        this.farmID = farmID;
        this.ownerID = ownerID;
    }

    public int getFarmID() { return farmID;}

    public int getOwnerID() { return ownerID;}

    public List<PlotDTO> getPlots(){
        return plots;
    }

    public void setPlots(List<PlotDTO> plots) {
        this.plots = plots;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}
