package Project.Models.Units;

import Project.Controllers.GameController;
import Project.Enums.CombatTypeEnum;
import Project.Enums.FeatureEnum;
import Project.Enums.UnitEnum;
import Project.Enums.UnitStates;
import Project.Models.Cities.City;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Production;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.Tile;
import Project.Utils.Constants;
import Project.Views.GameView;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public abstract class Unit extends Production {
    protected UnitEnum type;
    protected Civilization civ;
    protected double availableMoveCount;
    protected Location location;
    protected int health = Constants.UNIT_FULL_HEALTH;
    protected ArrayList<Tile> pathShouldCross;
    protected UnitStates state;
    protected Group graphicUnit;

    public Unit(UnitEnum type, Civilization civ, Location location) {
        this.type = type;
        this.civ = civ;
        this.pathShouldCross = new ArrayList<>();
        this.resetMovementCount();
        this.location = location;
        state = UnitStates.AWAKE;
    }

    // todoLater: integrate unit newing with Civ class
    public static Unit constructUnitFromEnum(UnitEnum unitEnum, Civilization civ, Location location) {
        if (unitEnum.isACombatUnit()) {
            if (unitEnum.isRangedUnit()) {
                return new RangedUnit(unitEnum, civ, location);
            } else {
                return new NonRangedUnit(unitEnum, civ, location);
            }
        } else {
            return new NonCombatUnit(unitEnum, civ, location);
        }
    }

    public void resetMovementCount() {
        this.availableMoveCount = type.getMovement();
    }

    public Unit(UnitEnum type, Civilization civ, Location location, int productionCost) {
        super(productionCost);
        this.type = type;
        this.civ = civ;
        this.pathShouldCross = new ArrayList<>();
        this.resetMovementCount();
        this.location = location;
        state = UnitStates.AWAKE;
    }

    public int calculateDamage(double strengthDiff) {
        Random random = new Random();
        double random_number = (double) random.nextInt(75, 125) / 100;
        //debug for test
//        random_number = 1.25;
        return (int) Math.ceil(25 * exp(strengthDiff / (25.0 * random_number)));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double calculateCombatStrength(Unit unit, Tile itsTile, String combatstrengh, Unit enemyUnit) {
        double strength;
        UnitEnum unitType = unit.getType();
        if (combatstrengh.equals("combatstrength")) {
            strength = unitType.getCombatStrength();
        } else {
            strength = unitType.getRangedCombatStrength();
        }
        if (!unitType.hasTerrainDefensiveBonusPenalty()) strength *= getTerrainFeaturesEffect(itsTile);
        if (enemyUnit == null) strength *= 1 + unitType.getBonusVsCity() / 100.0;
        else if (enemyUnit.getType() == UnitEnum.TANK && unitType == UnitEnum.ANTI_TANK_GUN) strength *= 2;
        else if (enemyUnit.getType().getCombatType() == CombatTypeEnum.MOUNTED)
            strength *= 1 + unitType.getBonusVsMounted() / 100.0;
        strength *= unit.getHealth() / 100.0;
        return strength;
    }

    protected static double getTerrainFeaturesEffect(Tile tile) {
        double effect = 1;
        effect *= (1.0 + ((double) tile.getTerrain().getTerrainType().getCombatModifier() / 100.0));
        for (FeatureEnum feature : tile.getTerrain().getFeatures()) {
            effect *= (1.0 + ((double) feature.getCombatModifier()) / 100.0);
        }
        return effect;
    }
    public Group createUnitGroup() {
        Hex hex = GameController.getGameTile(this.location).getHex();
        Group group = new Group();
        int unitsDistanceVertically = Constants.UNITS_DISTANCE_VERTICALLY;
        int unitDistanceHorizontally = Constants.UNITS_DISTANCE_HORIZONTALLY;
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                ImageView imageView = new ImageView(this.getType().getAssetImage());
                imageView.setLayoutX(hex.getCenterX() + unitDistanceHorizontally - unitDistanceHorizontally * j - imageView.getImage().getWidth() / 2);
                imageView.setLayoutY(hex.getCenterY() + unitsDistanceVertically - unitsDistanceVertically * i);
                group.getChildren().add(imageView);
            }
        }
        group.setOnMouseEntered((MouseEvent) -> {
            hex.setEffect(new DropShadow());
            System.out.println("\"mouseEntered\" = " + "mouseEntered");
            DropShadow hexDropShadow = (DropShadow) hex.getEffect();
            hex.setCursor(Cursor.HAND);
            hexDropShadow.setInput(new GaussianBlur());
        });
        group.setOnMouseExited((MouseEvent) -> {
            DropShadow effect = (DropShadow) hex.getEffect();
            effect.setInput(null);
        });
        return group;
    }

    public UnitEnum getType() {
        return this.type;
    }

    public void setType(UnitEnum type) {
        this.type = type;
    }

    public ArrayList<Tile> getPathShouldCross() {
        return pathShouldCross;
    }

    public void setPathShouldCross(ArrayList<Tile> pathShouldCross) {
        this.pathShouldCross = pathShouldCross;
    }

    public boolean isWorking() {
        return this.getState() == UnitStates.WORKING;
    }

    public double getAvailableMoveCount() {
        return availableMoveCount;
    }

    public void setAvailableMoveCount(double availableMoveCount) {
        this.availableMoveCount = availableMoveCount;
    }

    public void decreaseAvailableMoveCount(double value) {
        this.availableMoveCount -= value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UnitStates getState() {
        return state;
    }

    public void setState(UnitStates state) {
        this.state = state;
    }

    public Civilization getCivilization() {
        return civ;
    }

    public void setCiv(Civilization civ) {
        this.civ = civ;
    }

    @Override
    public void note(City city) {
        // todoLater : complete
    }

    public Group getGraphicUnit() {
        if(graphicUnit == null){
            graphicUnit = createUnitGroup();
        }
        return graphicUnit;
    }

    public void decreaseHealth(int value) {
        this.health -= value;
    }

    public String getInfo() {
        return "Type = " + this.getType().name() + '\n' +
                "Tile = " + this.getLocation() + '\n' +
                "Remaining movement = " + this.getAvailableMoveCount() + '\n' +
                "Health = " + this.getHealth() + '\n' +
                "Has work to do: " + this.isWorking() + '\n';
    }
}
