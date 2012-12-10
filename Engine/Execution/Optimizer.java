package Execution;

import java.util.LinkedList;

import Logical.Node;

public class Optimizer {
	private LinkedList<Node> graph;

	public Optimizer(LinkedList<Node> AllNodes){
		graph = new LinkedList<>();
		for(int i=0; i<AllNodes.size(); i++){
			try {
				graph.add((Node) AllNodes.get(i).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public boolean isGraphCorrect(){
		return true;
	}
	
	
	private LinkedList<String> updateFields(LinkedList<Integer> groupFields, int aggregField, LinkedList<String> prodFields){
		LinkedList<String> uf = new LinkedList<>();
		
		for(int i=0; i<groupFields.size(); i++){
			uf.add( prodFields.get(groupFields.get(i)) );
		}
		uf.add( prodFields.get(aggregField) );
		
		return uf;
	}
	
	
	public LinkedList<Node> optimize(){
		LinkedList<Node> tempScn = new LinkedList<>();
		LinkedList<Integer> tempIds = new LinkedList<>();
		int startPos = 0, endPos = 0, curPos = 0;

		
		// 1 . Add SOURCES in tempScn...
		
		for(int i=0; i<graph.size(); i++){
			if(graph.get(i).getType() == 0){
				tempScn.add(graph.get(i));
				tempIds.add(graph.get(i).getId());
				curPos++;
			}
		}
		
		
		// 2 . Add THE POSITION OF PRODUCERS/CONSUMERS from previous objects in tempScn...
		
		while(curPos < graph.size()){
			startPos = endPos;
			endPos = curPos;
			
			for(int i=startPos; i<endPos; i++){
				LinkedList<Integer> curConsumez = new LinkedList<>();
				curConsumez.addAll(tempScn.get(i).getAllConsumers());
				tempScn.get(i).clearConsumers();

				for(int j=0; j<curConsumez.size(); j++){
					if(!tempIds.contains(curConsumez.get(j))){
						tempScn.get(i).addConsumer(curPos);
						
						int pos = getPosOfId(curConsumez.get(j));
						tempScn.add(graph.get(pos));
						tempIds.add(graph.get(pos).getId());
						
						tempScn.get(curPos).clearProducers();
						tempScn.get(curPos).addProducer(i);
						curPos++;
					}
					else{
						for(int k=0; k<tempIds.size(); k++){
							if(tempIds.get(k) == graph.get(getPosOfId(curConsumez.get(j))).getId()){
								tempScn.get(i).addConsumer(k);
								
								tempScn.get(k).addProducer(i);
								
								break;
							}
						}
					}
				}
			}
		}
		

		//Set Fields type to each Node based on Producers...
		//eg.After grouping number of fields become smaller or bigger...
		
		for(int i=0; i<tempScn.size(); i++){
			LinkedList<Integer> adjProd = tempScn.get(i).getAllProducers();

			if(!adjProd.isEmpty()){
				for(int j=0; j<adjProd.size(); j++){
					if(tempScn.get(adjProd.get(j)).getType() == 5){
						tempScn.get(i).setFieldsType( updateFields(tempScn.get(adjProd.get(j)).getGroupFields(), tempScn.get(adjProd.get(j)).getAggregateField() , tempScn.get(adjProd.get(j)).getFieldsType()) );
					}
					else{
						tempScn.get(i).setFieldsType(tempScn.get(adjProd.get(j)).getFieldsType());
					}
				}
			}
		}
		
		//printScnr(tempScn);
		
		return tempScn;
		
	}
	
	
	private int getPosOfId(int id){
		for(int i=0; i<graph.size(); i++){
			if(id == graph.get(i).getId()){
				return i;
			}
		}
		return -1;
	}
	
/*	
	private void printScnr(LinkedList<Node> tempScn){
		System.out.println("~~~~~~~~ OPTIMIZED SCENARIO ~~~~~~~~");
		for(int i=0; i<tempScn.size(); i++){
			System.out.print(tempScn.get(i).getId()+": ");
			LinkedList<Integer> adjCon = tempScn.get(i).getAllConsumers();
			LinkedList<Integer> adjProd = tempScn.get(i).getAllProducers();

			for(int j=0; j<adjCon.size(); j++){
				System.out.print("c_"+adjCon.get(j)+", ");
			}
			
			for(int k=0; k<adjProd.size(); k++){
				System.out.print("p_"+adjProd.get(k)+", ");
			}
			
			System.out.println("\n");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
*/
	
}
