package Evaluator;



public class NotNull extends Evaluator_1T{

	public NotNull(int pos){
		super(pos);
		
	}

	
	@Override
	public boolean performCheck(String tuple) {
		String[] strtok = tuple.split("\\|");
		if(strtok[position].isEmpty())
			return false;
		
		return true;
	}

}
