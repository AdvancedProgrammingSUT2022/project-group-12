package Enums;

import java.util.ArrayList;

public enum ImprovementsEnum {
    camp(new ArrayList<>() {{
        add(ResourcesEnum.ivory);
        add(ResourcesEnum.fur);
        add(ResourcesEnum.deer);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.trapping);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.forest);
        add(TerrainsEnum.tundra);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.hill);
    }}),
    farm(new ArrayList<>() {{
        add(ResourcesEnum.wheat);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.agriculture);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.desert);
    }}),
    lumberMill(null, new ArrayList<>() {{
        add(TechnologiesEnum.engineering);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.forest);
    }}),
    mine(new ArrayList<>() {{
        add(ResourcesEnum.iron);
        add(ResourcesEnum.coal);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.gold);
        add(ResourcesEnum.silver);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.mining);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.desert);
        add(TerrainsEnum.tundra);
        add(TerrainsEnum.snow);
    }}),
    pasture(new ArrayList<>() {{
        add(ResourcesEnum.horse);
        add(ResourcesEnum.cattle);
        add(ResourcesEnum.sheep);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.animalHusbandry);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.desert);
        add(TerrainsEnum.tundra);
        add(TerrainsEnum.hill);
    }}),
    stoneMine(new ArrayList<>() {{
        add(ResourcesEnum.marble);
    }}, new ArrayList<>() {{
        //TODO : carving stone ?
    }}, new ArrayList<>() {{
        add(TerrainsEnum.desert);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.tundra);
        add(TerrainsEnum.hill);
    }}),
    cultivation(new ArrayList<>() {{
        add(ResourcesEnum.banana);
        add(ResourcesEnum.cotton);
        add(ResourcesEnum.dyes);
        add(ResourcesEnum.incense);
        add(ResourcesEnum.silk);
        add(ResourcesEnum.sugar);
    }}, new ArrayList<>() {{
        add(TechnologiesEnum.calendar);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.desert);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.forest);
        add(TerrainsEnum.jungle);
        add(TerrainsEnum.marsh);
        add(TerrainsEnum.fallout);
    }}),
    tradingPost(null, new ArrayList<>() {{
        add(TechnologiesEnum.trapping);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.desert);
        add(TerrainsEnum.tundra);
    }}),
    company(null, new ArrayList<>() {{
        add(TechnologiesEnum.engineering);
    }}, new ArrayList<>() {{
        add(TerrainsEnum.desert);
        add(TerrainsEnum.plain);
        add(TerrainsEnum.grassland);
        add(TerrainsEnum.tundra);
        add(TerrainsEnum.snow);
    }});

    private final ArrayList<ResourcesEnum> isRequiredBy;
    private final ArrayList<TechnologiesEnum> requiredTechs;
    private final ArrayList<TerrainsEnum> canBeBuiltOn;

    ImprovementsEnum(ArrayList<ResourcesEnum> isRequiredBy, ArrayList<TechnologiesEnum> requiredTechs, ArrayList<TerrainsEnum> canBeBuiltOn) {
        this.isRequiredBy = isRequiredBy;
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
    }

    public ArrayList<ResourcesEnum> getIsRequiredBy() {
        return this.isRequiredBy;
    }

    public ArrayList<TechnologiesEnum> getRequiredTechs() {
        return this.requiredTechs;
    }

    public ArrayList<TerrainsEnum> getCanBeBuiltOn() {
        return this.canBeBuiltOn;
    }
}
