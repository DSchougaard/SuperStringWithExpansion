
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class Reducer {
	// personal
	//ArrayList<String> substrings;
	HashSet<String> substrings;
	HashMap<Character, ArrayList<String>> personal_r;
	boolean print;
	
	// parsed
	SWEDecoder decoder;
	
	public Reducer(SWEDecoder decoder, boolean print){
		// Parsed variables
		this.decoder = decoder;
		
		// Personal variables
		this.print = print;
		//this.substrings = new ArrayList<String>();
		this.substrings = new HashSet<String>();
		this.personal_r = new HashMap<Character, ArrayList<String>>(this.decoder.getR());
	}
	
	private void printSetSize(HashMap<Character, ArrayList<String>> set){
		for( int i = 65 ; i < 91 ; i++ ){
			char c = (char)i;
			
			if( set.containsKey(c) ){
				ArrayList<String> t = set.get(c);
				System.out.println("" + c + ": " + t.size());
			}
			
		}
	}
	
	private void constructSubstrings(){
		for( int i = 0 ; i < decoder.getS().length() ; i++ ){
			for( int j = 1 ; j <= decoder.getS().length() - i ; j++ ){
				//this.substrings.add(decoder.getS().substring(i, j+i));
				this.substrings.add(decoder.getS().substring(i, j+i));
			}
		}
	}
	
	private void removeUnused(){
		int[] is = new int[26];
		for( int i = 0 ; i < this.decoder.getT().size() ; i++ ){
			for( int j = 0; j < this.decoder.getT().get(i).length() ; j++ ){

				if( Character.isUpperCase(this.decoder.getT().get(i).charAt(j)) ){ 
					int index = (int) this.decoder.getT().get(i).charAt(j) - 65;
					is[index] = 1;
				}
			}
		}
		
		for( int i = 0 ; i < 26 ; i++ ){
			if( is[i] == 0 ){
				char c = (char) (i+65);
				this.personal_r.remove(c);
			}
		}
	}
	
	private void reduceSet(HashMap<Character, ArrayList<String>> newR){
		for( int i = 65 ; i < 91 ; i++ ){
			char c = (char)i;
			
			// For each: A,B, ... Z
			if( this.personal_r.containsKey(c) ){
				// Get the list of replacements
				ArrayList<String> temp = this.personal_r.get(c);
				ArrayList<String> newSet = new ArrayList<String>();
				
				// for each of the replacements check if it exists in the permutations
				for( int j = 0 ; j < temp.size() ; j++ ){
					if( this.substrings.contains(temp.get(j))){
						newSet.add(temp.get(j));
					}
				}
				newR.put(c, newSet);	
			}
		}
	}
	
	private HashMap<Character, String> recursive(HashMap<Character, ArrayList<String>> set, ArrayList<String> T, HashMap<Character, String> assigned){
		// Completely naive depth first solution
		
		// Stop criteria
		if( set.isEmpty() ){
			ArrayList<String> testT = new ArrayList<String>();
			
			for( String s :  T ){
				String t = "";

				for( int i = 0 ; i < s.length() ; i++ ){
					
					if( Character.isUpperCase( s.charAt(i) ) ){
						t = t + assigned.get(s.charAt(i));
					}else{
						t = t + s.charAt(i);
					}
				}
				testT.add(t);

			}
			
			
			for( String s : testT ){
				if( !this.substrings.contains(s) ){
					return null;
				}
			}
			return assigned;
		}

		
		// Remove first entry in set, assign new variables in assigned.
		for( int i = 65 ; i < 91 ; i++ ){
			char c = (char)i;
			if( set.containsKey(c) ){
				HashMap<Character, ArrayList<String>> tempSet = new HashMap<Character, ArrayList<String>>(set);
				
				
				ArrayList<String> temp = tempSet.get(c);
				tempSet.remove(c);
				for( int j = 0 ; j < temp.size() ; j++ ){
					HashMap<Character, String> newAssigned = new HashMap<>(assigned);
					newAssigned.put(c, temp.get(j));
					
					HashMap<Character, String> result = recursive(tempSet, T, newAssigned);
					if( result != null ){
						return result;
					}
				}
			}
		}
		
		return null;
	}
	
	private void printResult(HashMap<Character, String> assigned){
		if( assigned == null ){
			System.out.println("NO");
		}else{
			for( Character c : assigned.keySet() ){
				System.out.println(c + ":" + assigned.get(c));
			}
		}
	}
	
	
	public void start(){
		if(this.print){
			System.out.println("Before reduction");
			this.printSetSize(this.decoder.getR());
		}
		this.removeUnused();
		
		this.constructSubstrings();
		HashMap<Character, ArrayList<String>> newR = new HashMap<Character, ArrayList<String>>();
		this.reduceSet(newR);
	
		if(this.print){
			System.out.println("After reduction");
			this.printSetSize(newR);
		}
		
		HashMap<Character, String> assigned = new HashMap<Character, String>();
		HashMap<Character, String> result = this.recursive(newR, this.decoder.getT(), assigned);
		this.printResult(result);

		
	}
	
	
	
	
	
	
}
