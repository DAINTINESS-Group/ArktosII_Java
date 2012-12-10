package Sort;

import java.io.IOException;
import java.util.LinkedList;

import Tools.EngineCore;


public class UnixExtSortWrapper extends ExtSortWrapper {
	private String sortCommand;
	private String cmd;
	
	
	public UnixExtSortWrapper(String inFileName, String outFileName, String allFields, LinkedList<String> fieldsType){
		super(inFileName, outFileName);

		sortCommand = "-t\"|\"";
		int pos;
		String[] fields = allFields.split(",");
		for(int i=0; i<fields.length; i++){
			if(fields[i].trim().endsWith("r") ){
				String fldPos = fields[i].substring(0, fields[i].length()-1).trim();
				pos = Integer.parseInt(fldPos)-1;
			}
			else{
				pos = Integer.parseInt(fields[i])-1;
			}
			
			if(fieldsType.get(pos).equalsIgnoreCase("string")){
				sortCommand += " -k" + fields[i].trim();
			}
			else if(fieldsType.get(pos).equalsIgnoreCase("double")){
				if(EngineCore.isWindows){
					sortCommand += " -gk" + fields[i].trim();
				}
				else{
					sortCommand += " -nk" + fields[i].trim();
				}
			}
			else{
				sortCommand += " -nk" + fields[i].trim();
			}
		}
		
		//sort.exe -t\"|\" -nk4 -nk5r TestData.dt -o sorted.dt
		if(EngineCore.isWindows){
			cmd = "cmd.exe /c " + System.getProperty("user.dir")+"\\UnixSort\\sort.exe " +sortCommand+ " " +inFile.getAbsolutePath()+ " -o " +outFile.getAbsolutePath();
		}
		else {
			cmd = "sort " +sortCommand+ " " +inFile.getAbsolutePath()+ " -o " +outFile.getAbsolutePath();
		}
	}
	
	
	@Override
	public void sort() {
		Process p = null;
		int exitVal = 0;
		
		if(EngineCore.isWindows){
			try {
				p = Runtime.getRuntime().exec(cmd);
				exitVal = p.waitFor();
			}
			catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		else{
			ProcessBuilder pb = new ProcessBuilder();
			pb.command("bash", "-c", cmd);
			
			try {
				p = pb.start();
				exitVal = p.waitFor();
			}
			catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(exitVal);
	}
	
}
