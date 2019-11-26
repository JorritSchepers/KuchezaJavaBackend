package nl.han.oose.sapporo.dto;

import java.util.List;
import java.util.Objects;

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

    public List<PlotDTO> getPlots(){
        return plots;
    }

    public void setPlots(List<PlotDTO> plots) {
        this.plots = plots;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FarmDTO farmDTO = (FarmDTO) o;
        return farmID == farmDTO.farmID &&
                ownerID == farmDTO.ownerID &&
                Objects.equals(plots, farmDTO.plots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(farmID, ownerID, plots);
    }
}
