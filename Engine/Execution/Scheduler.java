package Execution;

import java.util.LinkedList;

public abstract class Scheduler{
	protected LinkedList<Integer> schedulerList;
	
	public abstract int nextActivity();
	protected abstract void removeItem();
	
	
	public Scheduler(LinkedList<Integer> allItems){
		schedulerList = allItems;
	}
	
	
	public void removeFromList(int threadId){
		removeItem();
		
		for(int i=0; i<schedulerList.size(); i++){
			if(threadId == schedulerList.get(i)){
				schedulerList.remove(i);
				break;
			}
		}
	}
	
}
