package Aggregate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import GUI.Frame;
import Tools.RowPack;

public class HashAvg extends HashAggregate{
	private Iterator<String> keyItr;
	private double sum;
	
	
	public HashAvg(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
		super(fieldsTp, aggregateField, groupFields);
	}
	

	@Override
	public RowPack getResultPack() {
		RowPack rp = new RowPack();
	
		while(keyItr.hasNext() && !rp.isFull()){
			String key = keyItr.next();
			List<String> res = grouper.get(key);
			sum = Double.parseDouble(res.get(0));
			for(int i=0; i<res.size(); i++){
				sum += Double.parseDouble(res.get(i));
			}
			
			rp.add(key+"|"+((double)sum/(double)grouper.get(key).size()));
		}			
		
		if(!keyItr.hasNext() && rp.isEmpty()){
			return null;
		}
		
		return rp;
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
