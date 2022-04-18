package Enums.GameEnums;

import java.util.ArrayList;

public enum ImprovementsEnum {
    CAMP(new ArrayList<>() {{
        add(ResourcesEnum.IVORY);
        add(ResourcesEnum.FUR);
        add(ResourcesEnum.DEER);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.FOREST);
        add(TerrainsEnum.TUNDRA);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.HILL);
    }}),
    FARM(new ArrayList<>() {{
        add(ResourcesEnum.WHEAT);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.AGRICULTURE);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.DESERT);
    }}),
    LUMBER_MILL(null, new ArrayList<>() {{
        add(TechnologiesEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.FOREST);
    }}),
    MINE(new ArrayList<>() {{
        add(ResourcesEnum.IRON);
        add(ResourcesEnum.COAL);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.GOLD);
        add(ResourcesEnum.SILVER);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.MINING);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.DESERT);
        add(TerrainsEnum.TUNDRA);
        add(TerrainsEnum.SNOW);
    }}),
    PASTURE(new ArrayList<>() {{
        add(ResourcesEnum.HORSE);
        add(ResourcesEnum.CATTLE);
        add(ResourcesEnum.SHEEP);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.DESERT);
        add(TerrainsEnum.TUNDRA);
        add(TerrainsEnum.HILL);
    }}),
    STONE_MINE(new ArrayList<>() {{
        add(ResourcesEnum.MARBLE);
    }}, new ArrayList<>() {{
        //TODO : carving stone ?
    }}, new ArrayList<>() {{
        add(TerrainsEnum.DESERT);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.TUNDRA);
        add(TerrainsEnum.HILL);
    }}),
    CULTIVATION(new ArrayList<>() {{
        add(ResourcesEnum.BANANA);
        add(ResourcesEnum.COTTON);
        add(ResourcesEnum.DYES);
        add(ResourcesEnum.INCENSE);
        add(ResourcesEnum.SILK);
        add(ResourcesEnum.SUGAR);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.CALENDAR);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.DESERT);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.FOREST);
        add(TerrainsEnum.JUNGLE);
        add(TerrainsEnum.MARSH);
        add(TerrainsEnum.FALLOUT);
    }}),
    TRADING_POST(null, new ArrayList<>() {{
        add(TechnologiesEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.DESERT);
        add(TerrainsEnum.TUNDRA);
    }}),
    COMPANY(null, new ArrayList<>() {{
        add(TechnologiesEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.DESERT);
        add(TerrainsEnum.PLAIN);
        add(TerrainsEnum.GRASSLAND);
        add(TerrainsEnum.TUNDRA);
        add(TerrainsEnum.SNOW);
    }});

    private final ArrayList<ResourcesEnum> isRequiredBy;
    private final ArrayList<TechnologiesEnum> requiredTechs;
    private final ArrayList<TerrainsEnum> canBeBuiltOn;

    ImprovementsEnum(ArrayList<ResourcesEnum> isRequiredBy, ArrayList<TechnologiesEnum> requiredTechs, ArrayList<TerrainsEnum> canBeBuiltOn) {
        this.isRequiredBy = isRequiredBy;
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
    }

    public boolean hasRequiredTechs(ArrayList<TechnologiesEnum> techs) {
        for (TechnologiesEnum list : this.requiredTechs)
            if (!techs.contains(list)) {
                return false;
            }
        return true;
    }

    public boolean getCanBeBuiltOn(TerrainsEnum land) {
        return this.canBeBuiltOn.contains(land);
    }
}
