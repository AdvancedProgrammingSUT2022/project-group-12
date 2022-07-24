package Server.Models;

import Project.Models.Chat;
import Project.Models.OpenGame;
import Project.Models.User;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Project.Utils.NameAndToken;
import Project.Utils.TokenGenerator;
import Server.Controllers.GameController;
import Server.Controllers.MainMenuController;
import Server.Utils.CommandException;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Database {
    private static Database instance = null;
    private final HashMap<String, User> users;
    private final HashMap<String, Game> games;
    private final ArrayList<Chat> chats = new ArrayList<>();
    private final HashMap<String, User> token2userTable = new HashMap<>();
    private final HashMap<User, String> user2tokenTable = new HashMap<>();
    private final HashMap<String, OpenGame> openGamesMap = new HashMap<>();
    private Chat publicChat;

    public Database() {
        this.users = new HashMap<>();
        this.games = new HashMap<>();
        for (int i = 1; i < 10; ++i) {
            this.addUser(new User(String.valueOf(i), String.valueOf(i), "nick" + i));
        }
        publicChat = new Chat(new ArrayList<>(users.values()),new NameAndToken(TokenGenerator.generate(8),"Public Chat"));
        System.out.println("public chat usernames " + Arrays.toString(publicChat.getUsernames().toArray()));
    }

    public String receiveTokenFor(User user) throws CommandException {
        if (token2userTable.containsValue(user)) {
            throw new CommandException(CommandResponse.USER_ALREADY_LOGGED_IN);
        }
        String token = TokenGenerator.generate(8);
        token2userTable.put(token, user);
        user2tokenTable.put(user, token);
        return token;
    }

    public void invalidateTokenFor(User user) {
        String token = user2tokenTable.get(user);
        if (token != null) {
            deleteFromTokens(token, user);
        }
    }

    private void deleteFromTokens(String token, User user) {
        token2userTable.remove(token);
        user2tokenTable.remove(user);
    }

    public void invalidateToken(String token) {
        User user = this.getUserByToken(token);
        if (user != null) {
            deleteFromTokens(token, user);
        }
    }

    public boolean isUsernameOnline(String username) {
        return user2tokenTable.containsKey(this.getUser(username));
    }

    public User getUserByToken(String token) {
        return token2userTable.get(token);
    }
    public Game getGameByToken(String token){
        return games.get(token);
    }

    public ArrayList<String> getInvitedGamesFor(User user) {
        return new ArrayList<>(games.values().stream().filter(game -> game.getUsers().stream().map(User::getUsername).toList().contains(user.getUsername())).map(Game::getToken).toList());
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(this.users.values());
    }

    public ArrayList<String> getAllUsernames() {
        return new ArrayList<>(this.users.values().stream().map(User::getUsername).toList());
    }

    public void addGame(Game game) {
        games.put(game.getToken(), game);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public boolean checkForUsername(String username) {
        return this.users.containsKey(username);
    }

    public boolean nicknameAlreadyExists(String nickname) {
        return this.users.values().stream().anyMatch(user -> user.getNickname().equals(nickname));
    }

    public void deserialize() {
//        deserializeUsers();
    }

    public static void setInstance(Database instance) {
        Database.instance = instance;
    }

    public void deserializeUsers() {

        ArrayList<User> copiedUsers = new ArrayList<>();
        try {
            File file = new File(Game.class.getResource("/database/users.xml").toExternalForm());
            if (!file.exists())
                return;
            String filePath = new String(Files.readAllBytes(Paths.get(Game.class.getResource("/database/users.xml").toExternalForm())));
            XStream xStream = new XStream();
            xStream.addPermission(AnyTypePermission.ANY);
            if (filePath.length() != 0) {
                Object selectedUsers = new XStream().fromXML(filePath, new TypeToken<List<User>>() {
                }.getType());
                User[] myUsers = new User[Array.getLength(selectedUsers)];
                for (int i = 0; i < Array.getLength(selectedUsers); i++) {
                    Array.set(myUsers, i, Array.get(selectedUsers, i));
                }
                copiedUsers.addAll(List.of(myUsers));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!copiedUsers.isEmpty()) {
            for (User user : copiedUsers) {
                this.users.put(user.getUsername(), user);
            }
        }
    }

    private void serializeUsers() {
        try {
            FileWriter writer;
            if (!Files.exists(Paths.get(Game.class.getResource("/database/users.xml").toExternalForm())))
                new File(Game.class.getResource("/database").toExternalForm()).mkdir();
            writer = new FileWriter(Game.class.getResource("/database/game.xml").toExternalForm(), false);
            XStream xStream = new XStream();
            writer.write(xStream.toXML(this.getAllUsers()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize() {
//        serializeUsers();
    }

    public void saveGame() {
        Game game = GameController.getGame();
        try {
            FileWriter writer;
            if (!Files.exists(Paths.get(Game.class.getResource("/database/game.xml").toExternalForm())))
                new File(Game.class.getResource("/database").toExternalForm()).mkdir();
            writer = new FileWriter(Game.class.getResource("/database/game.xml").toExternalForm(), false);
            XStream xStream = new XStream();
            writer.write(xStream.toXML(game));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Game openLastGame() {
        try {
            String filePath = new String(Files.readAllBytes(Paths.get(Game.class.getResource("/database/game.xml").toExternalForm())));
            XStream file = new XStream();
            file.addPermission(AnyTypePermission.ANY);
            if (filePath.length() != 0) {
                return (Game) file.fromXML(filePath);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public boolean checkPassword(String username, String password) {
        return this.users.get(username).passwordMatchCheck(password);
    }
    public void logoutUsers(){
        this.token2userTable.clear();
        this.user2tokenTable.clear();
    }
    public ArrayList<User> getOnlineUser(){
        return new ArrayList<User>(token2userTable.values());
    }
    public boolean isOnline(User user){
        return token2userTable.values().contains(user);
    }

    public Chat getChatByToken(String token) {
        for (Chat chat:
             chats) {
            if(chat.getToken().equals(token)){
                return chat;
            }
        }
        return null;
    }

    public HashMap<String, Game> getGames() {
        return games;
    }

    public void setPublicChat(Chat publicChat) {
        if(publicChat == null) publicChat = new Chat(new ArrayList<>(users.values()),new NameAndToken(TokenGenerator.generate(8),"Public Chat"));
        this.publicChat = publicChat;
    }
    public void addUserToPublicChat(User user){
        publicChat.addUser(user);
    }

    public Chat getPublicChat() {
        return publicChat;
    }

    public String createOpenGame(String adminToken, String name, int height, int width, int playerLimit, boolean isPrivate) {
        OpenGame openGame = new OpenGame(name, Database.getInstance().getUserByToken(adminToken), height, width, playerLimit, isPrivate);
        this.openGamesMap.put(openGame.getToken(), openGame);
        return openGame.getToken();
    }

    public OpenGame getOpenGameByToken(String token) {
        return openGamesMap.get(token);
    }

    public void removeFromAnyRoom(User user) {
        if (this.openGamesMap == null)
            return;
        for (OpenGame room : this.openGamesMap.values()) {
            if (room.getPlayers().contains(user)) {
                room.removePlayer(user);
                if (room.getPlayers().size() == 0) {
                    Database.getInstance().removeOpenGame(room);
                    return;
                }
                MainMenuController.reloadRoomForPlayers(room);
            }
        }
    }

    public ArrayList<Game> getRunningGamesOf(User user) {
        return new ArrayList<>(games.values().stream().filter(game -> game.getUsers().stream().map(User::getUsername).toList().contains(user.getUsername())).toList());
    }

    public ArrayList<OpenGame> getSomePublicOpenGames() {
        ArrayList<OpenGame> openGames = new ArrayList<>(openGamesMap.values().stream().filter(openGame -> !openGame.isPrivate()).toList());
        Collections.shuffle(openGames);
        return new ArrayList<>(openGames.subList(0, Math.min(Constants.GAME_LIST_ITEM_COUNT, openGames.size())));
    }

    public void removeOpenGame(OpenGame openGame) {
        openGamesMap.remove(openGame.getToken());
    }

    public ArrayList<Game> getSomePublicRunningGames() {
        ArrayList<Game> games = new ArrayList<>(this.games.values().stream().filter(game -> !game.isPrivate()).toList());
        Collections.shuffle(games);
        return new ArrayList<>(games.subList(0, Math.min(Constants.GAME_LIST_ITEM_COUNT, games.size())));
    }
}