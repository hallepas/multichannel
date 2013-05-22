package message;

/**
 * Das Enum dient auch als Factory für Messages.
 *
 */

public enum MessageType {
	SMS (SMSMessage.class), 
	MMS (MMSMessage.class), 
	EMAIL (EmailMessage.class), 
	PRINT (PrintJobMessage.class);
	
	private final Class<Message> type;
	
	MessageType(Class messageCls) {
		this.type = messageCls;
	}
	
	public Message instance(){
		try {
			return (Message) type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
