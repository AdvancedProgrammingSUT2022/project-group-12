import Controllers.CityCombatController;
import Controllers.GameController;
import Controllers.UnitCombatController;
import Enums.*;
import Models.*;
import Models.Cities.City;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;
import Models.Units.*;
import Utils.Command;
import Utils.CommandException;
import Views.MenuStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static Models.Units.Unit.calculateCombatStrength;
import static Models.Units.Unit.calculateDamage;
import static Utils.CommandResponse.*;
import static java.lang.Math.exp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CombatTest {

    User user = new User("alireza","!aA12345678","james");
    User enemyUser = new User("asghar","!a122344A","jo");
    Game gameInstance;
    public Game getGame() {
        if(gameInstance ==  null){
            this.gameInstance =   new Game(new ArrayList<User>(List.of(user,enemyUser))); return gameInstance;
        }
        return gameInstance;
    }
    GameController gameController = new GameController(getGame());
    Civilization myCivilization = new Civilization(user, TerrainColor.BLUE_BACKGROUND);
    Civilization enemyCivilization = new Civilization(enemyUser,TerrainColor.BROWN_BACKGROUND);
    Unit enemyUnit = new NonCombatUnit(UnitEnum.SETTLER, myCivilization,new Location(20,21));
    RangedUnit myRangedCombatUnit = new RangedUnit(UnitEnum.ARCHER, myCivilization,new Location(20,22));
    NonRangedUnit myNonRangedCombatUnit = new NonRangedUnit(UnitEnum.WARRIOR, myCivilization,new Location(20,22));
    City myCity;
    City enemyCity;
    NonRangedUnit nonRangedUnitEnemy = new NonRangedUnit(UnitEnum.SCOUT,enemyCivilization,new Location(20,21));
    Terrain ownTerrain = new Terrain(TerrainEnum.GRASSLAND);
    Tile ownTile;
    Terrain enemyTerrain = new Terrain(TerrainEnum.HILL);
    Tile enemyTile;
    MenuStack menuStack = new MenuStack();
    Scanner scanner = new Scanner(System.in);
    @BeforeEach
    public void setUp() {
        enemyUnit.setHealthBar(100);
        myRangedCombatUnit.setHealthBar(90);
        enemyTerrain.getFeatures().removeAll(enemyTerrain.getFeatures()); enemyTerrain.getFeatures().add(TerrainEnum.FOREST);
        ownTerrain.getFeatures().removeAll(ownTerrain.getFeatures()); ownTerrain.getFeatures().add(TerrainEnum.MARSH);
        ownTile = new Tile(ownTerrain ,20,22);
        enemyTile = new Tile(enemyTerrain,20,21);
        myCity = new City("mycity", new ArrayList<Tile>(List.of(ownTile)),myCivilization,ownTile,true);
        enemyCity = new City("enemycity",new ArrayList<Tile>(List.of(enemyTile)),enemyCivilization,enemyTile,false);
    }
    @Test
    public void calculateRangeAttackDamage() {
        String response = "Attack happened successfully";
        enemyTile.setUnit(enemyUnit);
        enemyUnit.setHealthBar(10);
        ownTile.setUnit(myRangedCombatUnit);
        double strengthRangedUnit = Unit.calculateCombatStrength(myRangedCombatUnit,ownTile,"rangedcombatstrength");
        double combatUnitStrength = Unit.calculateCombatStrength(enemyUnit,enemyTile,"combatstrength");
        double strengthDiff = strengthRangedUnit - combatUnitStrength;
        Random random = new Random();
        Unit.calculateDamage(enemyUnit, strengthDiff, random);
        Assertions.assertTrue(ownTile.getCombatUnit() != null);
    }
    @Test
    public void calculateNonRangeAttack() {
        String response = "Attack happened successfully";
        enemyTile.setUnit(nonRangedUnitEnemy);
        ownTile.setUnit(myNonRangedCombatUnit);
        myNonRangedCombatUnit.setHealthBar(50);
        nonRangedUnitEnemy.setHealthBar(10);
        double combatStrength1 = myNonRangedCombatUnit.calculateCombatStrength(myNonRangedCombatUnit, ownTile,"combatstrength");
        double combatStrength2 = nonRangedUnitEnemy.calculateCombatStrength(nonRangedUnitEnemy, enemyTile, "combatstrength");
        UnitCombatController.calculateNonRangeAttackDamage(myNonRangedCombatUnit, combatStrength1, nonRangedUnitEnemy, combatStrength2);
        response = UnitCombatController.checkForKill(myNonRangedCombatUnit,nonRangedUnitEnemy,ownTile,enemyTile);
        Assertions.assertTrue(ownTile.getCombatUnit() == null);
    }

    @Test
    public void calculateDamage() {
        double strengthDiff = 12.3;
        double random_number = 1.25;
        enemyUnit.setHealthBar(enemyUnit.getHealthBar() - (int) (25 * exp(strengthDiff / (25.0 * random_number))));
        Assertions.assertEquals((100 -(int) (25 * exp(strengthDiff / (25.0 * random_number)))), enemyUnit.getHealthBar());
    }
    @Test
    public void calculateCityRangeAttack() throws CommandException {
        String response = new String("");
        /***
         * we want to test city on hill so i choose enemytile for our city
         */
        myCity.setHitPoint(10);
        enemyUnit.setHealthBar(30);
        enemyTile.setCity(myCity);
        enemyTile.setUnit(myRangedCombatUnit);
        ownTile.setUnit(enemyUnit);
        double strengthRangedUnit = myCity.calculateCombatStrength();
        double EnemyUnitStrength = Unit.calculateCombatStrength(enemyUnit, ownTile, "combatstrengh");
        CityCombatController.calculateRangeAttackDamage(myCity, strengthRangedUnit, enemyUnit, EnemyUnitStrength);
        response = CityCombatController.checkForKill(enemyUnit,myCity,ownTile,enemyTile);
        Assertions.assertTrue(myCity.getHitPoint() > 0);
    }
    @Test
    public void  calculateNonRangeAttackToCity() throws CommandException {
        String response = "Attack happened successfully";
        myNonRangedCombatUnit.setHealthBar(100);
        enemyTile.setUnit(nonRangedUnitEnemy);
        enemyCity.setHitPoint(10);
        double combatStrengthNonRangedUnit = Unit.calculateCombatStrength(myNonRangedCombatUnit, ownTile, "combatstrength");
        double EnemyCityStrength = enemyCity.calculateCombatStrength();
        CityCombatController.calculateNonRangeAttackDamage(myNonRangedCombatUnit, combatStrengthNonRangedUnit, enemyCity, EnemyCityStrength);
        CityCombatController.checkForKill(myNonRangedCombatUnit,enemyCity,ownTile,enemyTile);
        Assertions.assertTrue(enemyCity == null && ownTile.getCombatUnit() == null && enemyTile.g);
    }
    @Test
    public void calculateRangeAttackToCity() throws CommandException {
        String response = "Attack happened successfully";
        myRangedCombatUnit.setHealthBar(100);
        enemyTile.setUnit(nonRangedUnitEnemy);
        enemyCity.setHitPoint(10);
        double strengthRangedUnit = Unit.calculateCombatStrength(myRangedCombatUnit, ownTile, "rangedcombatstrength");
        if (myRangedCombatUnit.getType().getCombatType() == CombatTypeEnum.SIEGE) {
            strengthRangedUnit = CityCombatController.bonusForAttackToCity(myRangedCombatUnit, strengthRangedUnit);
        }
        double EnemyUnitStrength = enemyCity.calculateCombatStrength();
        CityCombatController.calculateRangeAttackDamage(myRangedCombatUnit, strengthRangedUnit, enemyCity, EnemyUnitStrength);
        Assertions.assertTrue(enemyCity.getHitPoint() < 0);
    }
    @Test
    public void calculateCityRangeAttackToCity() throws CommandException {
        String response = "Attack happened successfully";
        enemyTile.setUnit(nonRangedUnitEnemy);
        enemyCity.setHitPoint(10);
        double strengthRangedUnit = enemyCity.calculateCombatStrength();
        double EnemyUnitStrength = myCity.calculateCombatStrength();
        CityCombatController.calculateRangeAttackDamage(enemyCity, strengthRangedUnit, myCity, EnemyUnitStrength);
        Throwable exception = assertThrows(CommandException.class, () -> CityCombatController.checkForKill(myCity,enemyCity,ownTile,enemyTile));
        assertEquals(YOU_CANT_DESTROY_CITY_BY_CITY.toString(), exception.getMessage());
    }
}
