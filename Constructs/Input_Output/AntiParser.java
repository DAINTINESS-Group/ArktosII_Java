package Input_Output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import Logical.Node;

public abstract class AntiParser {
	protected PrintWriter pwr;
	
	
	public abstract void setFullScenario(LinkedList<Node> scenario);
	
	
	public AntiParser(String filePath){
		try {
			pwr =  new PrintWriter(new FileWriter(new File(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void closeAntiParser(){
		pwr.flush();
		pwr.close();
	}
	
}
