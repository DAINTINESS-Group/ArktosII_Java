package Logical;

import java.util.LinkedList;


public class Aggregator extends Node{
	private int aggrField;
	private LinkedList<Integer> groupFields;
	private String aggrFunc;
	private boolean isHash;
	

	public Aggregator(int id, int itemType, LinkedList<Integer> groupFlds, String aggregFunc, int aggregFld, String isHashOrSort, String imgPath){
		super(id, itemType, imgPath);
		
		aggrFunc = aggregFunc;
		aggrField = aggregFld-1;
		if(isHashOrSort.equalsIgnoreCase("hash"))
			isHash = true;
		else
			isHash = false;
		
		groupFields = new LinkedList<>();
		for(int i=0; i<groupFlds.size(); i++){
			groupFields.add(groupFlds.get(i) - 1);
		}
		
	}
	

	@Override
	public String getLabel() {
		String gf = new String();
		for(int i=0; i<groupFields.size(); i++){
			gf += groupFields.get(i)+1;
		}
		
		return "Group by\nColumn: "+ gf +"\n"+ aggrFunc +"("+ (aggrField+1) +")";
	}

	public String getAggregateFunc(){
		return aggrFunc;
	}
	
	@Override
	public LinkedList<Integer> getGroupFields() {
		return groupFields;
	}

	public int getAggregateField(){
		return aggrField;
	}
	
	@Override
	public boolean isHash() {
		return isHash;
	}

	
	@Override
	public String getOperator() {return null;}
	@Override
	public String getCheckValue() {return null;}
	@Override
	public String getFilePath() {return null;}
	@Override
	public LinkedList<Integer> getSortFields() {return null;}
	@Override
	public String getProperty() {return null;}
	@Override
	public int getFilterField() {return -1;}

}
