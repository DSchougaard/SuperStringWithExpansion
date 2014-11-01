package dk.dtu.chp.solver.theBrute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import dk.dtu.chp.decoder.SWEDecoder;




public class TheBrute {
	
	private String s;
	private ArrayList<Literal> r = new ArrayList<Literal>();

	
	private ArrayList<T> ts = new ArrayList<>();
	
	
	HashMap<Character, String> solution;
	
	public TheBrute(HashMap<Character, ArrayList<String>> R, String s, ArrayList<String> t){
		this.s=s;
		
		Set<Character> keys=R.keySet();
		int index=0;
		for (char key : keys) {
			r.add(new Literal(key, index, R.get(key)));

			index++;
		}
		
		for (String string : t) {
			ArrayList<Object> tmp= new ArrayList<>();
			for (int i = 0; i < string.length(); i++) {
				char c=string.charAt(i);
				if(Character.isUpperCase(c)){
					for (Literal l : r) {
						if(l.getName()==c){
							tmp.add(l);
							break;
						}
					}
				}else{
					tmp.add(c);
				}
			}
			ts.add(new T(tmp));
		}
//		System.out.println(s);
//		System.out.println("t: "+t);
//		System.out.println(ts);
//		System.out.println("R: "+R);
//		System.out.println("R: "+r);
	}
	
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
	
	public HashMap<Character, String> run2(){
		rec(new String[r.size()], 0);
		return solution;
	}
	
	
	private boolean rec(String[] sol, int index){
		
		if(index>=sol.length){
			
			return checkSolution(sol);
		}
		
		while(r.get(index).hasNext()){
			
			sol[index]= r.get(index).next();
			if(rec(sol, index+1)){
//				System.out.println(Arrays.toString(sol));
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
		solution = new HashMap<Character, String>();
		for (int i = 0; i < sol.length; i++) {
			solution.put(r.get(i).getName(), sol[i]);
		}
//		System.out.println("R: "+r.toString());
//		System.out.println("Solution: "+Arrays.toString(sol));
		return true;
	}
	
}
