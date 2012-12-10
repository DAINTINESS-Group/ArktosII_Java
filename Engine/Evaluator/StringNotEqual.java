package Evaluator;


public class StringNotEqual extends Evaluator_1T{
	private String checkVal;
	
	
	public StringNotEqual(int pos, String val){
		super(pos);
		
		checkVal = val;
	}
	
	
	@Override
	public boolean performCheck(String tuple) {
		String[] strtok = tuple.split("\\|");

		return !strtok[position].equals(checkVal);
	}
	
}
