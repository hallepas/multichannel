package message;

public class EmailMessage extends MessageWithSubjectAndAttachment {

    private static final long serialVersionUID = 1L;

    @Override
    public MessageType getType() {
        return MessageType.EMAIL;
    }
}