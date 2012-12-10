package Execution;

import java.util.LinkedList;

public class RoundRobin extends Scheduler{
	private int position, activity;
	
	
	public RoundRobin(LinkedList<Integer> allItems){
		super(allItems);
		
		position = 0;
		activity = 0;
	}
	

	@Override
	public int nextActivity() {
		activity = schedulerList.get(position);
		position = (position + 1) % schedulerList.size();
		
		return activity;
	}
	

	@Override
	protected void removeItem() {
		if(position > 0)
			position--;
	}
	
}
