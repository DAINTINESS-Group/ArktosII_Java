package Logical;

import java.util.LinkedList;


public class Filter extends Node{
	private String oper, checkValue;
	private int field;
	
	
	public Filter(int id, int type, int field, String condition, String checkVal, String imgPath){
		super(id, type, imgPath);
		
		this.field = field-1;
		oper = condition;
		checkValue = checkVal;
		
	}

	
	@Override
	public String getLabel() {
		return "Column: "+ (field+1) +"\n"+ oper +" "+ checkValue;
	}

	@Override
	public String getOperator() {
		return oper;
	}

	@Override
	public String getCheckValue() {
		return checkValue;
	}

	@Override
	public int getFilterField() {
		return field;
	}

	
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
