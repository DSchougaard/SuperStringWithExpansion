package dk.dtu.chp.solver;

import dk.dtu.chp.decoder.ParseException;
import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.reducer.Reducer;
import dk.dtu.chp.solver.utils.Timer;

public class Benchmarker {
	
	public static void run(int iterations){
				
		try{
			SWEDecoder[] decoder = new SWEDecoder[6];	
			double[] timings = new double[6];
		
			for( int i = 1 ; i <= 6; i++ ){
				decoder[i-1] = new SWEDecoder("test0" + i + ".SWE");
				decoder[i-1].parse();

				Timer timer= new Timer();
				timer.start();
				
				for( int j = 0; j < iterations ; j++ ){
					Reducer reduc = new Reducer(decoder[i-1], false);
					reduc.start();
				}
				timings[i-1] = timer.time();
			}
			
			System.out.println("Running the Benchmark Suite at " + iterations + " iterations.");
			for( int i = 1 ; i <= 6 ; i++ ){
				String format = "%-40s%s%n";
				System.out.printf(format, "File: ", "test0" + i + ".SWE");
				System.out.printf(format, "Total time: ", "" + timings[i-1] + "s");
			
				double avg = (double)timings[i-1]/(double)iterations;
				System.out.printf(format, "Average time per iteration: ", "" + avg + "s");
			}
			

			
			
		}catch(ParseException e){
			System.err.println(e.getMessage());
			System.err.println(e.getErrorCause());
			
		}
		
		
		
	}

}
