package dk.dtu.chp.optimized_decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dk.dtu.chp.decoder.ParseException;

public class Decoder {
	// Filename that the decoder is tied to.
	private String filename;
	private boolean lock;
	
	private int k;
	private String s;
	private ArrayList<String> t;
	private HashMap<Character, ArrayList<String> > R;
	
	
	public Decoder(ArrayList<String> content) throws ParseException { 
		this.lock = true;
		t = new ArrayList<String>();
		R = new HashMap<Character, ArrayList<String>>();
		
		this.decode(content);
	}
	
	private void decode(ArrayList<String> content) throws ParseException{
		
		int K_INDEX 		= 0;
		int S_INDEX 		= 1;
		int OFFSET 			= 2;
		
		// Parse the K value
		String k_string 	= content.get(K_INDEX);
		try{
			this.k = Integer.parseInt( k_string );
		}catch(NumberFormatException e){
			throw new ParseException("Error in parsing k", k_string );
		}
		
		// Parse the string S
		String s_string 	= content.get(S_INDEX);
		for( int i = 0 ; i < s_string.length() ; i++ ){
			if( !Character.isLowerCase( s_string.charAt(i) ) ){
				throw new ParseException("Error in parsing s.", s_string);
			}
		}
		this.s = s_string;
		
		// Parse the k T-strings
		for( int i = 0; i < this.k ; i++ ){
			String t_string = content.get(OFFSET+i);
			
			for( int j = 0 ; j < t_string.length() ; j++ ){
				if( !Character.isUpperCase( t_string.charAt(j) )
				 && !Character.isLowerCase( t_string.charAt(j) ) ){
					throw new ParseException("Error in parsing entry " + i + " of t.", t_string);
				}
			}		
			this.t.add(t_string);
		}
		
		
		// Parse the subsets, R_1 to R_26 (max)
		for( int i = OFFSET + this.k ; i < content.size() ; i++ ){
			String subset = content.get(i);
			
			if( !Character.isUpperCase( subset.charAt(0) ) || subset.charAt(1) != ':' ){
				throw new ParseException("Error in parsing entry " + t + " of R.", subset);
			}
			
			
			for( int j = 2 ; j < subset.length() ; j++ ){
				if( !(Character.isLowerCase( subset.charAt(j) )
				  || Character.isUpperCase( subset.charAt(j) )
				  || subset.charAt(j) == (char)',') ){
						throw new ParseException("Error in parsing entry " + t + " of R.", subset);
				}
			}
			
			if( !(Character.isLowerCase( subset.charAt( subset.length()-1 ) )
			   || Character.isUpperCase( subset.charAt( subset.length()-1 ) ) ) ){
				throw new ParseException("Error in parsing entry " + t + " of R.", subset);
			}
			
			
			/*
			while( j < subset.length() && subset.charAt(j) != (char)10  ){
				while( j < subset.length() && ( Character.isLowerCase( subset.charAt(j) ) 
											  ||Character.isUpperCase( subset.charAt(j) ) ) ){
					j++;
				}
				if( !( subset.charAt(j) == ',' || subset.charAt(j) == (char)10 ) ){
					throw new ParseException("Error in parsing entry " + t + " of R.", subset);
				}
				j++;
			}*/
			
			ArrayList<String> set = new ArrayList<String>( Arrays.asList( subset.substring(2, subset.length()).split(",") ) );
			this.R.put(subset.charAt(0), set);
		}
		
		this.lock = false;
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
