package message;
import java.util.ArrayList;

class EmailMessage<Host> extends Message<Host> {
	private String subject; 
	ArrayList<Host> forwardPath = new ArrayList<Host>(); 
	ArrayList<Host> reversePath = new ArrayList<Host>();
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
