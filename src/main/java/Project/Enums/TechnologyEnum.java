package Project.Enums;

import Project.App;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public enum TechnologyEnum {
    RESET(0, new ArrayList<>(),"-url-"),
    AGRICULTURE(20, new ArrayList<>(),"-url-"),
    ANIMAL_HUSBANDRY(35, new ArrayList<>(List.of(AGRICULTURE)),"-url-"),
    ARCHERY(35, new ArrayList<>(List.of(AGRICULTURE)),"-url-"),
    MINING(35, new ArrayList<>(List.of(AGRICULTURE)),"-url-"),
    BRONZE_WORKING(55, new ArrayList<>(List.of(MINING)),"-url-"),
    POTTERY(35, new ArrayList<>(List.of(AGRICULTURE)),"-url-"),
    CALENDAR(70, new ArrayList<>(List.of(POTTERY)),"-url-"),
    MASONRY(55, new ArrayList<>(List.of(MINING)),"-url-"),
    THE_WHEEL(55, new ArrayList<>(List.of(ANIMAL_HUSBANDRY)),"-url-"),
    TRAPPING(55, new ArrayList<>(List.of(ANIMAL_HUSBANDRY)),"-url-"),
    WRITING(55, new ArrayList<>(List.of(POTTERY)),"-url-"),
    CONSTRUCTION(100, new ArrayList<>(List.of(MASONRY)),"-url-"),
    HORSEBACK_RIDING(100, new ArrayList<>(List.of(THE_WHEEL)),"-url-"),
    IRON_WORKING(150, new ArrayList<>(List.of(BRONZE_WORKING)),"-url-"),
    MATHEMATICS(100, new ArrayList<>(List.of(THE_WHEEL)),"-url-"),
    PHILOSOPHY(100, new ArrayList<>(List.of(WRITING)),"-url-"),
    CIVIL_SERVICE(400, new ArrayList<>(List.of(PHILOSOPHY, TRAPPING)),"-url-"),
    CURRENCY(250, new ArrayList<>(List.of(MATHEMATICS)),"-url-"),
    CHIVALRY(440, new ArrayList<>(List.of(CIVIL_SERVICE, HORSEBACK_RIDING, CURRENCY)),"-url-"),
    THEOLOGY(250, new ArrayList<>(List.of(CALENDAR, PHILOSOPHY)),"-url-"),
    EDUCATION(440, new ArrayList<>(List.of(THEOLOGY)),"-url-"),
    ENGINEERING(250, new ArrayList<>(List.of(MATHEMATICS, CONSTRUCTION)),"-url-"),
    MACHINERY(440, new ArrayList<>(List.of(ENGINEERING)),"-url-"),
    METAL_CASTING(240, new ArrayList<>(List.of(IRON_WORKING)),"-url-"),
    PHYSICS(440, new ArrayList<>(List.of(IRON_WORKING)),"-url-"),
    STEEL(440, new ArrayList<>(List.of(METAL_CASTING)),"-url-"),
    TECHNO(250, new ArrayList<>(List.of(CALENDAR, PHILOSOPHY)),"-url-"),
    ACOUSTICS(650, new ArrayList<>(List.of(EDUCATION)),"-url-"),
    ARCHAEOLOGY(1300, new ArrayList<>(List.of(ACOUSTICS)),"-url-"),
    BANKING(650, new ArrayList<>(List.of(EDUCATION, CHIVALRY)),"-url-"),
    GUNPOWDER(680, new ArrayList<>(List.of(PHYSICS, STEEL)),"-url-"),
    CHEMISTRY(900, new ArrayList<>(List.of(GUNPOWDER)),"-url-"),
    PRINTING_PRESS(650, new ArrayList<>(List.of(MACHINERY, PHYSICS)),"-url-"),
    ECONOMICS(900, new ArrayList<>(List.of(BANKING, PRINTING_PRESS)),"-url-"),
    FERTILIZER(1300, new ArrayList<>(List.of(CHEMISTRY)),"-url-"),
    METALLURGY(900, new ArrayList<>(List.of(GUNPOWDER)),"-url-"),
    MILITARY_SCIENCE(1300, new ArrayList<>(List.of(ECONOMICS, CHEMISTRY)),"-url-"),
    RIFLING(1425, new ArrayList<>(List.of(METALLURGY)),"-url-"),
    SCIENTIFIC_THEORY(1300, new ArrayList<>(List.of(ACOUSTICS)),"-url-"),
    BIOLOGY(1680, new ArrayList<>(List.of(ARCHAEOLOGY, SCIENTIFIC_THEORY)),"-url-"),
    STEAM_POWER(1680, new ArrayList<>(List.of(SCIENTIFIC_THEORY, MILITARY_SCIENCE)),"-url-"),
    REPLACEABLE_PARTS(1900, new ArrayList<>(List.of(STEAM_POWER)),"-url-"),
    RAILROAD(1900, new ArrayList<>(List.of(STEAM_POWER)),"-url-"),
    DYNAMITE(1900, new ArrayList<>(List.of(FERTILIZER, RIFLING)),"-url-"),
    COMBUSTION(2200, new ArrayList<>(List.of(REPLACEABLE_PARTS, RAILROAD, DYNAMITE)),"-url-"),
    ELECTRICITY(1900, new ArrayList<>(List.of(BIOLOGY, STEAM_POWER)),"-url-"),
    RADIO(2200, new ArrayList<>(List.of(ELECTRICITY)),"-url-"),
    TELEGRAPH(2200, new ArrayList<>(List.of(ELECTRICITY)),"-url-");

    private final int cost;
    private final ArrayList<TechnologyEnum> prerequisiteTechs;

    public ArrayList<TechnologyEnum> getPrerequisiteTechs() {
        return prerequisiteTechs;
    }

    TechnologyEnum(int cost, ArrayList<TechnologyEnum> prerequisiteTechs,String assetUrl) {
        this.cost = cost;
        this.prerequisiteTechs = prerequisiteTechs;
        this.assetUrl = assetUrl;
    }
    private final String assetUrl;
    public Image getImage() {
        return new Image(App.class.getResource("/images/assets/technologies/" + assetUrl).toExternalForm());
    }


    public int getCost() {
        return this.cost;
    }

    public ArrayList<TechnologyEnum> leadsToTech() {
        ArrayList<TechnologyEnum> techs = new ArrayList<>();
        for (TechnologyEnum tech : TechnologyEnum.values()) {
            if (tech.prerequisiteTechs.contains(this)) {
                techs.add(tech);
            }
        }
        return techs;
    }
}