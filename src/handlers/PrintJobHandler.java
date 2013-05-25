package handlers;

import handlers.validators.PrintJobValidator;
import message.Message;
import message.PrintJobMessage;

public class PrintJobHandler extends MessageHandler {

	public PrintJobHandler() {
		super(new PrintJobValidator());
	}

	@Override
	public Message newMessage() {
		return new PrintJobMessage();
	}


}
