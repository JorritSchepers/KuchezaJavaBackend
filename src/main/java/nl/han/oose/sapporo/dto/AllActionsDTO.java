package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class AllActionsDTO {
    private ArrayList<ActionDTO> actions;

    public ArrayList<ActionDTO> getActions() {
        return actions;
    }

    public AllActionsDTO(ArrayList<ActionDTO> actions) {
        this.actions = actions;
    }
}
