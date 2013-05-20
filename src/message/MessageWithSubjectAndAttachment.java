package message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class MessageWithSubjectAndAttachment extends Message {

	private static final long serialVersionUID = 1L;

	private List<Attachment> attachments = new ArrayList<Attachment>();
	private String subject;

	public void addAttachment(String path) throws IOException {
		attachments.add(new Attachment(path));
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void saveAttachments(String path) throws IOException {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IOException("Path " + path + " is not a directory.");
		}
		for (Attachment attachment : attachments) {
			FileOutputStream fos = new FileOutputStream(path + attachment.getFileName());
			try {
				fos.write(attachment.getContent());
			} finally {
				fos.close();
			}
		}
	}

}