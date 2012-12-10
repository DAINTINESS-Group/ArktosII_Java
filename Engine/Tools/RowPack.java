package Tools;

import java.util.LinkedList;

public class RowPack implements Cloneable{
	private LinkedList<String> tuples;
	private int maxSize;
	
	
	public RowPack(){
		maxSize = EngineCore.enginePackSize;
		tuples = new LinkedList<>();
	}
	
	public RowPack(LinkedList<String> allTuples){
		maxSize = 100;
		tuples = new LinkedList<>();
		tuples.addAll(allTuples);
	}
	
	
	@SuppressWarnings("unchecked")
	public RowPack clone() throws CloneNotSupportedException{
		RowPack clone = (RowPack) super.clone();
		
		clone.tuples = (LinkedList<String>) tuples.clone();
		
		return clone;
	}
	
	
	public boolean add(String tuple){
		if(tuples.size() < maxSize){
			tuples.add(tuple);
			return true;
		}
		
		return false;	
	}
	
	public LinkedList<String> getDataPack(){
		return tuples;
	}
	
	public String getTuple(int pos){
		if(pos < tuples.size())
			return tuples.get(pos);
		else
			return null;
	}
	
	public String poll(){
		return tuples.pollFirst();
	}
	
	public void remove(int pos){
		tuples.remove(pos);
	}
	
	
	public int getSize(){
		return tuples.size();
	}
	
	public boolean isEmpty(){
		if(tuples.isEmpty()){
			return true;
		}
		
		return false;
	}
	
	public boolean isFull(){
		if(tuples.size() == maxSize){
			return true;
		}
		
		return false;
	}
	
}
