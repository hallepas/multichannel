package message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class MessageWithSubjectAndAttachment extends Message {

	private static final long serialVersionUID = 1L;

	private List<Attachment> attachments = new ArrayList<Attachment>();
	private String subject;

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public boolean hasAttachment() {
		return !attachments.isEmpty();
	}

	public void addAttachment(Attachment attachment) {
		this.attachments.add(attachment);
	}

	public void fillAttachements(ArrayList<Attachment> attachements) {
		if (attachements != null) {
			attachments.addAll(attachements);
		}
	}
}