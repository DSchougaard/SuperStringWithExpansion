package dk.dtu.chp.decoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SWEDecoder {
	// Filename that the decoder is tied to.
	private String filename;
	private boolean lock;
	
	private int k;
	private String s;
	private ArrayList<String> t;
	private HashMap<Character, ArrayList<String> > R;
	
	
	public SWEDecoder(String filename) { 
		this.filename = filename; 
		this.lock = true;
		
		t = new ArrayList<String>();
		R = new HashMap<Character, ArrayList<String>>();
	}
	
	
	public boolean parse() throws ParseException{
		Path file = FileSystems.getDefault().getPath(this.filename);
		
		if( !file.toFile().exists() ){
			throw new ParseException("File not found.", null);
		}
		
		try {
			List<String> content = Files.readAllLines(file, StandardCharsets.UTF_8);
			
			String k_string = content.get(0);
			if( !k_string.matches("[0-9]+") ){
				throw new ParseException("Error in parsing k", k_string);
			}
			
			this.k = Integer.parseInt(k_string);
			this.s = content.get(1);
			
			if( !this.s.matches("[a-z]+") ){
				throw new ParseException("Error in parsing s.", this.s);
			}
			
			int offset = 2;
			
			for( int i = 0; i < this.k ; i++ ){
				String t_string = content.get(offset+i);
				if( !t_string.matches("[A-Za-z]+")){
					throw new ParseException("Error in parsing entry " + i + " of t.", t_string);
				}			
				this.t.add(t_string);
			}
			
			for( int i = offset + this.k ; i < content.size() ; i++ ){
				String subset = content.get(i);
				
				String regex_match = "^[A-Z]:([a-z]+,)+[a-z]+";
				if( !subset.matches(regex_match) ){
					int t = i - 5; //offset
					throw new ParseException("Error in parsing entry " + t + " of R.", subset);
				}
				
				ArrayList<String> set = new ArrayList<String>( Arrays.asList( subset.substring(2, subset.length()).split(",") ) );
				this.R.put(subset.charAt(0), set);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("[SWE Decoder] File " + this.filename + " successfully decoded!");
		lock=false;
		return true;
	}
	

	public int getK(){
		if(!this.lock) return this.k;
		return 0;
	}
	
	public String getS(){
		if(!this.lock) return this.s;
		return null;
	}
	
	public ArrayList<String> getT(){
		if(!this.lock) return this.t;
		return null;
	}
	
	public HashMap<Character, ArrayList<String> > getR(){
		if(!this.lock) return this.R;
		return null;
	}
	
	
	
	
}
