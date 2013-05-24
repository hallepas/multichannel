package message;

import exceptions.ValidationError;

public class MMSUserAgent extends UserAgent {

	@Override
	public Message newMessage() {
		return new MMSMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		super.validateMessage(message);

	}

}
