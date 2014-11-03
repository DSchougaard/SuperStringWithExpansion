package dk.dtu.chp.solver;

import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dk.dtu.chp.decoder.SWEDecoder;
import dk.dtu.chp.readers.FileReader;
import dk.dtu.chp.readers.StdInReader;
import dk.dtu.chp.reducer.Reducer;

public class Main {

	public static void main(String[] args) {

//		Benchmarker.run(5000);
		
		try{
			if(args.length==1){
				String path=args[0];
				FileReader fr = new FileReader(path);
				SWEDecoder swed = new SWEDecoder();
				swed.parse(fr.read());
				Reducer reduction = new Reducer(swed, false);
				
				fr.output(reduction.start(), path);
			}else{
			
				StdInReader sir = new StdInReader();
				SWEDecoder swed = new SWEDecoder();
				swed.parse(sir.read());
				Reducer reduction = new Reducer(swed, false);
				
				sir.output(reduction.start());
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		


		
	}

}
