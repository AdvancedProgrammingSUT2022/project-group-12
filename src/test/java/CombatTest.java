import Controllers.*;
import Enums.*;
import Models.Buildings.Building;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;
import Models.Units.*;
import Models.User;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;
import Views.MenuStack;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static Controllers.CityCombatController.checkForKill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

    public Game getGame() {
        if (gameInstance == null) {
            this.gameInstance = new Game(new ArrayList<User>(List.of(user, enemyUser)));
            return gameInstance;
        }
        return gameInstance;
    }
    @Mock
    MenuStack menuStack;

    @BeforeEach
    public void setUp() {
        AnnexedScanner = new Scanner("Annexed");
        DestroyScanner = new Scanner("Destroy");
        MenuStack.getInstance().setScanner(AnnexedScanner);
        enemyUnit.setHealth(Constants.UNIT_FULL_HEALTH);
        myRangedCombatUnit.setHealth(90);
        enemyTerrain.getFeatures().removeAll(enemyTerrain.getFeatures());
        enemyTerrain.getFeatures().add(TerrainEnum.FOREST);
        ownTerrain.getFeatures().removeAll(ownTerrain.getFeatures());
        ownTerrain.getFeatures().add(TerrainEnum.MARSH);
        ownTile = new Tile(ownTerrain, 4, 5);
        enemyTile = new Tile(enemyTerrain, 5, 5);
        enemyTile2  = new Tile(enemyTerrain,4,3);
        myCity = new City("mycity", new ArrayList<Tile>(List.of(ownTile)), myCivilization, ownTile, true);
        enemyCityDestroying = new City("enemycity", new ArrayList<Tile>(List.of(enemyTile)), enemyCivilization, enemyTile, false);
        enemyCityDestroying.getBuildings().add(new Building(BuildingEnum.CASTLE)); enemyCityDestroying.getBuildings().add(new Building(BuildingEnum.BARRACK));
        enemyCityAnnexing = new City("enemycity", new ArrayList<Tile>(List.of(enemyTile)), enemyCivilization, enemyTile, false);
        enemyCityAnnexing.getBuildings().add(new Building(BuildingEnum.CASTLE)); enemyCityDestroying.getBuildings().add(new Building(BuildingEnum.BARRACK));
    }

    @Test
    public void affectRangeAttack() {
        String response = "Attack happened successfully";
        Assertions.assertDoesNotThrow(() -> {
            enemyUnit.setHealth(10);
            enemyTile.placeUnit(enemyUnit);
            ownTile.placeUnit(myRangedCombatUnit);
        });
        UnitCombatController.affectRangeAttack(myRangedCombatUnit,enemyUnit,ownTile,enemyTile);
        Assertions.assertNotNull(ownTile.getCombatUnit());
        Assertions.assertTrue(enemyUnit.getHealth() < 0);
    }
//
    @Test
    public void affectNonRangedAttack() {
        String response;
        Assertions.assertDoesNotThrow(() -> {
            nonRangedUnitEnemy.setHealth(10);
            enemyTile.placeUnit(nonRangedUnitEnemy);
            ownTile.placeUnit(myNonRangedCombatUnit);
        });
        UnitCombatController.affectNonRangedAttack(myNonRangedCombatUnit, nonRangedUnitEnemy,ownTile,enemyTile);
        Assertions.assertNull(ownTile.getCombatUnit());
        Assertions.assertTrue(nonRangedUnitEnemy.getHealth() < 0);
        Assertions.assertTrue(enemyTile.getCombatUnit() != null);
    }
    @Test
    public void affectCityAttackToUnit() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            enemyUnit.setHealth(10);
            enemyTile.placeUnit(enemyUnit);
            ownTile.setCity(myCity);
        });
        AtomicReference<String> response = new AtomicReference<>("");
        Assertions.assertDoesNotThrow(() -> {
            response.set(CityCombatController.affectCityAttackToUnit(myCity, enemyUnit, ownTile, enemyTile));
        });
        Assertions.assertEquals("unit has killed",response.toString());
    }
//
    @Test
    public void affectCityAttackToCity() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            myCity.setHealth(Constants.CITY_FULL_HEALTH);
            enemyCityDestroying.setHealth(10);
            enemyTile.setCity(enemyCityDestroying);
            ownTile.setCity(myCity);
        });
        Assertions.assertThrows(CommandException.class,()->CityCombatController.affectCityAttackToCity(myCity,enemyCityDestroying));
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
        Throwable exception = Assertions.assertThrows(CommandException.class,() -> CityCombatController.affectAttackToCity(myRangedCombatUnit,enemyCityDestroying,myCivilization,ownTile,enemyTile));
        Assertions.assertEquals(CommandResponse.YOU_CANT_DESTROY_CITY_BY_CITY.toString(),exception.getMessage());
    }

     @Test
    public void affectAttackNonRangedToCityAndMakeCityAnnexed() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            MenuStack.getInstance().setScanner(AnnexedScanner);
            myRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            myNonRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            enemyCityAnnexing.setHealth(10);
            enemyTile.setCity(enemyCityAnnexing);
            enemyCityAnnexing.getBuildings().add(new Building(BuildingEnum.CASTLE)); enemyCityAnnexing.getBuildings().add(new Building(BuildingEnum.BARRACK));
            ownTile.placeUnit(myNonRangedCombatUnit);
        });
         System.out.println("enemyCityAnnexing = " + enemyCityAnnexing.getBuildings().size());
         Assertions.assertEquals("wow you have captured the city", CityCombatController.affectAttackToCity(myNonRangedCombatUnit, enemyCityAnnexing, myCivilization, ownTile, enemyTile));
         Assertions.assertSame(enemyCityAnnexing.getCityState(), CityTypeEnum.ANNEXED);
         assertEquals(0, enemyCityAnnexing.getBuildings().size());
    }
   @Test
    public void affectAttackNonRangedToCityAndDestroyCity() throws CommandException {
        Assertions.assertDoesNotThrow(() -> {
            MenuStack.getInstance().setScanner(DestroyScanner);
            myRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            myNonRangedCombatUnit.setHealth(Constants.UNIT_FULL_HEALTH);
            enemyCityDestroying.setHealth(10);
            enemyTile.setCity(enemyCityDestroying);
            ownTile.placeUnit(myRangedCombatUnit);
        });
        Assertions.assertEquals("wow you have captured the city",CityCombatController.affectAttackToCity(myNonRangedCombatUnit,enemyCityDestroying,myCivilization,ownTile,enemyTile));
        Assertions.assertNull(enemyTile.getCity());
        assertEquals(0, enemyCityDestroying.getBuildings().size());
        Assertions.assertNull(ownTile.getCombatUnit());
    }
}
