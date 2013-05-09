package message;

public class MMSMessage<Host> extends Message<Host> {
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	} 

	}
