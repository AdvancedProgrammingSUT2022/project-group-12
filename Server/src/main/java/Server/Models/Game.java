package Server.Models;

import Project.Enums.*;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.NonRangedUnit;
import Project.Models.Units.Unit;
import Project.Models.User;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Project.Utils.TokenGenerator;
import Server.Controllers.CityHandler;
import Server.Controllers.GameController;
import Server.Controllers.MainMenuController;
import Server.Utils.CommandException;
import Server.Utils.GameException;
import Server.Utils.MenuStackManager;
import Server.Utils.UpdateNotifier;
import Server.Views.MenuStack;

import java.util.*;

public class Game {
    private final String name;
    private final String token;
    private final ArrayList<Civilization> civilizations;
    private final TileGrid tileGrid;
    private final ArrayList<User> users;
    private int gameTurn = -1;

    public Game(ArrayList<User> users,int height,int width) {
        this.token = TokenGenerator.generate(Constants.TOKEN_LENGTH);
        this.name = this.token;
        this.users = users;
        this.civilizations = new ArrayList<>();
        this.tileGrid = TileGrid.generateRandomTileGrid(height, width);
        this.tileGrid.setAllStatesTo(VisibilityEnum.VISIBLE);
        ArrayList<Tile> availableTiles = new ArrayList<>();
        for (Tile tile : this.tileGrid.getTilesFlatten()) {
            if (tile.getTerrain().getTerrainType().isReachable()) {
                availableTiles.add(tile);
            }
        }
        Collections.shuffle(availableTiles);

        List<TerrainColor> colors = List.of(TerrainColor.GREEN_BOLD, TerrainColor.RED_BOLD, TerrainColor.YELLOW_BOLD, TerrainColor.CYAN_BOLD, TerrainColor.WHITE_BOLD);
        for (int index = 0; index < users.size(); index++) {
            TerrainColor color = colors.get(index % colors.size());
            Civilization civ = new Civilization(users.get(index), color, height, width);
            civilizations.add(civ);

            Tile initialTile = availableTiles.get(availableTiles.size() - 1);
            //test
//            initialTile.setRuin(true);
            civ.setInitialLocation(initialTile.getLocation());

            for (Tile tile : this.tileGrid.getAllTilesInRadius(initialTile, Constants.INITIAL_SETTLERS_DISTANCE))
                availableTiles.remove(tile);

            NonCombatUnit settler = new NonCombatUnit(UnitEnum.SETTLER, civ.getName(), initialTile.getLocation());
            CombatUnit warrior = new NonRangedUnit(UnitEnum.WARRIOR, civ.getName(), initialTile.getLocation());

            try {
                GameController.placeUnit(settler, initialTile);
                GameController.placeUnit(warrior, initialTile);
            } catch (CommandException e) { // never
                System.out.println("error in placing initial units at game class");
            }
            civ.addUnit(settler);
            civ.addUnit(warrior);
            updateRevealedTileGrid(civ);
            //test
//            civ.addResource(ResourceEnum.IRON);
//            civ.addResource(ResourceEnum.BANANA);
            civ.getResearchingTechnologies().put(TechnologyEnum.AGRICULTURE, TechnologyEnum.AGRICULTURE.getCost());
//            civ.addTechnology(TechnologyEnum.AGRICULTURE);
//            civ.setResearchingTechnology(TechnologyEnum.POTTERY);
            civ.setCurrentSelectedGridLocation(initialTile.getLocation());
        }

        int ruinCount = (int) (tileGrid.getHeight() * tileGrid.getWidth() * Constants.RUIN_PROBABILITY);
        for (int i = 0; i < ruinCount && !availableTiles.isEmpty(); i++) {
            Tile ruinTile = availableTiles.get(availableTiles.size() - 1);
            for (Tile tile : this.tileGrid.getAllTilesInRadius(ruinTile, Constants.RUIN_DISTANCE)) {
                availableTiles.remove(tile);
            }
            ruinTile.setRuin(true);
            System.out.println("ruin at " + ruinTile.getLocation());
        }
    }

