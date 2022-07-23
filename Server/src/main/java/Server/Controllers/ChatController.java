package Server.Controllers;

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
        notifyUsers(chat, false);
    }

    private static void notifyUsers(Chat chat, Boolean isPublicChat) {
        //   System.out.println(Arrays.toString(chat.getUsernames().toArray()));
        for (User user :
                Database.getInstance().getAllUsers().stream().filter(e -> chat.getUsernames().contains(e.getUsername())).toList()) {
            if (Database.getInstance().isOnline(user)) {
                MenuStack menuStack = MenuStackManager.getInstance().getMenuStackOfUser(user);
                if (isPublicChat) menuStack.getUpdateNotifier().sendUpdatePublicChatRequest(chat);
                else menuStack.getUpdateNotifier().sendUpdateChatRequest(chat);
            }
        }
    }

    public static void sendMessage(Chat chat, Message message) {
        Database.getInstance().getChats().get(Database.getInstance().getChats().indexOf(chat)).getMessages().add(message);
        notifyUsers(chat, false);
    }

    public static void addUserToPublicChat(User user) {
        Database.getInstance().addUserToPublicChat(user);
        notifyUsers(Database.getInstance().getPublicChat(), true);
    }


    public static void updateChat(Chat updateChat) {
        Chat chat = Database.getInstance().getChatByToken(updateChat.getToken());
        Database.getInstance().getChats().remove(chat);
        Database.getInstance().getChats().add(updateChat);
        notifyUsers(updateChat, false);
    }

    public static void updatePublicChat(Chat newPublicChat) {
        Database.getInstance().setPublicChat(newPublicChat);
        notifyUsers(Database.getInstance().getPublicChat(), true);
    }
}
