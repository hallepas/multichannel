package handlers;

import handlers.validators.MMSValidator;
import message.MMSMessage;
import message.Message;

public class MMSHandler extends MessageWithAttachmentsHandler {

    public MMSHandler() {
	super(new MMSValidator());
    }

    @Override
    public Message newMessage() {
	return new MMSMessage();
    }

}
