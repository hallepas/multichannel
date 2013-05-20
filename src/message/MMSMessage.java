package message;

import java.util.ArrayList;
import java.util.Date;

import org.omg.IOP.Encoding;

public class MMSMessage extends Message{

	public MMSMessage(String message, String from, String subject, Date date, ArrayList<String> to, Encoding encoding) {
		super(message, from, subject, date, to, encoding);
		// TODO Auto-generated constructor stub
	}

	
}
