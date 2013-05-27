package clients.handlers;

import handlers.validators.SMSValidator;
import message.Message;
import message.SMSMessage;

public class SMSHandler extends MessageHandler {

    public SMSHandler() {
	super(new SMSValidator());
    }

    @Override
    public Message newMessage() {
	return new SMSMessage();
    }

}
