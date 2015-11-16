/**
 * Project: A00869363Gis
 * File: WriteToFile.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This class contains a method to write to a text file
 *
 */
package a00869363.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WriteToFile {
	private static final Logger LOG = LogManager.getLogger(WriteToFile.class);
	
	public static void writeReports(String stringToOutput, String fileName){
		try {
			LOG.info("Writing to " + fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(stringToOutput);
			writer.close();
		} catch (IOException e) {
			
			LOG.error("Error writing to " + fileName);
		}		
	}
	public static void appendToFile(String stringToOutput, String fileName){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
			writer.write(stringToOutput);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error("Error appending to " + fileName);
		}		
	}
}
