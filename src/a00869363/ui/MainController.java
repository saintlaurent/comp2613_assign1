package a00869363.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import a00869363.dao.Database;
import a00869363.dao.LeaderboardDAO;
import a00869363.dao.PersonasDAO;
import a00869363.dao.ScoresDAO;
import a00869363.io.Leaderboard;

public class MainController {
	private MainFrame mainFrame;
	Database database;
	LeaderboardDAO leaderboardDao;
	PersonasDAO personasDao;
	ScoresDAO scoresDao;
	
	public MainController(MainFrame mainFrame) {
		super();
		
		database = Database.getDatabaseInstance();
		leaderboardDao = LeaderboardDAO.getLeaderboardDao();
		personasDao = PersonasDAO.getPersonasDAO();
		this.mainFrame = mainFrame;
		
		this.mainFrame.addByGameActionListener(new ByGameActionListener());
		this.mainFrame.addGamertagActionListener(new GamertagActionListener());
		this.mainFrame.addByCountActionListener(new ByCountActionListener());
		this.mainFrame.addTotalsActionListener(new TotalsActionListener());
	}
	
	class ByGameActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LeaderboardDAO dao = LeaderboardDAO.getLeaderboardDao();
			try {
			    List<Leaderboard> rows = dao.getLeaderboardRows("byGame", MainFrame.sortDescending);
			    new LeaderboardDialog(rows);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				new ErrorDialog("Error generating leaderboard report by game");
			}				
			
		}		
	}
	
	class GamertagActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			  JTextField gamertagFilter = new JTextField(15);
		      JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Gamertag:"));
		      myPanel.add(gamertagFilter);
		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Please Enter Gamertag", JOptionPane.OK_CANCEL_OPTION);
		      if (result == JOptionPane.OK_OPTION) {
		    	  if(personasDao.selectByGamertag(gamertagFilter.getText().trim()) == null){
		    		  JOptionPane.showMessageDialog(null, "Gamertag not found.");
		    	  } else {
		    		  MainFrame.filterGamertag = gamertagFilter.getText();
		    	  }		    	  
		      }
		}		
	}
	
	class ByCountActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			try {
			    List<Leaderboard> rows = leaderboardDao.getLeaderboardRows("byCount", MainFrame.sortDescending);
			    new LeaderboardDialog(rows);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {				
				new ErrorDialog("Error generating leaderboard report by count.");
			}	
		}	
	}
	
	class TotalsActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			scoresDao = ScoresDAO.getScoresDao();
			Map<String, Integer> totals = scoresDao.getTotals();
			List<String> totalsList = new ArrayList<String>();
			
			for (Map.Entry<String, Integer> entry : totals.entrySet()) {
				totalsList.add(entry.getKey() + ":" + entry.getValue());
			}
			String[] arrayTotals = new String[totalsList.size()];
			arrayTotals = totalsList.toArray(arrayTotals);
			JList<String> list = new JList<String>(arrayTotals);
		      JPanel myPanel = new JPanel();
		      myPanel.setSize(500, 500);
		      myPanel.add(list);
		      
		      JOptionPane.showMessageDialog(null, myPanel);
		}	
	}
}
