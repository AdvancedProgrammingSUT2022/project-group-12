import Controllers.GameController;
import Enums.BuildingEnum;
import Enums.FeatureEnum;
import Models.Cities.City;
import Models.Game;
import Models.Terrains.Terrain;
import Models.User;
import Views.GameMenu;
import Views.MenuStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SourcesTest {
    User user = new User("alireza", "Password123?", "ali");

    String createUser = "user create --username alireza --password Password123? --nickname ali\n";
    String login = "user login -p Password123? -u alireza\n";
    String playNewGame = "play game --player1 alireza\n";
    String selectUnit = "select unit noncombat -p 10 10\n";
    String foundCity = "unit found city\n";
    String selectCity = "select city -p 10 10\n";
    String assignCitizen = "city citizen assign -p 12 10\n";
    String cheatAddBuildingLibrary = "cheat build building -n library\n";
    String cheatAddBuildingUniversity = "cheat build building -n university\n";
    String cheatAddBuildingMarket = "cheat build building -n market\n";
    String source;
    String endTurn = "end turn\n";
    Game game;
    GameMenu gameMenu;

    @BeforeEach
    public void setUp() {
        Assertions.assertDoesNotThrow(() -> {
                    game = new Game(new ArrayList<>(List.of(user)));
                    gameMenu = new GameMenu();
                    GameController.setGame(game);
                    Terrain terrain = game.getTileGrid().getTile(12, 10).getTerrain();
                    terrain.getFeatures().removeAll(terrain.getFeatures());

                }
        );
    }


    @Test
    public void TestBuildingCityAndBeaker() {
        Assertions.assertDoesNotThrow(
                () -> {
                    source = createUser + login + playNewGame + selectUnit + foundCity + selectCity + assignCitizen + cheatAddBuildingLibrary + cheatAddBuildingUniversity + endTurn + "menu exit\n" + "menu exit\n" + "menu exit\n";
                    MenuStack.getInstance().setNullScanner();
                    MenuStack.getInstance().setScanner(new Scanner(source));
                    String[] mainString = new String[1];
                    mainString[0] = "ali";
                    Main.main(mainString);
                }
        );
        City city = GameController.getGame().getCurrentCivilization().getCities().get(0);
        city.setCitizensCount(4);
        Assertions.assertEquals(4, city.getCitizensCount());
        Assertions.assertTrue(city.getBuildings().get(0).getType() == BuildingEnum.LIBRARY);
        Assertions.assertTrue(city.getBuildings().get(1).getType() == BuildingEnum.UNIVERSITY);
        BuildingEnum.LIBRARY.getNote().note(city);
        GameController.getGame().getTileGrid().getTile(11, 10).getTerrain().getFeatures().add(FeatureEnum.JUNGLE);
        GameController.getGame().getTileGrid().getTile(9, 10).getTerrain().getFeatures().add(FeatureEnum.JUNGLE);
        BuildingEnum.UNIVERSITY.getNote().note(city);
        Assertions.assertEquals(9, city.getCivilization().calculateScience());
    }

    @Test
    public void TestGold() {
        Assertions.assertDoesNotThrow(
                () -> {
                    Terrain terrain = game.getTileGrid().getTile(12, 10).getTerrain();
                    terrain.getFeatures().removeAll(terrain.getFeatures());
                    source = createUser + login + playNewGame + selectUnit + foundCity + selectCity + assignCitizen + cheatAddBuildingMarket + "menu exit\n" + "menu exit\n" + "menu exit\n";
                    MenuStack.getInstance().setScanner(new Scanner(source));
                }
        );


    }

}
