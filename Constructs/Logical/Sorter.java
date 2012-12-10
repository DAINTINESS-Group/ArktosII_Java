package Logical;

import java.util.LinkedList;


public class Sorter extends Node{
	private String sortFieldsLabel, property;
	private LinkedList<Integer> sortFields;

	
	public Sorter(int id, int itemType, String fields, String property, String imgPath){
		super(id, itemType, imgPath);
		
		sortFieldsLabel = fields;
		this.property = property;
		sortFields = new LinkedList<>();
		String[] sfields = fields.split(",");
		for(int i=0; i<sfields.length; i++){
			sortFields.add(Integer.parseInt(sfields[i].trim()) - 1);
		}
	}

	public Sorter(int id, int itemType, String fields, String imgPath){
		super(id, itemType, imgPath);
		
		this.sortFields = new LinkedList<>();
		sortFieldsLabel = fields;
		this.property = fields;
	}
	
	
	@Override
	public String getLabel() {
		if(sortFields.isEmpty()){
			return "Sort Fields :\n " + sortFieldsLabel;
		}
		else{
			return "Sort Fields :\n "+ sortFieldsLabel +"\nOrder by : "+ property;
		}
	}
	
	@Override
	public LinkedList<Integer> getSortFields() {
		return sortFields;
	}
	
	@Override
	public String getProperty() {
		return property;
	}
	
	
	@Override
	public int getFilterField() {return -1;}
	@Override
	public String getFilePath() {return null;}
	@Override
	public String getOperator() {return null;}
	@Override
	public String getCheckValue() {return null;}
	@Override
	public String getAggregateFunc() {return null;}
	@Override
	public int getAggregateField() {return -1;}
	@Override
	public LinkedList<Integer> getGroupFields() {return null;}
	@Override
	public boolean isHash() {return false;}

}
