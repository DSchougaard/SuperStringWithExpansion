package dk.dtu.chp.solver;



import dk.dtu.chp.decoder.ParseException;
import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.solver.theBrute.TheBrute;
import dk.dtu.chp.solver.utils.Timer;

public class Main {

	public static void main(String[] args) {
		SWEDecoder decoder = new SWEDecoder("test01.swe");
		try {
			decoder.parse();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timer timer= new Timer();
		timer.start();
		TheBrute brutus = new TheBrute(decoder);
		System.out.println("result: "+brutus.run());
		System.out.println("time: "+timer.time()+"s");
	}

}
