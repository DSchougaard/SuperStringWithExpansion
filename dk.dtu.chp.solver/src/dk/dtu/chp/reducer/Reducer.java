package dk.dtu.chp.reducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.solver.cleaner.Clean;

public class Reducer {
	// personal
	//ArrayList<String> substrings;
	HashSet<String> substrings;
	HashMap<Character, ArrayList<String>> personal_r;
	boolean print;
	ArrayList<String> reducedTList;
	
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
		this.reducedTList = new ArrayList<String>();
	}
	
	private void printSetSize(HashMap<Character, ArrayList<String>> set){
		for( int i = 65 ; i < 91 ; i++ ){
			char c = (char)i;
			
			if( set.containsKey(c) ){
				ArrayList<String> t = set.get(c);
				System.out.print("" + c + ": [" + t.size() + "] ");
				for(int j = 0;j<t.size();j++){
					System.out.print("" + t.get(j)+ ",");
				}
				System.out.println();
					
			}
			
		}
	}
	
	/**
	 * constructs all substrings of s 
	 */
	private void constructSubstrings(){
		for( int i = 0 ; i < decoder.getS().length() ; i++ ){
			for( int j = 1 ; j <= decoder.getS().length() - i ; j++ ){
				//this.substrings.add(decoder.getS().substring(i, j+i));
				this.substrings.add(decoder.getS().substring(i, j+i));
			}
		}
	}
	
	/**
	 * removes unused r values
	 */
	private void removeUnused(){
		boolean[] is = new boolean[26];
		for( int i = 0 ; i < this.decoder.getT().size() ; i++ ){
			for( int j = 0; j < this.decoder.getT().get(i).length() ; j++ ){

				if( Character.isUpperCase(this.decoder.getT().get(i).charAt(j)) ){ 
					int index = (int) this.decoder.getT().get(i).charAt(j) - 65;
					is[index] = true;
				}
			}
		}
		
		for( int i = 0 ; i < 26 ; i++ ){
			if( !is[i]){
				char c = (char) (i+65);
				this.personal_r.remove(c);
			}
		}
	}

	private void reduceTList(){		
		Collections.sort(this.decoder.getT(), new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1.length() < o2.length() ){
					return 1;
				}
				if(o1.length() > o2.length() ){
					return -1;
				}
				return 0;
			}
		});
		
		
		for( String s : this.decoder.getT() ){
			boolean flag = false;
			for( String s2 : this.reducedTList ){
				if(s2.contains(s)){
					flag = true;
					break;
				}
	
			}
			if(!flag){
				this.reducedTList.add(s);
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
		
	
	

	private HashMap<Character, ArrayList<String>> recursive(HashMap<Character, ArrayList<String>> set, String T, int t_index, HashMap<Character, String> assigned){
		if( T.length() == t_index ){
			StringBuilder sb= new StringBuilder();
			for( int i = 0 ; i < t_index ; i++ ){
				if( Character.isUpperCase(T.charAt(i)) ){
					sb.append(assigned.get(T.charAt(i)));
					
				}else{
					sb.append(T.charAt(i));

				}
			}
			String T2=sb.toString();
//			String T2 = "";
//			for( int i = 0 ; i < t_index ; i++ ){
//				if( Character.isUpperCase(T.charAt(i)) ){
//					T2 = T2 + assigned.get(T.charAt(i));
//				}else{
//					T2 = T2 + T.charAt(i);
//				}
//			}
			HashMap<Character, ArrayList<String>> basis_set = new HashMap<Character, ArrayList<String>>();
			if( T2 != "" && this.substrings.contains(T2) ){
				for( Character chr : assigned.keySet() ){
					ArrayList<String> basis_list = new ArrayList<String>();
					basis_list.add( assigned.get(chr) );
					basis_set.put(chr, basis_list);

				}
			}
			return basis_set;
		}
	
		char c = T.charAt(t_index);
		if( Character.isLowerCase(c) ){
			return recursive(set, T, t_index+1, assigned);
		}
		
		//ArrayList<String> temp = set.get(c);
		
		ArrayList<String> temp = null;
		if( set.get(c) == null && assigned.get(c) != null ){
			temp = new ArrayList<String>();
			temp.add(assigned.get(c));
		}else{
			temp = new ArrayList<String>(set.get(c));
		}
		
		HashMap<Character, ArrayList<String>> newSet = new HashMap<Character, ArrayList<String>>(set);
		newSet.remove(c);
		
		HashMap<Character, ArrayList<String>> accumulatedResult = new HashMap<Character, ArrayList<String>>();
		for( int j = 0 ; j < temp.size() ; j++ ){
			HashMap<Character, String> newAssigned = new HashMap<Character, String>(assigned);
			newAssigned.put(c, temp.get(j));
			
			HashMap<Character, ArrayList<String>> result = recursive(newSet, T, t_index+1, newAssigned);
			
			//For each of the elements, in the returned set 
			for( Character chr : result.keySet() ){
				ArrayList<String> insertIntoList = result.get(chr);	
				// Find the list of already known ties, based on the accumulated result
				ArrayList<String> mainList = accumulatedResult.get(chr);
				// Of the list is null, no previously data has been collected on this char
				if( mainList == null ){
					mainList = new ArrayList<String>();
				}
				
				for(String s : insertIntoList ){
					if(!mainList.contains(s)){
						mainList.add(s);
					}
				}
				
				if( accumulatedResult.get(chr) == null ){
					accumulatedResult.put(chr, mainList);
				}
				
				for( String s : (ArrayList<String>) result.get(chr) ){
					if( accumulatedResult.get(chr) != null && !accumulatedResult.get(chr).contains(s) ){
						accumulatedResult.get(chr).add(s);
					}
				}
			}	
		}
		return accumulatedResult;
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
	
	private void printResultList(HashMap<Character, ArrayList<String>> assigned){
		if( assigned == null ){
			System.out.println("NO");
		}else{
			for( Character c : assigned.keySet() ){
				System.out.println(c + ":" + assigned.get(c).get(0));
			}
		}
	}
	private void printResultList2(HashMap<Character, String> assigned){
		if( assigned == null ){
			System.out.println("NO");
		}else{
			for( Character c : assigned.keySet() ){
				System.out.println(c + ":" + assigned.get(c));
			}
		}
	}
	
	private ArrayList<String> ResultList(HashMap<Character, String> assigned){
		ArrayList<String> result = new ArrayList<String>();
		if( assigned == null ){
			result.add("NO");
		}else{
			for( Character c : assigned.keySet() ){
				result.add(c + ":" + assigned.get(c));
			}
		}
		return result;
	}
	
	public ArrayList<String> start(){
		

		this.removeUnused();
		
		this.constructSubstrings();
		HashMap<Character, ArrayList<String>> newR = new HashMap<Character, ArrayList<String>>();
		this.reduceSet(newR);
		
		
		this.reduceTList();



		
		HashMap<Character, String> assigned = new HashMap<Character, String>();
		HashMap<Character, ArrayList<String>> result = null;
		for(String s : this.reducedTList ){
			result = recursive(newR, s, 0, assigned);
			
			if( result.isEmpty() ){
				ArrayList<String> outResult = new ArrayList<String>();
				outResult.add("NO");
				return outResult;
			}
			
			for( Character c : result.keySet() ){
				newR.remove(c);
				newR.put(c, result.get(c));
			}
		}
		if(!newR.isEmpty()){
			Clean brutus= new Clean(newR, decoder.getS(), reducedTList);
			
			return ResultList(brutus.run2());
			
		}else{
			ArrayList<String> outResult = new ArrayList<String>();
			outResult.add("NO");

			return outResult;
		}


		
	}
	
	
	
	
	
	
}
