import message.Message;


public interface SubmissionAgent {
	void statusCheckIfUserExists(String address);
	void statusRecieveMessage(Message message); 
}
