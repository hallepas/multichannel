package clients;

import message.Message;

public interface MessageHandler {
	Message newMessage();
	void validateMessage(Message message) throws ValidationError;
}
