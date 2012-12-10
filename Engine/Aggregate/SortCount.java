package Aggregate;

import java.io.IOException;
import java.util.LinkedList;

import Tools.RowPack;

public class SortCount extends SortAggregate{
	private String tuple, key, tempKey;
	private String[] splitTuple;
	private int counter;
	
	
	public SortCount(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
		super(fieldsTp, aggregateField, groupFields);
		
		 key = new String();
	}
	
	
	@Override
	public RowPack getResultPack() {
		RowPack rp = new RowPack();

		try {
			while((tuple=bf.readLine()) != null){
				splitTuple = tuple.split("\\|");
				tempKey = splitTuple[groupFields.get(0)];
				for(int i=1; i<groupFields.size(); i++){
					tempKey += "|"+splitTuple[groupFields.get(i)];
				}
				
				if(key.equals(tempKey)){
					counter++;
				}
				else if(key.isEmpty()){
					key = tempKey;
					counter = 1;
				}
				else{
					rp.add(key+"|"+counter);
					key = tempKey;
					counter = 1;
					
					if(rp.isFull()){
						return rp;
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if(!key.isEmpty()){
			rp.add(key+"|"+counter);
			key = new String();
			
			return rp;
		}

		return null;
	}
	
	
}
