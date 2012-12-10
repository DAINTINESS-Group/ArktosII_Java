package Aggregate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import Sort.ExtSortWrapper;
import Sort.GoogleExtSortWrapper;
import Sort.UnixExtSortWrapper;
import Tools.EngineCore;
import Tools.RowPack;

public abstract class SortAggregate extends Aggregate{
	protected LinkedList<Integer> groupFields;
	protected int aggregateField;
	protected BufferedReader bf;
	private ExtSortWrapper esw;
	private String unsortedPath, sortedPath; 
	
	
	public abstract RowPack getResultPack();
	
	
	public SortAggregate(LinkedList<String> fieldsTp, int aggregField, LinkedList<Integer> grpFields){
		super(fieldsTp);
		
		if(EngineCore.isWindows){
			File dir = new File(System.getProperty("user.dir")+"\\Cache");
			if(!dir.exists())
				dir.mkdir();
	
			File[] files = dir.listFiles();
			unsortedPath = System.getProperty("user.dir")+"\\Cache\\aggregImp"+ files.length +".dt";
			sortedPath = System.getProperty("user.dir")+"\\Cache\\aggregExp"+ files.length +".dt";
		}
		else{
			File dir = new File("/"+System.getProperty("user.dir")+"/Cache");
			if(!dir.exists())
				dir.mkdir();
	
			File[] files = dir.listFiles();
			unsortedPath = "/"+System.getProperty("user.dir")+"/Cache/aggregImp"+ files.length +".dt";
			sortedPath = "/"+System.getProperty("user.dir")+"/Cache/aggregExp"+ files.length +".dt";
		}
		
		
		groupFields = grpFields;
		aggregateField = aggregField;
		esw = getExtSortWrapper();

	}
	
	
	public void spitData(RowPack tp){
		esw.exportFile(tp.getDataPack());
	}
	
	
	public void calculateResults(){
		closeExporter();
		sort();
		openImporter();
	}
	
	
	private void sort(){
		try {
			esw.sort();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void closeExporter(){
		esw.closeExporter();
	}
	
	
	private void openImporter(){
		try {
			bf = new BufferedReader(new FileReader(sortedPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public void closeAggregate(){
		try {
			bf.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ExtSortWrapper getExtSortWrapper(){
		if(EngineCore.isUnixSort){
			String allFlds = new String();
			for(int i=0; i<groupFields.size(); i++){
				allFlds += ((groupFields.get(i)+1) + ",");
			}
			
			return new UnixExtSortWrapper(unsortedPath, sortedPath, allFlds.substring(0, allFlds.length()-1), fieldsType);
		}
		else{
			return new GoogleExtSortWrapper(unsortedPath, sortedPath, groupFields, "asc", fieldsType);
		}
	}
	
}
