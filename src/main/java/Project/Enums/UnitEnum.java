package Project.Enums;

import Project.Client.App;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public enum UnitEnum {
    ARCHER(70, CombatTypeEnum.ARCHERY, 4, 6, 2, 2.0f, ResourceEnum.RESET, TechnologyEnum.ARCHERY),

    CHARIOT_ARCHER(60, CombatTypeEnum.MOUNTED, 3, 6, 2, 4.0f, ResourceEnum.HORSE, TechnologyEnum.THE_WHEEL),

    SCOUT(25, CombatTypeEnum.RECON, 4, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.RESET),

    SETTLER(89, CombatTypeEnum.CIVILIAN, 0, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.RESET),

    SPEARMAN(50, CombatTypeEnum.MELEE, 6, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.RESET),

    WARRIOR(40, CombatTypeEnum.MELEE, 6, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.RESET),

    WORKER(70, CombatTypeEnum.CIVILIAN, 0, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.RESET),

    CATAPULT(100, CombatTypeEnum.SIEGE, 4, 14, 2, 2.0f, ResourceEnum.IRON, TechnologyEnum.MATHEMATICS),

    HORSEMAN(80, CombatTypeEnum.MOUNTED, 12, 0, 0, 4.0f, ResourceEnum.HORSE, TechnologyEnum.HORSEBACK_RIDING),

    SWORDSMAN(80, CombatTypeEnum.MELEE, 11, 0, 0, 2.0f, ResourceEnum.IRON, TechnologyEnum.IRON_WORKING),

    CROSSBOWMAN(120, CombatTypeEnum.ARCHERY, 6, 12, 2, 2.0f, ResourceEnum.RESET, TechnologyEnum.MACHINERY),

    KNIGHT(150, CombatTypeEnum.MOUNTED, 18, 0, 0, 3.0f, ResourceEnum.HORSE, TechnologyEnum.CHIVALRY),

    LONG_SWORDSMAN(150, CombatTypeEnum.MELEE, 18, 0, 0, 3.0f, ResourceEnum.IRON, TechnologyEnum.STEEL),

    PIKE_MAN(100, CombatTypeEnum.MELEE, 10, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.CIVIL_SERVICE),

    TREBUCHET(170, CombatTypeEnum.SIEGE, 6, 20, 2, 2.0f, ResourceEnum.IRON, TechnologyEnum.PHYSICS),

    CANON(250, CombatTypeEnum.SIEGE, 10, 26, 2, 2.0f, ResourceEnum.RESET, TechnologyEnum.CHEMISTRY),

    CAVALRY(260, CombatTypeEnum.MOUNTED, 25, 0, 0, 3.0f, ResourceEnum.HORSE, TechnologyEnum.MILITARY_SCIENCE),

    LANCER(220, CombatTypeEnum.MOUNTED, 22, 0, 0, 4.0f, ResourceEnum.HORSE, TechnologyEnum.METALLURGY),

    MUSKET_MAN(120, CombatTypeEnum.GUNPOWDER, 16, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.GUNPOWDER),

    RIFLEMAN(200, CombatTypeEnum.GUNPOWDER, 25, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.RIFLING),

    ANTI_TANK_GUN(300, CombatTypeEnum.GUNPOWDER, 32, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.REPLACEABLE_PARTS),

    ARTILLERY(420, CombatTypeEnum.SIEGE, 16, 32, 3, 2.0f, ResourceEnum.RESET, TechnologyEnum.DYNAMITE),

    INFANTRY(300, CombatTypeEnum.GUNPOWDER, 36, 0, 0, 2.0f, ResourceEnum.RESET, TechnologyEnum.REPLACEABLE_PARTS),

    PANZER(450, CombatTypeEnum.ARMORED, 60, 0, 0, 5.0f, ResourceEnum.RESET, TechnologyEnum.COMBUSTION),

    TANK(450, CombatTypeEnum.ARMORED, 50, 0, 0, 4.0f, ResourceEnum.RESET, TechnologyEnum.COMBUSTION);

    private final int productionCost;
    private final CombatTypeEnum combatType;
    private final int combatStrength;
    private final int rangedCombatStrength;
    private final int range;
    private final double movement;
    private final ResourceEnum requiredResource;
    private final TechnologyEnum requiredTech;
    private final Image assetImage;

    UnitEnum(int productionCost, CombatTypeEnum combatType, int combatStrength, int rangedCombatStrength, int range, double movement, ResourceEnum requiredResource, TechnologyEnum requiredTech) {
        this.productionCost = productionCost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.requiredResource = requiredResource;
        this.requiredTech = requiredTech;
//        System.out.println("printing in unit enum: " + this.name().toLowerCase());
//        System.out.println(App.class.getResource("/images/units/Units/" + this.name().toLowerCase() + ".png"));
        if (App.class.getResource("/images/units/Units/" + this.name().toLowerCase() + ".png") != null)
            this.assetImage = new Image(App.class.getResource("/images/units/Units/" + this.name().toLowerCase() + ".png").toExternalForm());
        else
            this.assetImage = new Image(App.class.getResource("/images/units/Units/impi.png").toExternalForm());
    }

    public boolean isACombatUnit() {
        return combatType != CombatTypeEnum.CIVILIAN;
    }

    public int getProductionCost() {
        return this.productionCost;
    }

    public int calculateGoldCost() {
        return this.productionCost / 5;
    }

    public CombatTypeEnum getCombatType() {
        return this.combatType;
    }

    public int getCombatStrength() {
        return this.combatStrength;
    }

    public int getRangedCombatStrength() {
        return this.rangedCombatStrength;
    }

    public int getRange() {
        return this.range;
    }

    public double getMovement() {
        return this.movement;
    }

    public ResourceEnum getRequiredResource() {
        return this.requiredResource;
    }

    public TechnologyEnum getRequiredTech() {
        return this.requiredTech;
    }

    public boolean canFireIndirectly() {
        return this == ARTILLERY;
    }

    public boolean hasLimitedVisibility() {
        return this.getCombatType() == CombatTypeEnum.SIEGE || this == UnitEnum.PANZER;
    }
    public boolean hasRequiredTech(ArrayList<TechnologyEnum> technologyEnums){
        return technologyEnums.contains(this.requiredTech);
    }

    public boolean canMoveAfterAttack() {
        return List.of(HORSEMAN, LANCER, KNIGHT, CAVALRY, PANZER, TANK).contains(this);
    }

    public boolean hasTerrainDefensiveBonusPenalty() {
        return List.of(TANK, PANZER, ARTILLERY, LANCER, CAVALRY, CANON, TREBUCHET, KNIGHT, HORSEMAN, CATAPULT, CHARIOT_ARCHER).contains(this);
    }

    public boolean requiresSetup() {
        return this.getCombatType() == CombatTypeEnum.SIEGE;
    }

    public boolean isRangedUnit() {
        return this.range > 0;
    }


    public int getBonusVsMounted() {
        return List.of(PIKE_MAN, SPEARMAN).contains(this) ? 50 : 0;
    }

    public int getBonusVsCity() {
        if (this.getCombatType() == CombatTypeEnum.SIEGE) return 200;
        else if (this.getCombatType() == CombatTypeEnum.MOUNTED && this != CHARIOT_ARCHER) return -33;
        else return 0;
    }

    public Image getAssetImage() {
        return assetImage;
    }
    // todo: what if target of archer is on hill?
    // todo: reveal bug
}
