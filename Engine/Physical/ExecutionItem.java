package Physical;

import java.util.LinkedList;

import Execution.Monitor;
import GUI.Frame;
import Tools.DataQueue;
import Tools.Message;
import Tools.Msg;
import Tools.Status;
import Tools.RowPack;
import Tools.Mailbox;


public abstract class ExecutionItem implements Runnable{
	protected LinkedList<Integer> producers, consumers, busyConsumers;
	protected DataQueue packCollection;
	protected RowPack packToForward;
	protected Status status;
	protected Mailbox myBox;
	private String name;
	private long executionTime;
	private Thread t;
	protected int id;
	
	
	protected abstract void DataProcess();
	protected abstract void EndExecute();
	
	 
	public ExecutionItem(String threadName, int ID, LinkedList<Integer> produ, LinkedList<Integer> consu){
		name = threadName;
		id = ID;
		executionTime = 0;
		packCollection = new DataQueue();
		status = new Status();
		myBox = new Mailbox();
		busyConsumers = new LinkedList<Integer>();
		producers = new LinkedList<Integer>(produ);
		consumers = new LinkedList<Integer>(consu);
		
		t = new Thread(this, name);
		t.setPriority(8);
	}
	
	
	@Override
	public void run() {
		Frame.textArea.append("> "+name+" "+id+" starting!\n");

		while(!status.finished){
			InboxManagement();

			if(status.finished){
				break;
			}
			
			synchronized(this){
				while(status.stalled){
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			long startTime = System.currentTimeMillis();
			DataProcess();
			long endTime = System.currentTimeMillis();
			executionTime += endTime-startTime;

		}
		
		EndExecute();
	
		Frame.textArea.append(name+" "+id+" exiting! --> Time: "+ executionTime +"\n");
	}
	

	private void InboxManagement(){
		Message curMsg = myBox.receiveMsg();

		while(curMsg != null){
			switch(curMsg.getMsgType()){
				case Msg.Resume:
					status.stalled = false;
					break;
					
				case Msg.Stall:
					status.stalled = true;
					break;
					
				case Msg.EndOfData:
					for(int i=0; i<producers.size(); i++){
						if(producers.get(i).equals((curMsg.getMsgId()))){
							producers.remove(i);
							break;
						}
					}
					status.lastMessage = producers.isEmpty();
					status.stalled = false;
					break;
					
				case Msg.Terminate:
					status.finished = true;
					status.lastMessage = true;
					status.stalled = false;
					break;
			}
			
			curMsg = myBox.receiveMsg();
		}
	}

	
	protected boolean forwardToConsumers(){
		if(packToForward.isEmpty()){
			packToForward = null;
			return true;
		}
		else if(busyConsumers.isEmpty()){
			for(int i=0; i<consumers.size(); i++){
				if(!Monitor.allItems.get(consumers.get(i)).addPack(packToForward)){
					busyConsumers.add(consumers.get(i));
				}
			}
		}
		else{
			for(int i=0; i<busyConsumers.size(); i++){
				if(Monitor.allItems.get(busyConsumers.get(i)).addPack(packToForward)){
					busyConsumers.remove(i);
					i--;
				}
			}
		}
		
		if(busyConsumers.isEmpty()){
			packToForward = null;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean readyToForward() {
		boolean ready = true;
		
		if(!status.stalled){
			ready = false;
		}
		else{
			if(!consumers.isEmpty() && !packCollection.isEmpty()){
				for(int i=0; i<consumers.size(); i++){
					if(Monitor.allItems.get(consumers.get(i)).isFull()){
						ready = false;
						break;
					}
				}
			}
			else if(!producers.isEmpty() && packCollection.isEmpty()){
				ready = false;
			}
		}

		return ready;
	}

	
	public synchronized void sendMessage(Message msg){
		status.stalled = false;
		myBox.sendMsg(msg);
		this.notify();
	}
	
	protected synchronized void stallThread(){
		status.stalled = true;
		Monitor.sendMessage(new Message(id, Msg.Stall));
	}

	public boolean addPack(RowPack pack){
		return packCollection.addPack(pack);
	}
	
	public boolean isEmpty(){
		return packCollection.isEmpty();
	}
	
	public boolean isFull(){
		return packCollection.isFull();
	}

	public synchronized void signal(){
		status.stalled = false;
		this.notify();
	}

	public void start() {
		t.start();
	}
	
	public void stop() {
		t.interrupt();
	}

	public void join(){
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
