package Evaluator;

public abstract class Evaluator_1T{
	protected int position;
	
	
	public abstract boolean performCheck(String tuple);
	
	
	public Evaluator_1T(int position){
		this.position = position;
	}
	
}
