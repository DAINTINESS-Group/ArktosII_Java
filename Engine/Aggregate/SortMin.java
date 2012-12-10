package Aggregate;

import java.io.IOException;
import java.util.LinkedList;

import Tools.RowPack;

public class SortMin extends SortAggregate{
	private String tuple, key, tempKey;
	private String[] splitTuple;
	private double min;
	
	
	public SortMin(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
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
					if(min > Double.parseDouble(splitTuple[aggregateField]))
						min = Double.parseDouble(splitTuple[aggregateField]);
				}
				else if(key.isEmpty()){
					key = tempKey;
					min = Double.parseDouble(splitTuple[aggregateField]);
				}
				else{
					if(fieldsType.get(aggregateField).equals("int"))
						rp.add(key+"|"+((int)min));
					else
						rp.add(key+"|"+min);
					
					key = tempKey;
					min = Double.parseDouble(splitTuple[aggregateField]);
					
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
				rp.add(key+"|"+((int)min));
			else
				rp.add(key+"|"+min);
			
			key = new String();
			return rp;
		}

		return null;
	}
	

}
