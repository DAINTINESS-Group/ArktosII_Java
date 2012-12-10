package Input_Output;

import java.io.IOException;
import java.util.LinkedList;

import Logical.Filter;
import Logical.Aggregator;
import Logical.Node;
import Logical.NotNull;
import Logical.RecordSet;
import Logical.Sorter;
import Tools.EngineCore;

public class TextParser extends Parser{
	private LinkedList<Node> scnr;
	
	
	public TextParser(String filePath){
		super(filePath);
		
		scnr = new LinkedList<>();
	}
	
	
	public LinkedList<Node> getFullScenario(){
		String line;
		
		try {
			while((line = bf.readLine()) != null){
				if(!(line.isEmpty() || line.startsWith("*") || line.startsWith("-"))){
					String[] ln = line.split("\\|",2);
					String[] details = ln[1].split("'");

					
					switch(ln[0]){
					case "Source":
						scnr.add(new RecordSet(Integer.parseInt(details[0]), Integer.parseInt(details[1]), details[2], "/Icons/dtsource.png"));
						break;
						
					case "Target":
						scnr.add(new RecordSet(Integer.parseInt(details[0]), Integer.parseInt(details[1]), details[2], "/Icons/target.png"));
						break;
						
					case "NotNull":
						scnr.add(new NotNull(Integer.parseInt(details[0]), Integer.parseInt(details[1]), Integer.parseInt(details[2]), "/Icons/filter.png"));
						break;
						
					case "Filter":
						scnr.add(new Filter(Integer.parseInt(details[0]), Integer.parseInt(details[1]), Integer.parseInt(details[2]), details[3], details[4], "/Icons/filter.png"));
						break;
						
					case "Sorter":
						if(details.length == 3){
							EngineCore.isUnixSort = true;
							String[] sortFlds = details[2].split("\\(");
							scnr.add(new Sorter(Integer.parseInt(details[0]), Integer.parseInt(details[1]), sortFlds[1].substring(0, sortFlds[1].length()-1), "/Icons/sort.png"));
						}
						else{
							EngineCore.isUnixSort = false;
							String[] sortFlds = details[2].split("\\(");
							
							scnr.add(new Sorter(Integer.parseInt(details[0]), Integer.parseInt(details[1]), sortFlds[1].substring(0, sortFlds[1].length()-1), details[3], "/Icons/sort.png"));
						}

						break;
						
					case "Aggregator":
						String[] aggrFuncFields = details[2].split("\\(");
						String[] groupFields = details[3].split("\\(");
						
						LinkedList<Integer> grFlds = new LinkedList<>();
						String[] gflds = groupFields[1].substring(0, groupFields[1].length()-1).split(",");
						for(int i=0; i<gflds.length; i++){
							grFlds.add(Integer.parseInt(gflds[i].trim()));
						}
						
						int aggregFld =0;
						try{
							aggregFld = Integer.parseInt(aggrFuncFields[1].substring(0, aggrFuncFields[1].length()-1));
						}
						catch(Exception ex){
							aggregFld = 0;
						}
						
						String aggregFunc = aggrFuncFields[0];
						
						if( !(grFlds.isEmpty() || aggregFunc.isEmpty() || aggregFld == 0) ){
							
						
							scnr.add(new Aggregator(Integer.parseInt(details[0]), Integer.parseInt(details[1]), grFlds, aggregFunc, aggregFld, details[4], "/Icons/group.png"));
							
							if(details[4].equalsIgnoreCase("hash")){
								EngineCore.isAggregationWithSort = false;
							}
							else{
								EngineCore.isAggregationWithSort = true;
							}
						}

						break;
						
					case "Producers":
						if(scnr.get(scnr.size()-1).getType() != 0){
							for(int i=0; i<details.length; i++){
								scnr.get(scnr.size()-1).addProducer(Integer.parseInt(details[i]));
							}
						}
						break;
						
					case "Consumers":
						if(scnr.get(scnr.size()-1).getType() != 1){
							for(int i=0; i<details.length; i++){
								scnr.get(scnr.size()-1).addConsumer(Integer.parseInt(details[i]));
							}
						}
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scnr;
	}

}
