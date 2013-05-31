package devices;

import clients.MessageClient;
import message.EmailMessage;
import message.MMSMessage;
import message.MessageType;
import message.SMSMessage;

/**
 * Ein Smartphone kan SMS, MMS und EMails senden und empfangen.
 * 
 */
public class Smartphone extends Device {

	public Smartphone(String deviceName) {
		super(new MessageClient(new MessageType[]{MessageType.EMAIL, 
			  MessageType.SMS, MessageType.MMS}, false), deviceName);
	}
	
	public EmailMessage newEmail(){
		return (EmailMessage) newMessage(MessageType.EMAIL);
	}
	public MMSMessage newMMS(){
		return (MMSMessage) newMessage(MessageType.MMS);
	}
	public SMSMessage newSMS(){
		return (SMSMessage) newMessage(MessageType.SMS);
	}
	
}
