package handlers.validators;

import message.Message;
import message.PrintJobMessage;
import exceptions.ValidationError;

public class PrintJobValidator extends MessageValidator {

    @Override
    public void validateMessage(Message message) throws ValidationError {
	super.validateMessage(message);
	if(message.getMessage() == "") {
	    throw new ValidationError("Message Text empty");
	} 
	if (!(message instanceof PrintJobMessage)){
	    throw new ValidationError("Message is not a print message.");
	}
    }
}
