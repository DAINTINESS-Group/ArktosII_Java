package Tools;

import Execution.Monitor;

public class Timer implements Runnable{
	private Thread t;
	private String name;
	private boolean finished, skip, ticktack;
	
	
	public Timer(){
		finished = false;
		skip = false;
		ticktack = true;
		name = "Timer";
		t = new Thread(this,name);
		t.setPriority(10);
	}
	
	
	@Override
	public void run() {
		while(!finished){
			skip = false;
			
			synchronized(this) {
				try {
					this.wait(EngineCore.engineTimeSlot);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(!skip && ticktack){
				Monitor.inTimeSlot = true;
			}
		}
	}
	
	public void start(){
		t.start();
		ticktack = true;
	}
	
	public synchronized void reset(){
		skip = true;
		this.notify();
	}
	
	public void stop(){
		ticktack = false;
	}
	
	public void join(){
		finished = true;
		skip = true;
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
