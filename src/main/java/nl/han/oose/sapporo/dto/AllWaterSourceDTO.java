package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class AllWaterSourceDTO {
    private ArrayList<WaterSourceDTO> waterSources;

    public AllWaterSourceDTO(ArrayList<WaterSourceDTO> waterSources) {
        this.waterSources = waterSources;
    }

    public ArrayList<WaterSourceDTO> getWaterSources() {
        return waterSources;
    }
}
