/**
 * Project: A00869363Gis
 * File: FileInput.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class deals only with reading files
 *
 */
package a00869363.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileInput {
	private static final Logger LOG = LogManager.getLogger(FileInput.class);
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
			LOG.error("Could not read from " + fileName);
		}
		return info;
	}
}
