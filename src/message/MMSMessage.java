package message;

public class MMSMessage extends MessageWithSubjectAndAttachment {

    private static final long serialVersionUID = 1L;

    @Override
    public MessageType getType() {
	return MessageType.MMS;
    }

}