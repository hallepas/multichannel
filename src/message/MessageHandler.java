package message;

import exceptions.ValidationError;

public interface MessageHandler {
	Message newMessage();
	void validateMessage(Message message) throws ValidationError;
}
