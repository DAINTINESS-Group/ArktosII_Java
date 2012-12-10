package Tools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Mailbox {
	private BlockingQueue<Message> message;
	
	
	public Mailbox(){
		message = new LinkedBlockingQueue<Message>();
	}
	
	
	public void sendMsg(Message msg){
		message.add(msg);
	}
	
	public Message receiveMsg(){
		return message.poll();
	}
	
	public int getSize(){
		return message.size();
	}
	
}
