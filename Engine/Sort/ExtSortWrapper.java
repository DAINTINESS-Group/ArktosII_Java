package Sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import Tools.EngineCore;
import Tools.RowPack;

public abstract class ExtSortWrapper{
	protected final File inFile, outFile;
	private PrintWriter pwr;
	private BufferedReader bf;
	
	
	public abstract void sort();
	
	
	public ExtSortWrapper(String inFileName, String outFileName){
		inFile = new File(inFileName);
		outFile = new File(outFileName);

		try {
			pwr = new PrintWriter(new FileWriter(inFile));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void exportFile(LinkedList<String> dataPack) {
		for(int i=0; i<dataPack.size(); i++){
			pwr.println(dataPack.get(i));
		}
	}

	
	public void closeExporter(){
		pwr.flush();
		pwr.close();
	}
	

	public void openImporter(){
		try {
			bf = new BufferedReader(new FileReader(outFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public RowPack importFile() {
		RowPack tmpPack = new RowPack();
		String line = new String();
		
		try {
			for(int i=0; i<EngineCore.enginePackSize; i++){
				if((line = bf.readLine()) == null)
					break;
				tmpPack.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(tmpPack.isEmpty())	return null;
		
		return tmpPack;
	}
	
	
	public void closeImporter(){
		try {
			bf.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
