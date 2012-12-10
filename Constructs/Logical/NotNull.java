package Logical;

import java.util.LinkedList;


public class NotNull extends Node{
	private int field;
	
	
	public NotNull(int id, int type, int field, String imgPath){
		super(id, type, imgPath);
		
		this.field = field-1;
	}

	
	@Override
	public String getLabel() {
		return "Not Null\n" + "Column: "+(field+1);
	}

	@Override
	public int getFilterField() {
		return field;
	}
	

	@Override
	public String getOperator() {return null;}
	@Override
	public String getCheckValue() {return null;}
	@Override
	public String getFilePath() {return null;}
	@Override
	public String getAggregateFunc() {return null;}
	@Override
	public int getAggregateField() {return -1;}
	@Override
	public LinkedList<Integer> getGroupFields() {return null;}
	@Override
	public LinkedList<Integer> getSortFields() {return null;}
	@Override
	public boolean isHash() {return false;}
	@Override
	public String getProperty() {return null;}
	
}
