package Server.Utils;

import Project.Enums.BuildingEnum;

import java.util.HashMap;

public class BuildingNotesLoader {
    private static HashMap<BuildingEnum, BuildingNotes> buildingNotes = new HashMap<>();
    static {
        for (BuildingEnum be:
             BuildingEnum.values()) {
            buildingNotes.put(be, BuildingNoteEnum.valueOf(be.name()).getNote());
        }
    }

    public static HashMap<BuildingEnum, BuildingNotes> getBuildingNotes() {
        return buildingNotes;
    }
}
