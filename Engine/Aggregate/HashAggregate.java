package Aggregate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Tools.RowPack;

public abstract class HashAggregate extends Aggregate{
	protected LinkedList<Integer> groupFields;
	protected int aggregateField;
	protected HashMap<String, List<String>> grouper;


	public abstract void spitData(RowPack packToForward);
	public abstract RowPack getResultPack();
	public abstract void calculateResults();

	
	public HashAggregate(LinkedList<String> fieldsTp, int aggregateField, LinkedList<Integer> groupFields){
		super(fieldsTp);
		
		this.groupFields = groupFields;
		this.aggregateField = aggregateField;
		this.grouper = new HashMap<String, List<String>>();
	}

	
	public void closeAggregate(){
		return;
	}
	
}
