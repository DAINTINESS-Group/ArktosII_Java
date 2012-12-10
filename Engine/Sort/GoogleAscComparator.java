package Sort;

import java.util.Comparator;
import java.util.LinkedList;

public class GoogleAscComparator implements Comparator<String>{
	private LinkedList<Integer> fields;
	private LinkedList<String> fieldsType;
	private String[] s01,s02;
	
	
	public GoogleAscComparator(LinkedList<Integer> fields, LinkedList<String> fieldsTp){
		
		this.fieldsType = new LinkedList<>();
		this.fieldsType.addAll(fieldsTp);
		
		this.fields = fields;
		
	}


	public int compare(String s1, String s2) {
		s01 = s1.split("\\|");
		s02 = s2.split("\\|");
		int cmpVal = 0;
		
		for(int col:fields){
			switch(fieldsType.get(col)){

			case "String":
				if( (cmpVal = -s01[col].compareTo(s02[col])) != 0)
					return cmpVal;
				
				break;
					
			default:
				if( (cmpVal = -Double.compare(Double.parseDouble(s01[col]), Double.parseDouble(s02[col]))) != 0)
					return cmpVal;
				
				break;
			
			}
		}
		
		return 0;
	}

}
