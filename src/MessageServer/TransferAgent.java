package MessageServer;

import message.Message;

public interface TransferAgent {
	
	void forwardMessage(Message message); 
}
