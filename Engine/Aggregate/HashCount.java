package Aggregate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import GUI.Frame;
import Tools.RowPack;

public class HashCount extends HashAggregate{
	private Iterator<String> keyItr;
	
	
	public HashCount(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
		super(fieldsTp, aggregateField, groupFields);

	}


	@Override
	public RowPack getResultPack() {
		RowPack tp = new RowPack();

		while(keyItr.hasNext() && !tp.isFull()){
			String key = keyItr.next();
			tp.add(key+"|"+grouper.get(key).size());
		}
		
		if(!keyItr.hasNext() && tp.isEmpty()){
			return null;
		}
		
		return tp;
	}

	@Override
	public void spitData(RowPack tp) {
		for(String tuple:tp.getDataPack()){
			String tempKey = "";
			String[] splitTuple = tuple.split("\\|");
			for(int field:groupFields){
				tempKey += "|"+splitTuple[field];
			}
			
			String key = tempKey.substring(1, tempKey.length());
			if(!grouper.containsKey(key)){
				grouper.put(key, new LinkedList<String>());
			}
			
			grouper.get(key).add(splitTuple[aggregateField]);
			
			Frame.progressBar.setValue(Frame.progressBar.getValue() + tuple.length());
		}
	}


	@Override
	public void calculateResults() {
		Set<String> keys = grouper.keySet();
		keyItr = keys.iterator();
	}


}
