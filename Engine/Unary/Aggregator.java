package Unary;

import Aggregate.Aggregate;
import Aggregate.HashAvg;
import Aggregate.HashCount;
import Aggregate.HashMax;
import Aggregate.HashMin;
import Aggregate.HashSum;
import Aggregate.SortAvg;
import Aggregate.SortCount;
import Aggregate.SortMax;
import Aggregate.SortMin;
import Aggregate.SortSum;
import Execution.Monitor;
import Logical.Node;
import Physical.ExecutionItem;
import Tools.Message;
import Tools.Msg;

public class Aggregator extends ExecutionItem{
	private Aggregate aggregate;
	private boolean readRes;
	
	
	public Aggregator(String threadname, int Id, Node aggregator) {
		super(threadname, Id, aggregator.getAllProducers(), aggregator.getAllConsumers());
		
		readRes = false;
		aggregate = getAggregate(aggregator);
	}

	
	@Override
	protected void DataProcess() {
		if(packToForward != null && !forwardToConsumers()){
			stallThread();
		}
		else if(readRes){
			if((packToForward = aggregate.getResultPack()) == null){
				aggregate.closeAggregate();
				status.finished = true;
			}
			else if(!forwardToConsumers()){
				stallThread();
			}
		}
		else if(!packCollection.isEmpty()){
			aggregate.spitData(packCollection.pollPack());
		}
		else if(!readRes && status.lastMessage){
			aggregate.calculateResults();
			readRes = true;
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


	private Aggregate getAggregate(Node aggreg){
		if(aggreg.isHash()){
			switch(aggreg.getAggregateFunc()){
				case "Sum":
					return new HashSum(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Count":
					return new HashCount(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Min":
					return new HashMin(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Max":
					return new HashMax(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Avg":
					return new HashAvg(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
			}
		}
		else{
			switch(aggreg.getAggregateFunc()){
				case "Sum":
					return new SortSum(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Count":
					return new SortCount(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Min":
					return new SortMin(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Max":
					return new SortMax(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
				case "Avg":
					return new SortAvg(aggreg.getFieldsType(), aggreg.getAggregateField(), aggreg.getGroupFields());
					
			}
		}
		
		return null;
		
	}

}
