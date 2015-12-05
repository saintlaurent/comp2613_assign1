package a00869363.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00869363.dao.Database;
import a00869363.dao.ScoresDAO;
import a00869363.data.Score;

public class ScoresDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static final Logger LOG = LogManager.getLogger(ScoresDialog.class);

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			ScoresDialog dialog = new ScoresDialog();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ScoresDialog() {
		LOG.info("Creating scores dialog.");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		setBounds(200, 200, 600, 500);
		
		
			List<Score> scores = this.getScores();			
			String[] output = new String[scores.size()];
			for (int i = 0; i < output.length; i++) {
				output[i] = String.format("%-12s%-24s%-24s", 
						scores.get(i).getPersonaId(),
						scores.get(i).getGameId(),
						scores.get(i).getWinLose()
						);
			}
			JList<String> list = new JList<String>(output);
			JScrollPane scrollPane = new JScrollPane(list);
			this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public List<Score> getScores(){
		Database database = Database.getDatabaseInstance();
		ScoresDAO dao = ScoresDAO.getScoresDao();
		List<Score> scores = dao.selectAll();
		return scores;
	}

}
