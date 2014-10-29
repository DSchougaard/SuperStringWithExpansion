package dk.dtu.chp.decoder;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to the Computationally Hard Problems Decoder.");
		
		
		try{
			SWEDecoder[] decoder = new SWEDecoder[6];
			
			for( int i = 0 ; i < 6; i++ ){
				decoder[i] = new SWEDecoder("test0"+(i+1)+".SWE");
				decoder[i].parse();
			}
		}catch(ParseException e){
			System.err.println(e.getMessage());
			System.err.println(e.getErrorCause());
			
		}
		
	}

}
