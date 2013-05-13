import message.Message;

public interface SubmissionAgent {
	void checkIfUserExists (String address); 
	void recievMessage(Message message); 
}
