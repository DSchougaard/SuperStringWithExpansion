package dk.dtu.chp.readers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileReader {
	
	private String filename;

	public FileReader(String filename){
		this.filename = filename;
	}
	
	public ArrayList<String> read(){
		try{
			Path file = FileSystems.getDefault().getPath(this.filename);
			return (ArrayList<String>) Files.readAllLines(file, StandardCharsets.UTF_8);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void output(ArrayList<String> output, String path) throws IOException{
		Files.write(Paths.get(path.substring(0, path.length()-3)+"SOL"), output);
	}
}
