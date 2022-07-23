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


    public static void createNewChat(Chat chat){
        Database.getInstance().getChats().add(chat);
        notifyUsers(chat);
    }

    private static void notifyUsers(Chat chat) {
     //   System.out.println("users notified");
     //   System.out.println(Arrays.toString(chat.getUsernames().toArray()));
        for (User user:
                Database.getInstance().getAllUsers().stream().filter(e -> chat.getUsernames().contains(e.getUsername())).toList()) {
           if(Database.getInstance().isOnline(user)) {
               //print
              // System.out.println(user.getUsername());
               MenuStack menuStack = MenuStackManager.getInstance().getMenuStackOfUser(user);
               menuStack.getUpdateNotifier().sendUpdateChatRequest(chat);
           }
        }
    }

    public static void sendMessage(Chat chat , Message message){
   //     System.out.println("send message");
        Database.getInstance().getChats().get(Database.getInstance().getChats().indexOf(chat)).getMessages().add(message);
        notifyUsers(chat);
    }


    public static void updateChat(Chat updateChat) {
       Chat chat = Database.getInstance().getChatByToken(updateChat.getToken());
       Database.getInstance().getChats().remove(chat);
       Database.getInstance().getChats().add(updateChat);
       notifyUsers(updateChat);
    }
}
