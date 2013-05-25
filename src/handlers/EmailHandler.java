package handlers;

import message.EmailMessage;
import message.Message;
import exceptions.ValidationError;

public class EmailHandler extends MessageWithAttachmentsHandler {

	public EmailHandler() {
		super();
	}

	@Override
	public Message newMessage() {
		return new EmailMessage();
	}
	
	private void checkValidEmail(String address) throws ValidationError {
		if (!(address.indexOf('@') > 0 )) {
			throw new ValidationError("Email Adresse <" + address + "> hat kein @ Symbol.");
		} else if (address.split("@").length != 2) {
			throw new ValidationError("Email Adresse <" + address + "> hat falsches Format.");
		}
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		super.validateMessage(message);
		for (String to : message.getTo()) {
			checkValidEmail(to);
		}
		checkValidEmail(message.getFrom());
	}

}
