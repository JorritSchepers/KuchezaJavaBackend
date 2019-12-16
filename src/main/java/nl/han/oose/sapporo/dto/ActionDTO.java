package nl.han.oose.sapporo.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class ActionDTO {
    private int userID;
    private int actionID;
    private Timestamp dateOfAction;
    private String affectedItem;
    private int currentWater;
    private int currentMoney;

    public ActionDTO(int userID, int actionID, Timestamp dateOfAction, String affectedItem, int currentWater, int currentMoney) {
        this.userID = userID;
        this.actionID = actionID;
        this.dateOfAction = dateOfAction;
        this.affectedItem = affectedItem;
        this.currentWater = currentWater;
        this.currentMoney = currentMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionDTO)) return false;
        ActionDTO actionDTO = (ActionDTO) o;
        return userID == actionDTO.userID &&
                actionID == actionDTO.actionID &&
                currentWater == actionDTO.currentWater &&
                currentMoney == actionDTO.currentMoney &&
                Objects.equals(affectedItem, actionDTO.affectedItem);
    }
}
