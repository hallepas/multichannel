package clients.handlers;

import handlers.validators.Validator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import exceptions.ValidationError;

import message.Attachment;
import message.Message;
import message.MessageWithSubjectAndAttachment;

public abstract class MessageWithAttachmentsHandler extends MessageHandler {

    protected MessageWithAttachmentsHandler(Validator validator) {
	super(validator);
    }

    public void saveAttachments(MessageWithSubjectAndAttachment message, String path) throws IOException {
	if (!Files.isDirectory(Paths.get(path))) {
	    throw new IOException("Path " + path + " is not a directory.");
	}
	for (Attachment attachment : ((MessageWithSubjectAndAttachment) message).getAttachments()) {
	    FileOutputStream fos = new FileOutputStream(path + attachment.getFileName());
	    try {
		fos.write(attachment.getContent());
	    } finally {
		fos.close();
	    }
	}
    }

    public void addAttachment(MessageWithSubjectAndAttachment message, String path) throws IOException {
	message.addAttachment(new Attachment(path));
    }
    public void validateMessage(Message message) throws ValidationError {
	super.validateMessage(message);
    }
}
