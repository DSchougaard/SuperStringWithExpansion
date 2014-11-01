

public class SuperStringWithExpansionSolver {

	public static void main(String[] args) {
		try{
			StdInReader sir = new StdInReader();
			SWEDecoder swed = new SWEDecoder();
			swed.parse(sir.read());
			Reducer reduction = new Reducer(swed, false);
			reduction.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
