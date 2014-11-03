package dk.dtu.chp.solver.cleaner;

import java.util.ArrayList;

public class T {
	
	//
	ArrayList<Object> things = new ArrayList<Object>();
	
	public T(ArrayList<Object> things){
		this.things=things;
	}
	
	public String getString(String[] Rtrans){
		
		StringBuilder sb = new StringBuilder();
		for (Object t : things) {
			if(t instanceof Literal){
				sb.append(Rtrans[((Literal) t).getIndex()]);
			}else{
				sb.append(t);
			}
		}
		
		return sb.toString();
	}
	
	public String toString(){
		String string="";
		
		for (Object object : things) {
			if(object instanceof Literal){
				string+=((Literal) object).getName();
			}else{
				string+=object;
			}
		}
		
		
		return string;
	}

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}
}
