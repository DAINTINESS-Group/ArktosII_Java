package Execution;

import java.io.File;
import java.util.LinkedList;

import GUI.Frame;
import Logical.Node;
import Physical.ExecutionItem;
import Proxies.Reader;
import Proxies.Writer;
import Tools.EngineCore;
import Tools.Mailbox;
import Tools.Message;
import Tools.Msg;
import Tools.Status;
import Tools.Timer;
import Unary.Aggregator;
import Unary.Filter;
import Unary.Sorter;

public class Monitor implements Runnable{
	public static LinkedList<ExecutionItem> allItems;
	private static Mailbox Box;
	private Status status;
	private Scheduler scheduler;
	private Timer clockTimer;
	private boolean stopExecution;
	public static boolean inTimeSlot;
	private String name;
	private int id;
	private Thread t;
	
	
	public Monitor(String threadName){
		name = threadName;
		Box = new Mailbox();
		allItems = new LinkedList<ExecutionItem>();
		status = new Status();
		stopExecution = false;
		inTimeSlot = false;
		id = -1;					//Monitor id
		
		t = new Thread(this,name);
		t.setPriority(7);
	}

	
	@Override
	public void run() {
		while(true){
			synchronized(this) {
				while(status.stalled) {
					try {
						this.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(status.finished)
				break;
			
			riseScenario();
			monitoring();

			close();
		}
	}

	
	public void initializeScenario(LinkedList<Node> scenario){
		allItems.clear();
		
		for(int i=0; i<scenario.size(); i++){
			switch(scenario.get(i).getType()){
				case 0:
					allItems.add(new Reader("Source", i, scenario.get(i)));
					break;
					
				case 1:
					allItems.add(new Writer("Target", i, scenario.get(i)));
					break;
					
				case 2:
					allItems.add(new Filter("NotNull", i, scenario.get(i)));
					break;
					
				case 3:
					allItems.add(new Filter("Filter", i, scenario.get(i)));
					break;
					
				case 4:
					allItems.add(new Sorter("Sorter", i, scenario.get(i)));
					break;
					
				case 5:
					allItems.add(new Aggregator("Aggregator", i, scenario.get(i)));
					break;
				
			}
		}
		
	}		
	
	
	private void riseScenario(){
		clockTimer = new Timer();
		clockTimer.start();
		LinkedList<Integer> itemList = new LinkedList<>();
		for(int i=0; i<allItems.size(); i++){
			itemList.add(i);
			allItems.get(i).start();
		}
		
		if(EngineCore.engineScheduler == EngineCore.RoundRobin){
			scheduler = new RoundRobin(itemList);
		}
	}
	
	
	private void monitoring(){
		int totalItems = allItems.size();
		int finishedItems = 0;
		boolean done = false;
		boolean actStalledEndOfTS = true;
		int nextActivity = scheduler.nextActivity();
		Message curMsg = null;

		allItems.get(nextActivity).sendMessage(new Message(id,Msg.Resume));
		clockTimer.reset();
		inTimeSlot = false;


		while(!done && !stopExecution){
			if(inTimeSlot){
				if(actStalledEndOfTS){
					allItems.get(nextActivity).sendMessage(new Message(id,Msg.Stall));
				}
				
				int activity = scheduler.nextActivity();
				if(allItems.get(activity).readyToForward()){
					nextActivity = activity;
					allItems.get(activity).sendMessage(new Message(id,Msg.Resume));
					actStalledEndOfTS = true;
				}
				else
					actStalledEndOfTS = false;
				
				clockTimer.reset();
				inTimeSlot = false;
			}
			
			if((curMsg = Box.receiveMsg()) != null){
				switch(curMsg.getMsgType()){
				case Msg.Terminate:
					finishedItems++;
					scheduler.removeFromList(curMsg.getMsgId());
					
					if(finishedItems == totalItems)
						done = true;
					
					break;
					
				case Msg.Stall:
					int activity = scheduler.nextActivity();
					if(allItems.get(activity).readyToForward()){
						nextActivity = activity;
						allItems.get(activity).sendMessage(new Message(id,Msg.Resume));
						actStalledEndOfTS = true;
					}
					else
						actStalledEndOfTS = false;
					
					clockTimer.reset();
					inTimeSlot = false;
					
					break;
				}
			}
		}
	}

	public static void sendMessage(Message msg){
		Box.sendMsg(msg);
	}
	
	private void close(){
		clockTimer.join();
		
		if(stopExecution){
			for(int i=0; i<allItems.size(); i++)
				allItems.get(i).stop();

			stopExecution = false;
			allItems.clear();
		}
		else{
			for(int i=0; i<allItems.size(); i++)
				allItems.get(i).join();
			
			Frame.printClosingMsg();
		}
		
		status.stalled = true;
		
		File dir;
		if(EngineCore.isWindows)
			dir = new File(System.getProperty("user.dir")+"\\Cache");
		else
			dir = new File("/"+System.getProperty("user.dir")+"/Cache");
		
		if(dir.exists()){
			File[] files = dir.listFiles();
			for(File fil : files){
				fil.delete();
			}
		}
		dir.delete();
	}
	
	
	public synchronized void signal(){
		status.stalled = false;
		this.notify();
	}
	
	public synchronized void quit(){
		status.stalled = false;
		status.finished = true;
		this.notify();
	}
	
	public synchronized void stop(){
		stopExecution = true;
		this.notify();
	}
	
	public void start() {
		t.start();
	}

	public void join() {
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
