package Unary;

import java.io.File;

import Execution.Monitor;
import Logical.Node;
import Physical.ExecutionItem;
import Sort.ExtSortWrapper;
import Sort.GoogleExtSortWrapper;
import Sort.UnixExtSortWrapper;
import Tools.EngineCore;
import Tools.Message;
import Tools.Msg;


public class Sorter extends ExecutionItem{
	private String unsortedPath, sortedPath;
	private ExtSortWrapper esw;
	private boolean readSorted;
	
	
	public Sorter(String threadName, int Id, Node sorter) {
		super(threadName, Id, sorter.getAllProducers(), sorter.getAllConsumers());
		if(EngineCore.isWindows){
			File dir = new File(System.getProperty("user.dir")+"\\Cache");
			if(!dir.exists())
				dir.mkdir();
			File[] files = dir.listFiles();
			unsortedPath = System.getProperty("user.dir")+"\\Cache\\ExpToFile"+ files.length +".dt";
			sortedPath = System.getProperty("user.dir")+"\\Cache\\ImpSorted"+ files.length +".dt";
		}
		else{
			File dir = new File("/"+System.getProperty("user.dir")+"/Cache");
			if(!dir.exists())
				dir.mkdir();
			File[] files = dir.listFiles();
			unsortedPath = "/"+System.getProperty("user.dir")+"/Cache/ExpToFile"+ files.length +".dt";
			sortedPath = "/"+System.getProperty("user.dir")+"/Cache/ImpSorted"+ files.length +".dt";
		}
		
		readSorted = false;
		esw = getExtSortWrapper(sorter);
	}
	

	@Override
	protected void DataProcess() {
		if(packToForward != null && !forwardToConsumers()){
			stallThread();
		}
		else if(readSorted){
			if((packToForward = esw.importFile()) == null){
				esw.closeImporter();
				status.finished = true;
			}
			else if(!forwardToConsumers()){
				stallThread();
			}
		}
		else if(!packCollection.isEmpty()){
			esw.exportFile(packCollection.pollPack().getDataPack());
		}
		else if(!readSorted && status.lastMessage){
			esw.closeExporter();
			esw.sort();
			esw.openImporter();
			
			readSorted = true;
		}
		else{
			stallThread();
		}
	}


	@Override
	protected void EndExecute() {
		for(int i=0; i<consumers.size(); i++){
			Monitor.allItems.get(consumers.get(i)).sendMessage(new Message(id, Msg.EndOfData));
		}
		Monitor.sendMessage(new Message(id, Msg.Terminate));
	}
	
	
	private ExtSortWrapper getExtSortWrapper(Node sorter){
		if(sorter.getSortFields().isEmpty())
			return new UnixExtSortWrapper(unsortedPath, sortedPath, sorter.getProperty(), sorter.getFieldsType());
		else
			return new GoogleExtSortWrapper(unsortedPath, sortedPath, sorter.getSortFields(), sorter.getProperty(), sorter.getFieldsType());

	}

}
