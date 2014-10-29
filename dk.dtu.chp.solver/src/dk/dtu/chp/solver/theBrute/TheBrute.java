package dk.dtu.chp.solver.theBrute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import dk.dtu.chp.decoder.SWEDecoder;




public class TheBrute {
	
	private String s;
	private ArrayList<Literal> r = new ArrayList<Literal>();

	
	
	private ArrayList<T> ts = new ArrayList<>();
	
	
	public TheBrute(SWEDecoder decoder){
		s=decoder.getS();
		Set<Character> keys= decoder.getR().keySet();
		int index=0;
		for (char key : keys) {
			r.add(new Literal(key, index, decoder.getR().get(key)));
			index++;
		}
				
		ArrayList<String> Ts =decoder.getT();

		for (String string : Ts) {
			ArrayList<Object> tmp= new ArrayList<>();
			for (int i = 0; i < string.length(); i++) {
				char c=string.charAt(i);
				if(Character.isUpperCase(c)){
					for (Literal l : r) {
						if(l.getName()==c){
							tmp.add(l);
						}
					}
				}else{
					tmp.add(c);
				}
			}
			ts.add(new T(tmp));
		}

	}
	
	
	public boolean run(){
		
		return rec(new String[r.size()], 0);
	}
	
	
	private boolean rec(String[] sol, int index){
		
		if(index>=sol.length){
			
			return checkSolution(sol);
		}
		
		while(r.get(index).hasNext()){
			
			sol[index]= r.get(index).next();
			if(rec(sol, index+1)){
				System.out.println(Arrays.toString(sol));
				return true;
			}
		}
		return false;
	}
	
	
	private boolean checkSolution(String[] sol){
		for (T t : ts) {
			if(!s.contains(t.getString(sol))){
				return false;
			}
			
		}
//		System.out.println("R: "+r.toString());
//		System.out.println("Solution: "+Arrays.toString(sol));
		return true;
	}
	
}
