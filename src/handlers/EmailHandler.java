package handlers;

import handlers.validators.EmailValidator;
import message.EmailMessage;
import message.Message;

public class EmailHandler extends MessageWithAttachmentsHandler {

	public EmailHandler() {
		super(new EmailValidator());
	}

	@Override
	public Message newMessage() {
		return new EmailMessage();
	}

}
