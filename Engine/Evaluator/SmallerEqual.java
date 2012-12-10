package Evaluator;


public class SmallerEqual extends Evaluator_1T{
	private double checkVal;
	
	
	public SmallerEqual(int pos, String val){
		super(pos);
		
		checkVal = Double.parseDouble(val);
	}
	
	
	@Override
	public boolean performCheck(String tuple) {
		String[] strtok = tuple.split("\\|");
		if(Double.parseDouble(strtok[position]) <= checkVal)
			return true;
		
		return false;
	}
	
}
