package dk.dtu.chp.solver;

import java.util.ArrayList;

import dk.dtu.chp.decoder.ParseException;
import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.readers.FileReader;
import dk.dtu.chp.reducer.Reducer;
import dk.dtu.chp.solver.utils.Timer;

public class Benchmarker {
	
	public static void run(int iterations){
				
		try{
			//SWEDecoder[] decoder = new SWEDecoder[6];	
			double[][] timings = new double[6][5];
		
			for( int i = 1 ; i <= 6; i++ ){

				
				//decoder[i-1] = new SWEDecoder("test0" + i + ".SWE");
				//decoder[i-1].parse();

				FileReader fr = new FileReader("test0" + i + ".SWE");
				ArrayList<String> content = fr.read();
				if( content == null ){
					System.out.println("read null");
				}
				
				Timer timer=new Timer();
				timer.start();
				
				
				
				for( int j = 0; j < iterations ; j++ ){
					timings[i-1][0]+= timer.difference();
					SWEDecoder swed = new SWEDecoder();
					swed.parse(content);
					timings[i-1][1]+= timer.difference();
					Reducer reduc = new Reducer(swed, false);
					timings[i-1][2]+= timer.difference();
					reduc.start();
					timings[i-1][3]+= timer.difference();
				}
				timings[i-1][4] += timer.time();
			}
			
			System.out.println("Running the Benchmark Suite at " + iterations + " iterations.");
			for( int i = 1 ; i <= 6 ; i++ ){
				System.out.println("File: test0" + i + ".SWE");
				System.out.printf("Total time: %.3f s\n", timings[i-1][4]);
				
				double avg1 = (double)timings[i-1][1];
				System.out.printf("parse time: %.3f s\n",avg1);
				
				double avg2 = (double)timings[i-1][2];
				System.out.printf("setup reducer time: %.3f s\n",avg2);
				
				double avg3 = (double)timings[i-1][3];
				System.out.printf("reducer time: %.3f s\n",avg3);
				
//				double avg4 = (double)timings[i-1][4]/(double)iterations;
//				System.out.printf("Average time per iteration: %.3f s\n",avg4);
			}
			

			
			
		}catch(ParseException e){
			System.err.println(e.getMessage());
			System.err.println(e.getErrorCause());
			
		}
		
		
		
	}

}
