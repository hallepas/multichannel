package message;

public abstract class MessageWithSubjectAndAttachment extends Message {
	private List<Attachment> attachments;
	private String subject;
}
