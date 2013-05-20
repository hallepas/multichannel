package message;
import java.util.*;

import org.omg.IOP.Encoding;

public class EmailMessage<Host> extends Message{
	
	public EmailMessage(String message, String from, String subject, Date date, ArrayList<String> to, Encoding encoding) {
		super(message, from, subject, date, to, encoding);
	}
	
	ArrayList<String> to = new ArrayList<String>(); 
	ArrayList<Host> forwardPath = new ArrayList<Host>(); 
	ArrayList<Host> reversePath = new ArrayList<Host>();

}
