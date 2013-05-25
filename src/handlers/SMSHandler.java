package handlers;

import exceptions.ValidationError;
import message.Message;
import message.SMSMessage;

public class SMSHandler extends MessageHandler {

	public SMSHandler() {
		super();
	}

	@Override
	public Message newMessage() {
		return new SMSMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		super.validateMessage(message);
	}

}
