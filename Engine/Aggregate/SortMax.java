package Aggregate;

import java.io.IOException;
import java.util.LinkedList;

import Tools.RowPack;

public class SortMax extends SortAggregate{
	private String tuple, key, tempKey;
	private String[] splitTuple;
	private double max;
	
	
	public SortMax(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
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
					if( max < Double.parseDouble(splitTuple[aggregateField]) )
						max = Double.parseDouble(splitTuple[aggregateField]);
				}
				else if(key.isEmpty()){
					key = tempKey;
					max = Double.parseDouble(splitTuple[aggregateField]);
				}
				else{
					if(fieldsType.get(aggregateField).equals("int"))
						rp.add(key+"|"+((int)max));
					else
						rp.add(key+"|"+max);
					
					key = tempKey;
					max = Double.parseDouble(splitTuple[aggregateField]);
					
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
			if(fieldsType.get(aggregateField).equals("int"))
				rp.add(key+"|"+((int)max));
			else
				rp.add(key+"|"+max);
			
			key = new String();
			return rp;
		}

		return null;
	}
	
	
}
