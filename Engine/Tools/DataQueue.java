package Tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DataQueue {
	private BlockingQueue<RowPack> packs;
	private int maxSize;
	
	
	public DataQueue(){
		maxSize = EngineCore.engineQueueSize;
		packs = new ArrayBlockingQueue<RowPack>(maxSize);
	}
	
	
	public boolean addPack(RowPack pack){
		if(!pack.isEmpty())
			return packs.offer(pack);
		else
			return true;
	}
	
	public RowPack peekPack(){
		return packs.peek();
	}
	
	public RowPack pollPack(){
		return packs.poll();
	}
	
	public void removePack(){
		packs.remove();
	}
	
	
	public boolean isEmpty(){
		if(packs.isEmpty()){
			return true;
		}
			
		return false;
	}
	
	public boolean isFull(){
		if(packs.size() >= maxSize){
			return true;
		}	

		return false;
	}
	
}
