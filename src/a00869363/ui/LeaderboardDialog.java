package a00869363.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import a00869363.dao.Database;
import a00869363.dao.LeaderboardDAO;
import a00869363.io.Leaderboard;
import javax.swing.JScrollPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Dimension;
import javax.swing.JTable;

public class LeaderboardDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(LeaderboardDialog.class);
	
	private static Database db;
	private static LeaderboardDAO dao;
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private int numberOfColumns = 4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			db = Database.getDatabaseInstance(); 
			dao = LeaderboardDAO.getLeaderboardDao();
			List<Leaderboard> rows = dao.getLeaderboardRows("byGame", false);
			LeaderboardDialog dialog = new LeaderboardDialog(rows);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LeaderboardDialog(List<Leaderboard> rows) {
		LOG.info("Creating leaderboard dialog.");
		db = Database.getDatabaseInstance(); 
		dao = LeaderboardDAO.getLeaderboardDao();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setBounds(100, 100, 400, 400);
		this.setLayout(new FlowLayout());
		
			String[] columnNames = {"Wins:Losses",
                    				"Game Name",
                    				"Gamertag",
                    				"Platform"};
			String[][] data = new String[rows.size()][numberOfColumns];
			for (int i = 0; i < rows.size(); i++) {
				data[i] = new String[numberOfColumns];
				for (int j = 0; j < numberOfColumns; j++) {
					data[i][0] = rows.get(i).getWins() + ":" + rows.get(i).getLosses();
					data[i][1] = rows.get(i).getGameName();
					data[i][2] = rows.get(i).getGamerTag();
					data[i][3] = rows.get(i).getPlatform();				
				}
			}
			table = new JTable(data, columnNames);	
			contentPanel.add(new JScrollPane(table));				
			this.add(contentPanel);
			this.pack();
	}

}
