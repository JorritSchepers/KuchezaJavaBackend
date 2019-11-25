package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class FarmDTO {
    private int farmID;
    private int ownerID;
    private ArrayList<PlotDTO> plots = new ArrayList<>();

    public FarmDTO(int farmID, int ownerID) {
        this.farmID = farmID;
        this.ownerID = ownerID;
    }

    public void setPlots(ArrayList<PlotDTO> plots) {
        this.plots = plots;
    }

    public int getFarmID() {
        return farmID;
    }
}
