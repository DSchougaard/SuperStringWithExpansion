package dk.dtu.chp.readers;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
}
