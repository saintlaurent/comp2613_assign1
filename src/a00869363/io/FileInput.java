package a00869363.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInput {
	public static List<String> readFile(String fileName) throws IOException{
		List<String> info = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			reader.readLine(); //Ignore first line of data file
			String line = null;
			while((line = reader.readLine()) != null){
				info.add(line);
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}
}
