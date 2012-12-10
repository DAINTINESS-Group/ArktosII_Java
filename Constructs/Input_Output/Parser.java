package Input_Output;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import Logical.Node;

public abstract class Parser {
	protected BufferedReader bf;
	
	
	public abstract LinkedList<Node> getFullScenario();

	
	public Parser(String filePath){
		try {
			bf = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public void closeParser(){
		try {
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
