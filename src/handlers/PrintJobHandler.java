package handlers;

import exceptions.ValidationError;
import message.Message;
import message.PrintJobMessage;

public class PrintJobHandler extends MessageHandler {

	public PrintJobHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Message newMessage() {
		return new PrintJobMessage();
	}

	@Override
	public void validateMessage(Message message) throws ValidationError {
		if(message.getMessage() == "") {
			throw new ValidationError("Message Text empty");
		} 
		if (!(message instanceof PrintJobMessage)){
			throw new ValidationError("Message is not a print message.");
		}
	}

}
