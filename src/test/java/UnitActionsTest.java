import Project.CommandlineViews.DisabledMenuStackDisabled;
import Project.Controllers.GameController;
import Project.Enums.*;
import Project.Models.Cities.City;
import Project.Models.Civilization;
import Project.Models.Game;
import Project.Models.Location;
import Project.Models.Terrains.Terrain;
import Project.Models.Tiles.Tile;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.NonRangedUnit;
import Project.Models.Units.RangedUnit;
import Project.Models.Units.Unit;
import Project.Models.User;
import Project.Utils.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UnitActionsTest {
    User user = new User("alireza", "!aA12345678", "james");
    User enemyUser = new User("asghar", "!a122344A", "jo");
    Game gameInstance;
    GameController gameController = new GameController(getGame());
    Civilization myCivilization = new Civilization(user, TerrainColor.BLUE_BACKGROUND);
    Unit enemyUnit = new NonCombatUnit(UnitEnum.SETTLER, myCivilization, new Location(20, 21));
    RangedUnit myRangedCombatUnit = new RangedUnit(UnitEnum.ARCHER, myCivilization, new Location(20, 22));
    NonRangedUnit myNonRangedCombatUnit = new NonRangedUnit(UnitEnum.WARRIOR, myCivilization, new Location(20, 22));
    Civilization enemyCivilization = new Civilization(enemyUser, TerrainColor.BROWN_BACKGROUND);
    NonRangedUnit nonRangedUnitEnemy = new NonRangedUnit(UnitEnum.SCOUT, enemyCivilization, new Location(20, 21));
    City myCity;
    City enemyCity;
    Terrain ownTerrain = new Terrain(TerrainEnum.GRASSLAND);
    Tile ownTile;
    Terrain enemyTerrain = new Terrain(TerrainEnum.HILL);
    Tile enemyTile;
    DisabledMenuStackDisabled disabledMenuStackDisabled = new DisabledMenuStackDisabled();
    Scanner scanner = new Scanner(System.in);
    public Game getGame() {
        if (gameInstance == null) {
            this.gameInstance = new Game(new ArrayList<User>(List.of(user, enemyUser)));
            return gameInstance;
        }
        return gameInstance;
    }

    @BeforeEach
    public void setUp() {
        enemyUnit.setHealth(Constants.UNIT_FULL_HEALTH);
        myRangedCombatUnit.setHealth(90);
        enemyTerrain.getFeatures().removeAll(enemyTerrain.getFeatures());
        enemyTerrain.getFeatures().add(FeatureEnum.FOREST);
        ownTerrain.getFeatures().removeAll(ownTerrain.getFeatures());
        ownTerrain.getFeatures().add(FeatureEnum.MARSH);
        ownTile = new Tile(ownTerrain, new Location(20, 22));
        enemyTile = new Tile(enemyTerrain, new Location(20, 21));
        myCity = new City("mycity", new ArrayList<Tile>(List.of(ownTile)), myCivilization, ownTile, true);
        enemyCity = new City("enemycity", new ArrayList<Tile>(List.of(enemyTile)), enemyCivilization, enemyTile, false);
    }
   @Test
    void test(){
        ownTile.addImprovement(ImprovementEnum.ROAD);
        ArrayList<ImprovementEnum> improvements = new ArrayList<>(ownTile.getImprovements());
        List<ImprovementEnum> improvementEnums = improvements.stream().filter(improvementEnum -> {
            return improvementEnum != ImprovementEnum.ROAD && improvementEnum != ImprovementEnum.RAILROAD;
        }).toList();
        Assertions.assertTrue(improvementEnums.size() == 0);
   }





}
