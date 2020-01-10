package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllWaterSourceDTO;
import nl.han.oose.sapporo.dto.WaterSourceDTO;

public interface IBuildingDAO {
    AllWaterSourceDTO getAllWaterSources();

    WaterSourceDTO getWaterSource(int waterSourceID);
}
