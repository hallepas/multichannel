package message;

/**
 * Das Enum dient auch als Factory für Messages.
 *
 */

public enum MessageType {
	SMS ("SMS", SMSMessage.class), 
	MMS ("MMS", MMSMessage.class), 
	EMAIL ("Email", EmailMessage.class), 
	PRINT ("Print", PrintJobMessage.class);
	
	private final Class<Message> type;
	private final String typeName;
	
	MessageType(String typeName, Class messageCls) {
		this.type = messageCls;
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
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
