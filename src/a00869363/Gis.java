/**
 * Project: A00869363Gis
 * File: Gis.java
 * Date: Oct 24th, 2015
 * Time: 10:14 AM	
 */
/**
 * @author Catherine Li, A00869363
 * This is the main class which parses the command line arguments.
 *
 */
package a00869363;


import java.awt.EventQueue;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.ui.MainController;
import a00869363.ui.MainFrame;


public class Gis {
	
	private static final Logger LOG = LogManager.getLogger(Gis.class);
	static final String TOTAL = "total";
	static final String SORT_BY_GAME = "by_game";
	static final String SORT_BY_COUNT = "by_count";
	static final String SORT_DEFAULT = "by_gamertag";
	static final String DESC = "desc";
	static final String PLATFORM = "platform=";
	
	public static void main(String[] args) throws IOException {
		LOG.info("Starting main method");
		
		/**
		 * Launch the application.
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainController(new MainFrame()); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
