package Aggregate;

import java.io.IOException;
import java.util.LinkedList;

import Tools.RowPack;

public class SortSum extends SortAggregate{
	private String tuple, key, tempKey;
	private String[] splitTuple;
	private double sum;
	
	
	public SortSum(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
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
					sum += Double.parseDouble(splitTuple[aggregateField]);
				}
				else if(key.isEmpty()){
					key = tempKey;
					sum = Double.parseDouble(splitTuple[aggregateField]);
				}
				else{
					if(fieldsType.get(aggregateField).matches("int"))
						rp.add(key+"|"+((int)sum));
					else
						rp.add(key+"|"+sum);

					key = tempKey;
					sum = Double.parseDouble(splitTuple[aggregateField]);
					
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
			if(fieldsType.get(aggregateField).matches("int"))
				rp.add(key+"|"+((int)sum));
			else
				rp.add(key+"|"+sum);

			key = new String();
			return rp;
		}

		return null;
	}

	
}
