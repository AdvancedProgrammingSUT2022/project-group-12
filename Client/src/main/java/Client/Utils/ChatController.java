package Client.Utils;

import Project.Enums.ChatType;
import Project.Models.Chat;
import Project.Models.Message;

public interface ChatController {

    abstract void updateChat(Chat chat);
    abstract void sendUpdateChatRequest(Chat chat);
    abstract void makeMessageEditable(Message message);
}
