package dk.dtu.chp.solver;



import dk.dtu.chp.decoder.ParseException;
import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.readers.FileReader;
import dk.dtu.chp.readers.StdInReader;
import dk.dtu.chp.reducer.Reducer;
import dk.dtu.chp.solver.theBrute.TheBrute;
import dk.dtu.chp.solver.utils.Timer;

public class Main {

	public static void main(String[] args) {
		
		Timer timer= new Timer();
		timer.start();

		try{
			StdInReader sir = new StdInReader();
			FileReader fr = new FileReader("test06.SWE");
			SWEDecoder swed = new SWEDecoder();
			swed.parse(fr.read());
			Reducer reduction = new Reducer(swed, false);
			reduction.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//System.out.println("time: "+ timer.time() + "s");

		
		//Benchmarker.run(3);
		
	}

}
