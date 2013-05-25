package handlers.validators;

import message.Message;
import exceptions.ValidationError;

public class SMSValidator extends MessageValidator {
	
	@Override
	public void validateMessage(Message message) throws ValidationError {
		super.validateMessage(message);
		if(message.getMessage().length() > 160) {
			throw new ValidationError("SMS can only be 160 chars long.");
		}
	}
}
