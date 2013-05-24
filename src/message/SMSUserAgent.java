package message;

import exceptions.ValidationError;

public class SMSUserAgent extends UserAgent {

	@Override
	public Message newMessage() {
		return new SMSMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		super.validateMessage(message);
	}
	
}
