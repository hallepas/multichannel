package handlers;

import exceptions.ValidationError;
import message.MMSMessage;
import message.Message;

public class MMSHandler extends MessageWithAttachmentsHandler {

	public MMSHandler() {
		super();
	}

	@Override
	public Message newMessage() {
		return new MMSMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		super.validateMessage(message);

	}

}
