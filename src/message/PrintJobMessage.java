package message;

public class PrintJobMessage extends Message {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString(){
	return "Nachricht von " + getFrom() +":\n" + getMessage();
    }

    @Override
    public MessageType getType() {
	return MessageType.PRINT;
    }

}