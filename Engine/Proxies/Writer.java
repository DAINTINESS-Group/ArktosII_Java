package Proxies;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import Execution.Monitor;
import Logical.Node;
import Physical.ExecutionItem;
import Tools.Message;
import Tools.Msg;

public class Writer extends ExecutionItem{
	private PrintWriter pwr;
	private LinkedList<String> dataPack;
	
	public Writer(String threadName, int Id, Node writer) {
		super(threadName, Id, writer.getAllProducers(), writer.getAllConsumers());
		
		try {
			pwr =  new PrintWriter(new FileWriter(writer.getFilePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void DataProcess() {
		if(!packCollection.isEmpty()){
			dataPack = packCollection.pollPack().getDataPack();
			for(int i=0; i<dataPack.size(); i++){
				pwr.println(dataPack.get(i));
			}
		}
		else if(status.lastMessage){
			status.finished = true;
		}
		else{
			stallThread();
		}
	}

	@Override
	protected void EndExecute() {
		Monitor.sendMessage(new Message(id, Msg.Terminate));
		pwr.close();
	}
	
}
