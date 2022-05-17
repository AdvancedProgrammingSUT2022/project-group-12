import Controllers.CombatController;
import Controllers.GameController;
import Enums.*;
import Models.Buildings.Building;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;
import Models.Units.NonCombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;
import Models.User;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;
import Views.MenuStack;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(MockitoExtension.class)
public class CombatTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    User user = new User("alireza", "!aA12345678", "james");
    User enemyUser = new User("asghar", "!a122344A", "jo");
    Game gameInstance;
    GameController gameController = new GameController(getGame());

    Civilization myCivilization = new Civilization(user, TerrainColor.BLUE_BACKGROUND);
    Unit enemyUnit = new NonCombatUnit(UnitEnum.SETTLER, myCivilization, new Location(5, 5));
    RangedUnit myRangedCombatUnit = new RangedUnit(UnitEnum.ARCHER, myCivilization, new Location(4, 5));
    NonRangedUnit myNonRangedCombatUnit = new NonRangedUnit(UnitEnum.WARRIOR, myCivilization, new Location(4, 5));
    Civilization enemyCivilization = new Civilization(enemyUser, TerrainColor.BROWN_BACKGROUND);
    NonRangedUnit nonRangedUnitEnemy = new NonRangedUnit(UnitEnum.SCOUT, enemyCivilization, new Location(5, 5));
    City myCity;
    City enemyCityDestroying;
    City enemyCityAnnexing;
    Terrain ownTerrain = new Terrain(TerrainEnum.GRASSLAND);
    Tile ownTile;
    Terrain enemyTerrain = new Terrain(TerrainEnum.HILL);
    Tile enemyTile;
    Tile enemyTile2;
    Scanner AnnexedScanner;
    Scanner DestroyScanner;
    MenuStack menuStack;
    //

    public Game getGame() {
        if (gameInstance == null) {
            this.gameInstance = new Game(new ArrayList<User>(List.of(user, enemyUser)));
            return gameInstance;
        }
        return gameInstance;
    }

    @BeforeEach
    public void setUp() {
        AnnexedScanner = new Scanner("Annexed");
        DestroyScanner = new Scanner("Destroy");
        MenuStack.getInstance().setScanner(AnnexedScanner);
        enemyUnit.setHealth(Constants.UNIT_FULL_HEALTH);
        myRangedCombatUnit.setHealth(90);
        enemyTerrain.getFeatures().removeAll(enemyTerrain.getFeatures());
        enemyTerrain.getFeatures().add(FeatureEnum.FOREST);
        ownTerrain.getFeatures().removeAll(ownTerrain.getFeatures());
        ownTerrain.getFeatures().add(FeatureEnum.MARSH);
        ownTile = new Tile(ownTerrain, new Location(4, 5));
        enemyTile = new Tile(enemyTerrain, new Location(5, 5));
        enemyTile2 = new Tile(enemyTerrain, new Location(4, 3));
        myCity = new City("mycity", new ArrayList<Tile>(List.of(ownTile)), myCivilization, ownTile, true);
        enemyCityDestroying = new City("enemycity", new ArrayList<Tile>(List.of(enemyTile)), enemyCivilization, enemyTile, false);
        enemyCityDestroying.getBuildings().add(new Building(BuildingEnum.CASTLE));
        enemyCityDestroying.getBuildings().add(new Building(BuildingEnum.BARRACK));
        enemyCityAnnexing = new City("enemycity", new ArrayList<Tile>(List.of(enemyTile)), enemyCivilization, enemyTile, true);
        enemyCityAnnexing.getBuildings().add(new Building(BuildingEnum.CASTLE));
        enemyCityDestroying.getBuildings().add(new Building(BuildingEnum.BARRACK));
        getGame().getTileGrid().setTile(enemyTile.getLocation(), enemyTile);
        getGame().getTileGrid().setTile(ownTile.getLocation(), ownTile);
    }

    @Test
    public void affectCityAttackToUnit() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            enemyUnit.setHealth(10);
            enemyTile.placeUnit(enemyUnit);
            enemyUnit.setCiv(enemyCivilization);
            ownTile.setCity(myCity);
        });
        AtomicReference<String> response = new AtomicReference<>("");
        Assertions.assertDoesNotThrow(() -> {
            response.set(CombatController.AttackCity(myCity, enemyTile.getLocation()));
        });
        Assertions.assertEquals("unit has killed", response.toString());
    }


    @Test
    public void affectRangeAttack() {
        String response = "Attack happened successfully";
        Assertions.assertDoesNotThrow(() -> {
            nonRangedUnitEnemy.setHealth(10);
            enemyTile.placeUnit(nonRangedUnitEnemy);
            ownTile.placeUnit(myRangedCombatUnit);
            myRangedCombatUnit.setCiv(myCivilization);
            nonRangedUnitEnemy.setCiv(enemyCivilization);
        });
        Assertions.assertDoesNotThrow(() -> CombatController.AttackUnit(myRangedCombatUnit, enemyTile.getLocation()));
        Assertions.assertNotNull(ownTile.getCombatUnit());
        Assertions.assertTrue(nonRangedUnitEnemy.getHealth() < 0);
    }

    @Test
    public void affectNonRangedAttack() {
        String response;
        Assertions.assertDoesNotThrow(() -> {
            nonRangedUnitEnemy.setHealth(10);
            enemyTile.placeUnit(nonRangedUnitEnemy);
            ownTile.placeUnit(myNonRangedCombatUnit);
        });
        Assertions.assertDoesNotThrow(() -> CombatController.AttackUnit(myNonRangedCombatUnit, enemyTile.getLocation()));
        Assertions.assertNull(ownTile.getCombatUnit());
        Assertions.assertTrue(nonRangedUnitEnemy.getHealth() < 0);
        Assertions.assertTrue(enemyTile.getCombatUnit() != null);
    }

    @Test
    public void affectCityAttackToCity() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            myCity.setHealth(Constants.CITY_FULL_HEALTH);
            enemyCityDestroying.setHealth(10);
            enemyTile.setCity(enemyCityDestroying);
            ownTile.setCity(myCity);
        });
        Assertions.assertThrows(CommandException.class, () -> CombatController.AttackCity(myCity, enemyTile.getLocation()));
    }
   @Test
    public void affectAttackRangedToCity() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            myRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            myNonRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            enemyCityDestroying.setHealth(10);
            enemyTile.setCity(enemyCityDestroying);
            ownTile.placeUnit(myRangedCombatUnit);
        });
       Throwable exception = Assertions.assertThrows(CommandException.class, () -> CombatController.AttackUnit(myRangedCombatUnit, enemyTile.getLocation()));
        Assertions.assertEquals(CommandResponse.YOU_CANT_DESTROY_CITY_BY_CITY.toString(),exception.getMessage());
    }

    @Test
    public void affectAttackNonRangedToCityAndMakeCityAnnexed() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            MenuStack.getInstance().setNullScanner();
            MenuStack.getInstance().setScanner(AnnexedScanner);
            myRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            myNonRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            enemyCityAnnexing.setHealth(10);
            enemyTile.setCity(enemyCityAnnexing);
            ownTile.placeUnit(myNonRangedCombatUnit);
        });
        System.out.println("enemyCityAnnexing = " + enemyCityAnnexing.getBuildings().size());
        Assertions.assertEquals("wow you have captured the city", CombatController.AttackUnit(myNonRangedCombatUnit, enemyTile.getLocation()));
        Assertions.assertSame(enemyCityAnnexing.getCityState(), CityTypeEnum.ANNEXED);
        Assertions.assertEquals(0, enemyCityAnnexing.getBuildings().size());
    }
   @Test
    public void affectAttackNonRangedToCityAndDestroyCity() throws CommandException {
       Assertions.assertDoesNotThrow(() -> {
           MenuStack.getInstance().setNullScanner();
           MenuStack.getInstance().setScanner(DestroyScanner);
           myRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
           myNonRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
           enemyCityDestroying.setHealth(10);
           enemyTile.setCity(enemyCityDestroying);
           ownTile.placeUnit(myRangedCombatUnit);
           myNonRangedCombatUnit.setCiv(myCivilization);
           enemyCityDestroying.setCivilization(enemyCivilization);
       });
       Assertions.assertEquals("wow you have captured the city", CombatController.AttackUnit(myNonRangedCombatUnit, new Location(5, 5)));
       Assertions.assertNull(enemyTile.getCity());
       Assertions.assertEquals(0, enemyCityDestroying.getBuildings().size());
       Assertions.assertNull(ownTile.getCombatUnit());
   }

    @Test
    public void affectAttackUnitToCityAndDestroy() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            MenuStack.getInstance().setNullScanner();
            MenuStack.getInstance().setScanner(DestroyScanner);
            myRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            myNonRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            enemyCityDestroying.setHealth(10);
            enemyTile.setCity(enemyCityDestroying);
            ownTile.placeUnit(myRangedCombatUnit);
        });
        Assertions.assertEquals("wow you have captured the city", CombatController.AttackUnit(myNonRangedCombatUnit, enemyTile.getLocation()));
        Assertions.assertNull(enemyTile.getCity());
        Assertions.assertEquals(0, enemyCityDestroying.getBuildings().size());
        Assertions.assertNull(ownTile.getCombatUnit());
    }
}
