package Logical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import GUI.Frame;


public class RecordSet extends Node{
	private String filePath;
	
	
	public RecordSet(int id, int itemType, String filePath, String imgPath){
		super(id, itemType, imgPath);
		
		
		this.filePath = filePath;
		if(itemType == 0){
			setFieldsType();
		}
	}
	
	
	private void setFieldsType(){
		File fl = new File(filePath);
		Frame.progressBar.setMaximum(4*(int) (fl.length()));
		
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String[] line = null;
		try {
			line = bf.readLine().split("\\|");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for(int i=0; i<line.length; i++){
			boolean isInt,isDouble;
			try{
				Double.parseDouble(line[i]);
				isDouble = true;
			}catch(Exception e){
				isDouble = false;
			}
			
			try{
				Integer.parseInt(line[i]);
				isInt = true;
			}catch(Exception e){
				isInt = false;
			}
			
			
			if(isInt){
				fieldsType.add("int");
			}
			else if(isDouble){
				fieldsType.add("double");
			}
			else{
				fieldsType.add("String");
			}
		}
		
		try {
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String getLabel() {
		String[] splitPath = filePath.split("\\\\");
		
		return splitPath[splitPath.length-1].split("\\.")[0];
	}
	
	@Override
	public String getFilePath() {
		return filePath;
	}
	
	
	@Override
	public String getOperator() {return null;}
	@Override
	public String getCheckValue() {return null;}
	@Override
	public int getFilterField() {return -1;}
	@Override
	public String getAggregateFunc() {return null;}
	@Override
	public int getAggregateField() {return -1;}
	@Override
	public LinkedList<Integer> getGroupFields() {return null;}
	@Override
	public LinkedList<Integer> getSortFields() {return null;}
	@Override
	public boolean isHash(){return false;}
	@Override
	public String getProperty() {return null;}
	
}