    public Civilization getCivOfUser(User user) {
        int index = this.users.indexOf(user);
        return index == -1 ? null : this.civilizations.get(index);
    }

    public void bindTileUpdatesTo(User user, UpdateNotifier updateNotifier, boolean isShow) {
        for (Tile tile : (isShow ? getTileGrid() : this.getCivOfUser(user).getRevealedTileGrid()).getTilesFlatten()) {
            tile.initializeNotifier();
            tile.addObserver(updateNotifier);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void updateRevealedTileGrid(Civilization civilization) {
        civilization.getRevealedTileGrid().changeVisibleTilesToRevealed();
        for (Unit unit : civilization.getUnits()) {
            Tile tile = tileGrid.getTile(unit.getLocation());
            int visibilityRange = unit.getUnitType().hasLimitedVisibility() ? Constants.UNIT_LIMITED_VISION_RADIUS : Constants.UNIT_VISION_RADIUS;
            for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, visibilityRange)) {
                revealTileFor(civilization, neighbor);
            }
        }
        for (City city : civilization.getCities()) {
            Tile tile = tileGrid.getTile(city.getLocation());
            for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, Constants.UNIT_VISION_RADIUS)) {
                revealTileFor(civilization, neighbor);
            }
        }
    }

    public void revealTileFor(Civilization civilization, Tile tile) {
        TileGrid civTileGrid = civilization.getRevealedTileGrid();
        civTileGrid.getTile(tile.getLocation()).copyPropertiesFrom(tile);
    }

    public String endCurrentTurn() throws CommandException {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        StringBuilder response = new StringBuilder();
        updateRevealedTileGrid(civ);
        for (Unit unit : civ.getUnits()) {
            if (unit.getState() == UnitStates.AWAKE) {
                checkForMultipleMoves(unit,response);
                // please don't erase this!
//                checkForMovementCost(unit);
            }
        }

        for (City city : civ.getCities()) {
            if (CityHandler.numberOfUnassignedCitizens(city) > 0) {
                throw new CommandException(CommandResponse.UNASSIGNED_CITIZEN, city.getName());
            } else if (city.getProductionQueue().isEmpty()) {
                throw new CommandException(CommandResponse.EMPTY_PRODUCTION_QUEUE, city.getName());
            }
        }
        response.append("Your turn ended successfully");


        if (!civ.getCities().isEmpty() && civ.getResearchingTechnology() == null) {

            throw new CommandException(CommandResponse.NO_RESEARCHING_TECHNOLOGY);
        }
        checkForKillingCitizen(civ);
        civ.setGold(civ.calculateCivilizationGold());
        System.out.println(isCurrentCivDefeatOthers());
        System.out.println(new Date());
        if (isCurrentCivDefeatOthers()) {
            updateCivsScore();
            setWinDateForWinnerCiv(this.getCurrentCivilization());
            endGameNotifyUsers();
            removeGameFromDataBase();
            throw new CommandException(CommandResponse.END_OF_GAME);
        }
        System.out.println("game turn divide civilization size " + (this.gameTurn / civilizations.size()) );
        if ((this.gameTurn / civilizations.size()) > Constants.GAME_LENGTH) {
            Civilization winnerCivilization = this.civilizations.get(0);
            updateCivsScore();
            for (Civilization civ1 :
                    civilizations) {
                if (civ1.calculateSuccess() > winnerCivilization.calculateSuccess()) {
                    winnerCivilization = civ1;
                }
            }
            handleEndOfGame(winnerCivilization);
        }
        updateYear();
        return response.toString();
    }

    private void handleEndOfGame(Civilization winnerCiv) throws CommandException {
        updateCivsScore();
        setWinDateForWinnerCiv(winnerCiv);
        endGameNotifyUsers();
        removeGameFromDataBase();
        throw new CommandException(CommandResponse.END_OF_GAME);
    }

    private void removeGameFromDataBase() {
        Database.getInstance().getGames().remove(this.getToken());
    }

    private void setWinDateForWinnerCiv(Civilization winnerCivilization) {
        winnerCivilization.getUser().setLastWinDate(new Date());
        MainMenuController.reloadClientScoreboards();
    }

    private void updateCivsScore() {
        for (Civilization civ1 :
                civilizations) {
            if (civ1.calculateSuccess() > civ1.getUser().getScore()) {
                civ1.getUser().setScore(civ1.calculateSuccess());
            }
        }
    }

    private void endGameNotifyUsers() {
        HashMap<String, Integer> civsScores = new HashMap<>();
        for (Civilization civ :
                GameController.getGame().getCivilizationsSortedByScore()) {
            civsScores.put(civ.getName(), civ.calculateSuccess());
        }
        for (MenuStack menuStack :
                this.users.stream().map(e -> MenuStackManager.getInstance().getMenuStackOfUser(e)).toList()) {
            menuStack.getUpdateNotifier().sendEndGameMessage(civsScores);
        }
    }


    private void checkForKillingCitizen(Civilization civ) {
        for (City city : civ.getCities()) {
            if (CityHandler.calculateFood(city) < 0 && city.getCitizensCount() > 0) {
                ArrayList<Location> tilesLocations = city.getTilesLocations();
                for (Location location : tilesLocations) {
                    Tile tile = GameController.getGameTile(location);
                    if (tile.getCitizen() != null && location != city.getLocation()) {
                        tile.setCitizen(null);
                        break;
                    }
                }
            }
        }
    }

    public Civilization getCurrentCivilization() {
        return civilizations.get(this.gameTurn % civilizations.size());
    }

    public void startNewTurn(UpdateNotifier updateNotifier) throws GameException {
        this.gameTurn++;
        Civilization civ = this.getCurrentCivilization();
        civ.applyNotes();
        updateRevealedTileGrid(civ);
        civ.advanceResearchTech(updateNotifier);
        civ.advanceWorkerWorks();
        for (City city : civ.getCities()) {
            CityHandler.checkCitizenBirth(city);
            Production production = city.advanceProductionQueue(CityHandler.calculateProduction(city));
            if (production != null) {
                addProductionOfProductionQueueToCity(city, production);
            }
        }
        for (Unit unit : civ.getUnits()) {
            checkForAlertUnit(unit);
            checkForFortifyHealUnit(unit);
        }
        civ.resetMoveCount();
        // set has attack false for units which they can move after attack
        civ.getUnits().forEach((unit) -> {
            if (unit instanceof CombatUnit) ((CombatUnit) unit).setHasAttack(false);
        });
    }

    private boolean isCurrentCivDefeatOthers() {
        if (this.getCurrentCivilization().isOwnerOfItsCapital()) {
            for (Civilization civ :
                    civilizations.stream().filter(e -> !e.equals(this.getCurrentCivilization())).toList()) {
                if (civ.isOwnerOfItsCapital()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void addProductionOfProductionQueueToCity(City city, Production production) {
        if (production instanceof Unit unit) {
            Civilization cityCiv = GameController.getGame().getCivByName(city.getCivName());
            try {
                GameController.placeUnit(unit, GameController.getGameTile(city.getLocation()));
                cityCiv.getUnits().add(unit);
            } catch (CommandException e) {
                // we should guarantee emptiness of the tile at the last turn
                throw new RuntimeException(e);
            }
        } else if(production instanceof Building building){
            city.getBuildings().add(building);
        }
    }

    private boolean checkForEnemy(ArrayList<Tile> tiles, Civilization unitCiv) {
        for (Tile tile : tiles) {
            if (GameController.isEnemyExists(tile.getLocation(), unitCiv) || GameController.isNonCombatEnemyExists(tile.getLocation(), unitCiv)) {
                return true;
            }
        }
        return false;
    }

    private void checkForMovementCost(Unit unit) throws GameException {
        if (unit.getAvailableMoveCount() > 0) {
            throw new GameException(CommandResponse.UNIT_NEED_ORDER, unit.getUnitType().name() + " at " + unit.getLocation());
        }
    }

    private void checkForMultipleMoves(Unit unit, StringBuilder response) {
        if (unit.getAvailableMoveCount() > 0 && unit.getPathShouldCross() != null && unit.getPathShouldCross().size() > 0) {
            if (GameController.getGameTile(unit.getLocation()).isARuin()) {
                GameController.getGameTile(unit.getLocation()).achieveRuin();
                GameController.getCivByName(unit.getCivName()).addGold(Constants.GOLD_PRIZE_RUIN);
                if (!response.toString().startsWith("ruin")) {
                    response.append("ruin achieved successfully and 30 gold added to your territory");
                }
            }
        }
    }

    private void checkForAlertUnit(Unit unit) {
        Tile unitTile = GameController.getGameTile(unit.getLocation());
        ArrayList<Tile> tiles = tileGrid.getAllTilesInRadius(unitTile, 2);
        Civilization unitCiv = GameController.getCivByName(unit.getCivName());
        if (checkForEnemy(tiles, unitCiv)) {
            unit.setState(UnitStates.AWAKE);
        }
    }

    private void setPlayersScores() {
        for (Civilization civilization : this.civilizations) {
            civilization.civUser().setScore(civilization.calculateSuccess() / 100);
        }
    }

    private void checkForFortifyHealUnit(Unit unit) {
        if (unit.getHealth() == Constants.UNIT_FULL_HEALTH) {
            unit.setState(UnitStates.AWAKE);
        }
    }

    public TileGrid getTileGrid() {
        return this.tileGrid;
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public Unit getSelectedUnit(Civilization currentCivilization, Location location, Boolean isCombatUnit) throws CommandException {
        Tile tile = this.tileGrid.getTile(location);
        Unit unit;
        if (isCombatUnit != null) {
            unit = isCombatUnit ? tile.getCombatUnit() : tile.getNonCombatUnit();
        } else if (tile.getCombatUnit() != null && tile.getNonCombatUnit() != null) {
            throw new CommandException(CommandResponse.AMBIGUOUS_UNIT_SELECTION);
        } else {
            unit = tile.getCombatUnit() != null ? tile.getCombatUnit() : tile.getNonCombatUnit();
        }
        if (unit == null || GameController.getCivByName(unit.getCivName()) != (currentCivilization)) {
            throw new CommandException(CommandResponse.UNIT_DOES_NOT_EXISTS);
        } else {
            return unit;
        }
    }
    public void updateYear(){
        for (MenuStack menuStack :
                this.users.stream().map(e -> MenuStackManager.getInstance().getMenuStackOfUser(e)).toList()) {
            menuStack.getUpdateNotifier().sendUpdateYearMessage(this.getCurrentYear());
        }
    }

    public Civilization getCivByName(String name) {
        for (Civilization civ :
                this.getCivilizations()) {
            if (name.equals(civ.getName())) {
                return civ;
            }
        }
        return null;
    }

    public int getGameTurnNumber() {
        return this.gameTurn;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Civilization>  getCivilizationsSortedByScore() {
     Collections.sort(this.civilizations , new Comparator<Civilization>() {
           @Override
           public int compare(Civilization o1, Civilization o2) {
               if(o1.calculateSuccess() > o2.calculateSuccess()) {
                   return 1;
               } else {
                   return -1;
               }
           }
       });
     return this.civilizations;
    }

    public HashMap<String, Integer> getCivNamesAndScore() {
        HashMap<String, Integer> civsScore = new HashMap<>();
        ArrayList<Civilization> civilizationsSortedByScore = this.getCivilizationsSortedByScore();
        for (int i = civilizationsSortedByScore.size() - 1; i >= 0 ; i--) {
            civsScore.put(civilizationsSortedByScore.get(i).getName(), civilizationsSortedByScore.get(i).calculateSuccess());
        }
        return civsScore;
    }
    public int getCurrentYear(){
        return (int) (( Math.floor((double) (gameTurn / civilizations.size())) + 1 ) * 10);
    }
}