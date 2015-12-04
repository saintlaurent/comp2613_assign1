package a00869363.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import a00869363.dao.Database;
import a00869363.dao.PersonasDAO;
import a00869363.dao.PlayerDAO;
import a00869363.data.Player;

import javax.swing.JList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayersDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(PersonasDialog.class);
	
	private final DefaultListModel<String> listModel;
	
	List<Player> playersList;
	Database db;
	PersonasDAO dao;
	PlayerDAO playerDao;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			new PlayersDialog();
			
		} catch (Exception e) {
			LOG.error("Couldn't create players dialog.");
		}
	}
	
	public void getPlayers(){
		db = Database.getDatabaseInstance();
		playerDao = PlayerDAO.getPlayerDAO();
		playersList = playerDao.selectAll();
	}

	/**
	 * Create the dialog.
	 */
	public PlayersDialog() {
		LOG.info("Creating players dialog.");
		listModel = new DefaultListModel<String>();
		this.getPlayers();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		this.setBounds(100, 100, 450, 300);
					
		String[] playersFirstLast = new String[playersList.size()];
		for (int i = 0; i < playersFirstLast.length; i++) {
			playersFirstLast[i] = playersList.get(i).getfName() + " " + playersList.get(i).getlName();
			listModel.addElement(playersList.get(i).getfName() + " " + playersList.get(i).getlName());
		}
			
		JList<String> list = new JList<String>(listModel);			

		this.add(new JScrollPane(list), BorderLayout.CENTER);
				
	}

}
