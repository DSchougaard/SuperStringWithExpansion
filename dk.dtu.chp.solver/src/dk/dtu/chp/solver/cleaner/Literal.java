package dk.dtu.chp.solver.cleaner;

import java.util.ArrayList;

public class Literal {
	
	private char name;
	private ArrayList<String> R;
	private int index;
	
	private int i=0;
	
	public Literal(char name, int index, ArrayList<String> R){
		this.name=name;
		this.index=index;
		this.R=R;
	}
	
	public int getIndex(){
		return index;
	}
	public ArrayList<String> getR(){
		return R;
	}

	
	public char getName(){
		return name;
	}
	
	public String toString(){
		return name + " : "+ R;
	}

	public boolean hasNext() {
		if(i>=R.size()){
			i=0;
			return false;
		}else{
			return true;
		}
	}

	public String next() {
//		if(i>=R.size()){
//			i=0;
//		}
		String string= R.get(i);
		i++;
		return string;
	}

}
