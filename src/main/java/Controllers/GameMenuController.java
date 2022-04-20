package Controllers;

import Enums.CommandResponseEnum;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.Unit;

public class GameMenuController {
    private final Game game;
    private Civilization player1;
    private Civilization player2;

    public GameMenuController(Game newGame) {
        this.game = newGame;
    }


    public static boolean validateCity(String key,Game game){
        for (Tile tile:
                game.getCivTurn().get(game.getCivTurn().size()-1).getTiles() ) {
            if(tile.getCity() != null && tile.getCity().getName().equals(key)) return true;
        }
        return false;
    }
    public static CommandResponseEnum showMapOnCity(String key, Game game) {
        if(validateCity(key, game)){
            //TODO : showMapOnCity
            return CommandResponseEnum.OK;
        }
        return CommandResponseEnum.INVALID_CITY;
    }
    public static CommandResponseEnum moveMapRight(int amount) {
        if(amount <= 0){return CommandResponseEnum.INVALID_NUMBER;}
        return CommandResponseEnum.OK;
        // TODO : move map right
    }

    public static CommandResponseEnum moveMapUp(int amount) {
        if(amount <= 0){return CommandResponseEnum.INVALID_NUMBER;}
        return CommandResponseEnum.OK;
        // TODO : move map right
    }

    public static CommandResponseEnum moveMapLeft(int amount) {
        if(amount <= 0){return CommandResponseEnum.INVALID_NUMBER;}
        return CommandResponseEnum.OK;
        // TODO : move map right
    }

    public static CommandResponseEnum moveMapDown(int amount) {
        if(amount <= 0){return CommandResponseEnum.INVALID_NUMBER;}
        return CommandResponseEnum.OK;
        // TODO : move map right
    }

    public static CommandResponseEnum AttackUnit(int row, int col, Game game,Tile currentTile,Civilization civilization) {
        if(isCorrectPosition(row, col,game)){
            CommandResponseEnum response=validateAttacking(currentTile,civilization);


            return CommandResponseEnum.OK;
        }else {
            return CommandResponseEnum.INVALID_POSITION;
        }
    }

    private static CommandResponseEnum validateAttacking(Tile currentTile, Civilization civilization) {
        //TODO : complete
        return null;
    }


    public Game getGame() {
        return game;
    }
    private static boolean isCorrectPosition(int row, int col,Game game){
        if(row > game.getTileGrid().getHeight() || row < 0 ||  col > game.getTileGrid().getWidth() || col < 0) return false;
        return true;
    }
    public static CommandResponseEnum showMapOnPosition(int row, int col, Game game) {
        if(isCorrectPosition(row, col,game)){
            //TODO : show map
            return CommandResponseEnum.OK;
        }else {
            return CommandResponseEnum.INVALID_POSITION;
        }
    }

    public CommandResponseEnum battle(Civilization attacking, Civilization defending) {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum movement(Unit moving) {
        return CommandResponseEnum.OK;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
