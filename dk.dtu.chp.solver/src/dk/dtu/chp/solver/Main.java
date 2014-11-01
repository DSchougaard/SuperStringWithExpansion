package dk.dtu.chp.solver;



import dk.dtu.chp.decoder.ParseException;
import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.reducer.Reducer;
import dk.dtu.chp.solver.theBrute.TheBrute;
import dk.dtu.chp.solver.utils.Timer;

public class Main {

	public static void main(String[] args) {
		
		
		Benchmarker.run(10000);
		
		
		
		SWEDecoder decoder = new SWEDecoder("test04.SWE");
		
		try {
			decoder.parse();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Timer timer= new Timer();
		timer.start();
		
		//TheBrute brutus = new TheBrute(decoder);
		Reducer reduc = new Reducer(decoder, false);
		reduc.start();

		//System.out.println("result: "+brutus.run());
		System.out.println("time: "+timer.time()+"s");
		
		
	}

}
