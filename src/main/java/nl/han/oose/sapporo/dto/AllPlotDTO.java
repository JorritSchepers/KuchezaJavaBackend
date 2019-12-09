package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class AllPlotDTO {
    private ArrayList<PlotDTO> plots;

    public AllPlotDTO(ArrayList<PlotDTO> plots) {
        this.plots = plots;
    }

    public ArrayList<PlotDTO> getPlots() {
        return plots;
    }
}
