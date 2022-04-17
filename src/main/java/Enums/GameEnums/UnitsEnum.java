package Enums.GameEnums;

public enum UnitsEnum {
    archer(70, CombatTypeEnum.archery, 4, 6, 2, 2, null, TechnologiesEnum.archery),

    chariotArcher(60, CombatTypeEnum.mounted, 3, 6, 2, 4, ResourcesEnum.horse, TechnologiesEnum.theWheel),

    scout(25, CombatTypeEnum.recon, 4, 0, 0, 2, null, null),

    settler(89, CombatTypeEnum.civilian, 0, 0, 0, 2, null, null),

    spearman(50, CombatTypeEnum.melee, 6, 0, 0, 2, null, null),

    warrior(40, CombatTypeEnum.melee, 6, 0, 0, 2, null, null),

    worker(70, CombatTypeEnum.civilian, 0, 0, 0, 2, null, null),

    catapult(100, CombatTypeEnum.siege, 4, 14, 2, 2, ResourcesEnum.iron, TechnologiesEnum.mathematics),

    horseman(80, CombatTypeEnum.mounted, 12, 0, 0, 4, ResourcesEnum.horse, TechnologiesEnum.horsebackRiding),

    swordsman(80, CombatTypeEnum.melee, 11, 0, 0, 2, ResourcesEnum.iron, TechnologiesEnum.ironWorking),

    crossbowman(120, CombatTypeEnum.archery, 6, 12, 2, 2, null, TechnologiesEnum.machinery),

    knight(150, CombatTypeEnum.mounted, 18, 0, 0, 3, ResourcesEnum.horse, TechnologiesEnum.chivalry),

    longSwordsman(150, CombatTypeEnum.melee, 18, 0, 0, 3, ResourcesEnum.iron, TechnologiesEnum.steel),

    pikeMan(100, CombatTypeEnum.melee, 10, 0, 0, 2, null, TechnologiesEnum.civilService),

    trebuchet(170, CombatTypeEnum.siege, 6, 20, 2, 2, ResourcesEnum.iron, TechnologiesEnum.physics),

    canon(250, CombatTypeEnum.siege, 10, 26, 2, 2, null, TechnologiesEnum.chemistry),

    cavalry(260, CombatTypeEnum.mounted, 25, 0, 0, 3, ResourcesEnum.horse, TechnologiesEnum.militaryScience),

    lancer(220, CombatTypeEnum.mounted, 22, 0, 0, 4, ResourcesEnum.horse, TechnologiesEnum.metallurgy),

    musketMan(120, CombatTypeEnum.gunpowder, 16, 0, 0, 2, null, TechnologiesEnum.gunpowder),

    rifleman(200, CombatTypeEnum.gunpowder, 25, 0, 0, 2, null, TechnologiesEnum.rifling),

    antiTankGun(300, CombatTypeEnum.gunpowder, 32, 0, 0, 2, null, TechnologiesEnum.replaceableParts),

    artillery(420, CombatTypeEnum.siege, 16, 32, 3, 2, null, TechnologiesEnum.dynamite),

    infantry(300, CombatTypeEnum.gunpowder, 36, 0, 0, 2, null, TechnologiesEnum.replaceableParts),

    panzer(450, CombatTypeEnum.armored, 60, 0, 0, 5, null, TechnologiesEnum.combustion),

    tank(450, CombatTypeEnum.armored, 50, 0, 0, 4, null, TechnologiesEnum.combustion);

    private final int cost;
    private final CombatTypeEnum combatType;
    private final int combatStrength;
    private final int rangedCombatStrength;
    private final int range;
    private final int movement;
    private final ResourcesEnum requiredResource;
    private final TechnologiesEnum requiredTech;

    UnitsEnum(int cost, CombatTypeEnum combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ResourcesEnum requiredResource, TechnologiesEnum requiredTech) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.requiredResource = requiredResource;
        this.requiredTech = requiredTech;
    }

    public int getCost() {
        return this.cost;
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

    public int getMovement() {
        return this.movement;
    }

    public ResourcesEnum getRequiredResource() {
        return this.requiredResource;
    }

    public TechnologiesEnum getRequiredTech() {
        return this.requiredTech;
    }
}
