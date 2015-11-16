package a00869363;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import a00869363.data.Player;
import a00869363.io.GamesFormat;
import a00869363.io.Leaderboard;
import a00869363.io.LeaderboardReportWriter;
import a00869363.io.PersonaFormat;
import a00869363.io.PlayerFormat;
import a00869363.io.ScoreFormat;



public class TestMenu {
	
	private static LeaderboardReportWriter lrw; 
	public ByGameActionListener test1(){
		ByGameActionListener x = this.new ByGameActionListener();
		return x;
	}
	public static void main(String[] args) {
		TestMenu testMenu = new TestMenu();
		//Upload all information
		PlayerFormat pf = new PlayerFormat(); 
		PersonaFormat personaFormat = new PersonaFormat(); 
		GamesFormat gamesFormat = new GamesFormat();
		ScoreFormat scoreFormat = new ScoreFormat();
		lrw = new LeaderboardReportWriter();
	
		// Create the menu bar
	    JMenuBar menuBar = new JMenuBar();

	    // Create a menu
	    /**
	     * 
	     */
	    JMenu menuFile = new JMenu(MenuName.File.toString());
	    JMenuItem quitMenuItem = new JMenuItem("Quit");
	    quitMenuItem.addActionListener(new QuitActionListener());
	    menuFile.add(quitMenuItem);
	    
	    JMenu menuLists = new JMenu(MenuName.Lists.toString());
	    JMenuItem playerMenuItem = new JMenuItem("Players");
	    playerMenuItem.addActionListener(new PlayerActionListener());
	    menuLists.add(playerMenuItem);
	    menuLists.add(new JMenuItem("Personas"));
	    menuLists.add(new JMenuItem("Scores"));
	    
	    JMenu menuReports = new JMenu(MenuName.Reports.toString());
	    menuReports.add(new JMenuItem("Total"));
	    menuReports.add(new JMenuItem("Descending"));
	    JMenuItem byGameMenuItem = new JMenuItem("By Game");
	    byGameMenuItem.addActionListener(testMenu.test1());
	    menuReports.add(byGameMenuItem);
	    
	    menuReports.add(new JMenuItem("By Count"));
	    menuReports.add(new JMenuItem("Gamertag"));
	    
	    JMenu menuHelp = new JMenu(MenuName.Help.toString());
	    menuHelp.add(new JMenuItem("About"));
	    
	    menuBar.add(menuFile);
	    menuBar.add(menuLists);
	    menuBar.add(menuReports);
	    menuBar.add(menuHelp);

	    // Install the menu bar in the frame
		//Create and configure frame
		JFrame frame = new JFrame();
		frame.setSize(800,  800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	    frame.setJMenuBar(menuBar);
	    
	}
	private enum MenuName {
		File, Lists, Reports, Help;
	}
	private class ByGameActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {			
			List<Leaderboard> leaderboardRows = lrw.getLeaderboardRows("game_name");
			for(Leaderboard lb :leaderboardRows ){
				System.out.println(lb.toString());
			}
		}
	}
	
}
	class QuitActionListener implements ActionListener {
	  public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	  }
	}
	
	
	class PlayerActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PlayerFormat playerFormat = new PlayerFormat();
			List<Player> players = playerFormat.getListOfPlayers();
			
			final JFrame frame = new JFrame();
			frame.setSize(600, 600);
			frame.setVisible(true);
			
			frame.setLocationRelativeTo(null);
			
			 JFrame.setDefaultLookAndFeelDecorated(true);
			    
			   
			    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    String[] playerFirstAndLast = new String[players.size()];
			    
			    for (int i = 0; i < playerFirstAndLast.length; i++) {
			    	playerFirstAndLast[i] = players.get(i).getfName() + " " + players.get(i).getlName();
				}
			    
			    JList<String> list = new JList<String>(playerFirstAndLast);
			    list.addMouseListener(new MouseAdapter() {
				    public void mouseClicked(MouseEvent evt) {
				        JList<String> list = (JList<String>)evt.getSource();
				        if (evt.getClickCount() == 2) {
		
				            // Double-click detected
				            int index = list.locationToIndex(evt.getPoint());
				            System.out.println(list.getSelectedValue());
				            
				        } 
				    }
				});
			    frame.add(new JScrollPane(list));
			    frame.setVisible(true);
			
		}
	}
	
