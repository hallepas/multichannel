package message;

import java.util.ArrayList;
import java.util.Date;

public class SMSMessage extends Message {

	private static final long serialVersionUID = 1L;

	public SMSMessage(){
		super();
	}
	
	// Konstruktor für Tests
	public SMSMessage(String message, String from, String subject, Date datum, 
			ArrayList<String> to, String something) {
		setMessage(message);
		setFrom(from);
		setDate(datum);
		setTo(to);
	}
	
}