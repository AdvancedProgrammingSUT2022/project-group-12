package Server.Controllers;

import Project.Models.Chat;
import Project.Models.User;
import Project.Utils.TokenGenerator;
import Server.Models.Database;
import Server.Utils.NameAndToken;

import java.util.ArrayList;

public class ChatController {


    public void createNewChat(ArrayList<User> users,String name){
        NameAndToken nameAndToken = new NameAndToken(name, TokenGenerator.generate(8));
        Database.getInstance().getChats().put(nameAndToken,new Chat(users,nameAndToken.getName()));

    }


}
