package Server.Controllers;

import Project.Enums.ChatType;
import Project.Models.Chat;
import Project.Models.Message;
import Project.Models.User;
import Project.Utils.TokenGenerator;
import Server.Models.Database;
import Server.Utils.MenuStackManager;
import Project.Utils.NameAndToken;
import Server.Views.MenuStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ChatController {


    public static void createNewChat(Chat chat) {
        Database.getInstance().getChats().add(chat);
        notifyUsers(chat, ChatType.NORMAL_CHAT);
    }

    private static void notifyUsers(Chat chat, ChatType chatType) {
        //   System.out.println(Arrays.toString(chat.getUsernames().toArray()));
        for (User user :
                Database.getInstance().getAllUsers().stream().filter(e -> chat.getUsernames().contains(e.getUsername())).toList()) {
            if (Database.getInstance().isOnline(user)) {
                MenuStack menuStack = MenuStackManager.getInstance().getMenuStackOfUser(user);
                menuStack.getUpdateNotifier().sendUpdateChatRequest(chat,chatType);
            }
        }
    }



    public static void addUserToPublicChat(User user) {
        Database.getInstance().addUserToPublicChat(user);
        notifyUsers(Database.getInstance().getPublicChat(), ChatType.PUBLIC_CHAT);
    }


    public static void updateChat(Chat updateChat, ChatType chatType) {
        Chat chat = Database.getInstance().getChatByToken(updateChat.getToken());
        switch (chatType) {
            case NORMAL_CHAT -> {
                Database.getInstance().getChats().remove(chat);
                Database.getInstance().getChats().add(updateChat);
            }
            case PUBLIC_CHAT -> {
                Database.getInstance().setPublicChat(updateChat);
            }
            case LOBBY_CHAT -> {
                Database.getInstance().updateLobbyChat(updateChat);
            }
        }
        notifyUsers(updateChat, chatType);
    }


}
