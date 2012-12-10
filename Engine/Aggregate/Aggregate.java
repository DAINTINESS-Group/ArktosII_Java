package Aggregate;

import java.util.LinkedList;

import Tools.RowPack;

public abstract class Aggregate {
	protected LinkedList<String> fieldsType;
	
	public abstract void spitData(RowPack tp);
	public abstract RowPack getResultPack();
	public abstract void calculateResults();
	public abstract void closeAggregate();

	
	public Aggregate(LinkedList<String> fieldsTp){
		fieldsType = new LinkedList<>();
		fieldsType.addAll(fieldsTp);
	}
	
}
