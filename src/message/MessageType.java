package message;

/**
 * Das Enum dient auch als Factory für Messages und User Agents.
 * Ev. schlechte Kohäsion.
 *
 */

public enum MessageType {
	SMS (SMSMessage.class, SMSUserAgent.class), 
	MMS (MMSMessage.class, MMSUserAgent.class), 
	EMAIL (EmailMessage.class, EmailUserAgent.class), 
	PRINT (PrintJobMessage.class, PrintJobUserAgent.class);
	
	private final Class<Message> type;
	private final Class<UserAgent> userAgent;
	
	MessageType(Class messageCls, Class userAgentCls) {
		this.type = messageCls;
		this.userAgent = userAgentCls;
	}
	
	public Message instance(){
		try {
			return (Message) type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public UserAgent agent(){
		try {
			return (UserAgent) userAgent.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {	
			e.printStackTrace();
		}
		return null;
	}
}
