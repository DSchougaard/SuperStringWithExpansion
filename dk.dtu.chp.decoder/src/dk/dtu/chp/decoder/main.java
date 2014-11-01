package dk.dtu.chp.decoder;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to the Computationally Hard Problems Decoder.");
		
		
		try{
			SWEDecoder[] decoder = new SWEDecoder[6];
			ArrayList<String> content = null;
			
			for( int i = 0 ; i < 6; i++ ){
				try{
					int index = i+1;
					Path file = FileSystems.getDefault().getPath("test0" + index + ".SWE");
					content =  (ArrayList<String>) Files.readAllLines(file, StandardCharsets.UTF_8);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				decoder[i] = new SWEDecoder();
				decoder[i].parse(content);
			}
		}catch(ParseException e){
			System.err.println(e.getMessage());
			System.err.println(e.getErrorCause());
			
		}
		
	}

}
