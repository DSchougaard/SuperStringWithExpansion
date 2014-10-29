package dk.dtu.chp.solver.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Substitute {

	private ArrayList<String> R;
	
	public Substitute(ArrayList<String> R){
		this.R = R;
	}
			
	public ArrayList<String> Perform(HashMap<String, String> map) {
		ArrayList<String> new_R = new ArrayList<String>();
		
		for( String s : R ){
			String new_s = "";
			for( int i = 0; i < s.length() ; i++ ){
				
				String t = String.valueOf( s.charAt(i) );
				if( map.containsKey(t) ){
					new_s = new_s + map.get(t);
				}else{
					new_s = new_s + t;
				}
				
			}
			new_R.add(new_s);
		}
		
		return new_R;
	}
}
