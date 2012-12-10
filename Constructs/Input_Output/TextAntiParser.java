package Input_Output;

import java.util.LinkedList;

import Logical.Node;
import Tools.EngineCore;

public class TextAntiParser extends AntiParser{
	
	
	public TextAntiParser(String filePath){
		super(filePath);
	}

	
	public void setFullScenario(LinkedList<Node> scnr){
		pwr.println("----------------------------------- F O R M A T -------------------------------------\n"
					+"*Source|id'type'file\n"
					+"*Target|id'type'file\n"
					+"*NotNull|id'type'field\n"
					+"*Filter|id'type'field'condition\n"
					+"*Sorter|id'type'sortby(sortFields)'order\tOR\tSorter|id'type'sortby(sortFields)\n"
					+"*Aggregator|id'type'aggregateFunc(aggregateFields)'groupby(groupFields)'HashOrSort\n\n\n"
					+"--------------------------------- S C E N A R I O ----------------------------------\n");

		
		for(int i=0; i<scnr.size(); i++){
			LinkedList<Integer> consumers = scnr.get(i).getAllConsumers();
			LinkedList<Integer> producers = scnr.get(i).getAllProducers();
			String line = "";
			
			switch(scnr.get(i).getType()){
				case EngineCore.Reader:
					pwr.println("Source|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'"+scnr.get(i).getFilePath());
					
					for(int j=0; j<consumers.size(); j++){
						line += "'"+scnr.get(consumers.get(j)).getId();
					}
					pwr.println("Consumers|"+line.substring(1)+"\n");
					
					break;
					
				case EngineCore.Writer:
					pwr.println("Target|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'"+scnr.get(i).getFilePath());
					
					for(int j=0; j<producers.size(); j++){
						line += "'"+scnr.get(producers.get(j)).getId();
					}
					pwr.println("Producers|"+line.substring(1)+"\n");
					
					break;

				case EngineCore.NotNull:
					pwr.println("NotNull|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'"+(scnr.get(i).getFilterField()+1));
					
					for(int j=0; j<producers.size(); j++){
						line += "'"+scnr.get(producers.get(j)).getId();
					}
					pwr.println("Producers|"+line.substring(1));
					
					line = "";
					for(int j=0; j<consumers.size(); j++){
						line += "'"+scnr.get(consumers.get(j)).getId();
					}
					pwr.println("Consumers|"+line.substring(1)+"\n");
					
					break;
					
				case EngineCore.Filter:
					pwr.println("Filter|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'"+(scnr.get(i).getFilterField()+1)+"'"+scnr.get(i).getOperator()+"'"+scnr.get(i).getCheckValue());
					
					for(int j=0; j<producers.size(); j++){
						line += "'"+scnr.get(producers.get(j)).getId();
					}
					pwr.println("Producers|"+line.substring(1));
					
					line = "";
					for(int j=0; j<consumers.size(); j++){
						line += "'"+scnr.get(consumers.get(j)).getId();
					}
					pwr.println("Consumers|"+line.substring(1)+"\n");
					
					break;
					
				case EngineCore.Sorter:
					if(scnr.get(i).getSortFields().isEmpty()){
						pwr.println("Sorter|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'sortby("+scnr.get(i).getProperty()+")");
					}
					else{
						String srtFlds = new String();
						for(int j=0; j<scnr.get(i).getSortFields().size(); j++){
							srtFlds += (scnr.get(i).getSortFields().get(j)+1)+",";
						}
						srtFlds = srtFlds.substring(0, srtFlds.length()-1);
						
						
						pwr.println("Sorter|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'sortby("+ srtFlds +")'"+scnr.get(i).getProperty());
					}
					
					
					for(int j=0; j<scnr.get(i).getAllProducers().size(); j++){
						line += "'"+scnr.get(producers.get(j)).getId();
					}
					pwr.println("Producers|"+line.substring(1));
					
					line = "";
					for(int j=0; j<consumers.size(); j++){
						line += "'"+scnr.get(consumers.get(j)).getId();
					}
					pwr.println("Consumers|"+line.substring(1)+"\n");
					
					break;
					
				case EngineCore.Aggregator:
					String grpFlds = new String();
					for(int j=0; j<scnr.get(i).getGroupFields().size(); j++){
						grpFlds += (scnr.get(i).getGroupFields().get(j)+1)+",";
					}
					grpFlds = grpFlds.substring(0, grpFlds.length()-1);
					
					pwr.println("Aggregator|"+scnr.get(i).getId()+"'"+scnr.get(i).getType()+"'"+scnr.get(i).getAggregateFunc()+"("+(scnr.get(i).getAggregateField()+1)+")'Groupby("+ grpFlds +")'"+ (scnr.get(i).isHash()?"Hash":"Sort"));
					
					for(int j=0; j<producers.size(); j++){
						line += "'"+scnr.get(producers.get(j)).getId();
					}
					pwr.println("Producers|"+line.substring(1));
					
					line = "";
					for(int j=0; j<consumers.size(); j++){
						line += "'"+scnr.get(consumers.get(j)).getId();
					}
					pwr.println("Consumers|"+line.substring(1)+"\n");
					
					break;
					
			}
		}
	}
	
}
