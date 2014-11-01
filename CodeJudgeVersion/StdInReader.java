

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StdInReader {
	
	public StdInReader(){}

	public ArrayList<String> read(){
		ArrayList<String> contents = new ArrayList<String>();
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			
			while( (input = br.readLine()) != null ){
				contents.add(input);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if( br != null ){
				try{
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return contents;
	}
	
}
