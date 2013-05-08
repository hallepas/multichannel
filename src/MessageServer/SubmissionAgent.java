package MessageServer;

import message.Message;

interface SubmissionAgent {
	void checkIfUserExists(String address); 
	void recieveMessage(Message message); 
}
