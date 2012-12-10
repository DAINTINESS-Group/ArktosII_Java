package Proxies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Execution.Monitor;
import Logical.Node;
import Physical.ExecutionItem;
import Tools.EngineCore;
import Tools.Message;
import Tools.Msg;
import Tools.RowPack;


public class Reader extends ExecutionItem{
	private BufferedReader bfr;
	private String line;

	
	public Reader(String threadName, int Id, Node reader) {
		super(threadName, Id, reader.getAllProducers(), reader.getAllConsumers());
		
		try {
			bfr = new BufferedReader(new FileReader(reader.getFilePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	protected void DataProcess() {
		try {
			if(packToForward == null){
				if(status.lastMessage){
					status.finished = true;
				} 
				else{
					packToForward = new RowPack();
					for(int i=0; i<EngineCore.enginePackSize; i++){
						if((line = bfr.readLine()) == null){
							status.lastMessage = true;
							break;
						}
						
						packToForward.add(line);
					}
				}
			}
			else if(!forwardToConsumers()){
				stallThread();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void EndExecute() {
		for(int i=0; i<consumers.size(); i++){
			Monitor.allItems.get(consumers.get(i)).sendMessage(new Message(id, Msg.EndOfData));
		}
		Monitor.sendMessage(new Message(id, Msg.Terminate));
		
		
		try {
			bfr.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
