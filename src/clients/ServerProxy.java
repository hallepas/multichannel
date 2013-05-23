package clients;

import java.util.List;

import message.Message;
import message.Status;

public interface ServerProxy {
	List<Message> poll();
	Status put(Message message);
}
