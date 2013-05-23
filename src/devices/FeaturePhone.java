package devices;

import clients.MessageClient;
import message.MMSMessage;
import message.MessageType;
import message.SMSMessage;

/**
 * Ein Feature Phone kann nur SMS und MMS senden und empfangen.
 * 
 */
public class FeaturePhone extends Device {

	public FeaturePhone() {
		super(new MessageClient(new MessageType[]{MessageType.SMS, MessageType.MMS}));
	}
	
	
	public MMSMessage newMMS(){
		return (MMSMessage) newMessage(MessageType.MMS);
	}
	public SMSMessage newSMS(){
		return (SMSMessage) newMessage(MessageType.SMS);
	}

}
