package Aggregate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Tools.RowPack;

public class HashMin extends HashAggregate{
	private Iterator<String> keyItr;
	private double min;
	
	public HashMin(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
		super(fieldsTp, aggregateField, groupFields);
		
	}

	@Override
	public RowPack getResultPack() {
		RowPack rp = new RowPack();

		while(keyItr.hasNext() && !rp.isFull()){
			String key = keyItr.next();
			List<String> res = grouper.get(key);
			min = Double.parseDouble(res.get(0));
			for(int i=1; i<res.size(); i++){
				min = Double.parseDouble(res.get(i))<min ? Double.parseDouble(res.get(i)) : min;
			}

			if(fieldsType.get(aggregateField).matches("int")){
				rp.add(key+"|"+(int)min);
			}
			else{
				rp.add(key+"|"+min);
			}
		}

		if(!keyItr.hasNext() && rp.isEmpty()){
			return null;
		}
		
		return rp;
	}
	
	
	@Override
	public void spitData(RowPack tp) {
		for(String tuple:tp.getDataPack()){
			String tempKey = new String();
			String[] splitTuple = tuple.split("\\|");
			for(int j=0; j<groupFields.size(); j++){
				tempKey += "|"+splitTuple[groupFields.get(j)];
			}
			
			String key = tempKey.substring(1, tempKey.length());
			if(!grouper.containsKey(key)){
				grouper.put(key, new LinkedList<String>());
			}
			grouper.get(key).add(splitTuple[aggregateField]);
		}
	}

	@Override
	public void calculateResults() {
		Set<String> keys = grouper.keySet();
		keyItr = keys.iterator();
	}


}
