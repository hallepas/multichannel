package handlers.validators;

import message.Message;
import exceptions.ValidationError;

public interface Validator {
	public void validateMessage(Message message) throws ValidationError;
}
