package handlers.validators;

import java.util.Date;

import message.Message;
import exceptions.ValidationError;

public abstract class MessageValidator implements Validator {

    /**
     * Diese Methode stellt sicher, dass die Nachricht den Standards 
     * entspricht und versendet werden kann.
     * @param message
     * @throws ValidationError
     */
    public void validateMessage(Message message) throws ValidationError {
	if (!(message.getDate() instanceof Date)) {
	    throw new ValidationError("No date specified");
	}
	if (message.getReminder() != null && message.getDate()
					    .before(message.getReminder()) ){
	    throw new ValidationError("Reminder cannot be set on a message " +
	    			      "that has already been sent.");
	}
    }
}
