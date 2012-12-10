package Unary;

import Evaluator.Bigger;
import Evaluator.BiggerEqual;
import Evaluator.Equal;
import Evaluator.Evaluator_1T;
import Evaluator.NotEqual;
import Evaluator.NotNull;
import Evaluator.Smaller;
import Evaluator.SmallerEqual;
import Evaluator.StringBigger;
import Evaluator.StringBiggerEqual;
import Evaluator.StringEqual;
import Evaluator.StringNotEqual;
import Evaluator.StringSmaller;
import Evaluator.StringSmallerEqual;
import Execution.Monitor;
import Logical.Node;
import Physical.ExecutionItem;
import Tools.Message;
import Tools.Msg;
import Tools.RowPack;

public class Filter extends ExecutionItem{
	private Evaluator_1T evaluator_1T;

	
	public Filter(String threadname, int Id, Node filter) {
		super(threadname, Id, filter.getAllProducers(), filter.getAllConsumers());
		
		if(threadname.equalsIgnoreCase("notnull"))
			evaluator_1T = new NotNull(filter.getFilterField());
		else
			evaluator_1T = getSingleEvaluator(filter);

	}

	
	@Override
	protected void DataProcess() {
		if(packToForward == null){
			if(!packCollection.isEmpty()){
				packToForward = new RowPack(packCollection.pollPack().getDataPack());
			
				for(int i=0; i<packToForward.getSize(); i++){
					if(!evaluator_1T.performCheck(packToForward.getTuple(i))){
						packToForward.remove(i);
						i--;
					}
				}
				
				if(!forwardToConsumers()){
					stallThread();
				}
				
			}
			else if(status.lastMessage){
				status.finished = true;
			}
		}
		else if(!forwardToConsumers()){
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

	
	
	private Evaluator_1T getSingleEvaluator(Node filter){
		if(filter.getFieldsType().get(filter.getFilterField()).equalsIgnoreCase("string")){
			switch(filter.getOperator()){
			case "<>":
				return new StringNotEqual(filter.getFilterField(), filter.getCheckValue());
				
			case "=":
				return new StringEqual(filter.getFilterField(), filter.getCheckValue());
				
			case ">=":
				return new StringBiggerEqual(filter.getFilterField(), filter.getCheckValue());
				
			case ">":
				return new StringBigger(filter.getFilterField(), filter.getCheckValue());
				
			case "<=":
				return new StringSmallerEqual(filter.getFilterField(), filter.getCheckValue());
				
			case "<":
				return new StringSmaller(filter.getFilterField(), filter.getCheckValue());
				
			}
		}
		else{
			switch(filter.getOperator()){
			case "<>":
				return new NotEqual(filter.getFilterField(), filter.getCheckValue());
				
			case "=":
				return new Equal(filter.getFilterField(), filter.getCheckValue());
				
			case ">=":
				return new BiggerEqual(filter.getFilterField(), filter.getCheckValue());
				
			case ">":
				return new Bigger(filter.getFilterField(), filter.getCheckValue());
				
			case "<=":
				return new SmallerEqual(filter.getFilterField(), filter.getCheckValue());
				
			case "<":
				return new Smaller(filter.getFilterField(), filter.getCheckValue());
				
			}
		}

		return null;
		
	}
	
}
