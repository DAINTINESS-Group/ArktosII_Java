package Aggregate;

import java.io.IOException;
import java.util.LinkedList;

import Tools.RowPack;

public class SortAvg extends SortAggregate{
	private String tuple, key, tempKey;
	private String[] splitTuple;
	private double sum;
	private int counter;
	
	
	public SortAvg(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
		super(fieldsTp, aggregateField, groupFields);
		
		key = new String();
	}

	
	@Override
	public RowPack getResultPack() {
		RowPack rp = new RowPack();

		try {
			while((tuple = bf.readLine()) != null){
				splitTuple = tuple.split("\\|");
				tempKey = splitTuple[groupFields.get(0)];
				for(int i=1; i<groupFields.size(); i++){
					tempKey += "|"+splitTuple[groupFields.get(i)];
				}
				
				if(key.equals(tempKey)){
					sum += Double.parseDouble(splitTuple[aggregateField]);
					counter++;
				}
				else if(key.isEmpty()){
					key = tempKey;
					sum = Double.parseDouble(splitTuple[aggregateField]);
					counter = 1;
				}
				else{
					rp.add(key+"|"+((double)sum/(double)counter));
					key = tempKey;
					sum = Double.parseDouble(splitTuple[aggregateField]);
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
			rp.add(key+"|"+((double)sum/(double)counter));
			key = new String();
			return rp;
		}

		return null;
	}
	
	
}
