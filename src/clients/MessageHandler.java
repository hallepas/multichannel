package clients;

import message.Message;
import message.MessageType;

public interface MessageHandler {
	Message newMessage();
	void validateMessage(Message message) throws ValidationError;
}
