package a00869363.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import a00869363.dao.Database;
import a00869363.dao.PersonasDAO;
import a00869363.dao.PlayerDAO;
import a00869363.data.Persona;
import a00869363.data.Player;

import javax.swing.JList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonasDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(PersonasDialog.class);
	List<Persona> personas;
	Database db;
	PersonasDAO dao;
	PlayerDAO playerDao;

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			PersonasDialog dialog = new PersonasDialog();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getGamertags(){
		db = Database.getDatabaseInstance();
		dao = PersonasDAO.getPersonasDAO();
		playerDao = PlayerDAO.getPlayerDAO();
		personas = dao.selectAll();
	}

	/**
	 * Create the dialog.
	 */
	public PersonasDialog() {
		LOG.info("Creating personas dialog.");
		this.getGamertags();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		setBounds(100, 100, 450, 300);
			String[] personasRow = new String[personas.size()];
		    //String[] test = new String[]{"one", "two", "three"};
		    for (int i = 0; i < personasRow.length; i++) {
		    	personasRow[i] = personas.get(i).getGamerTag();
			}
			JList<String> list = new JList<String>(personasRow);
			
			list.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        @SuppressWarnings("unchecked")
					JList<String> list = (JList<String>)evt.getSource();
			        if (evt.getClickCount() == 2) {
			            editPersona(list.getSelectedValue());				     
			        } 
			    }
			});
			this.add(new JScrollPane(list), BorderLayout.CENTER);
	}
	
	/*
	 * Edit persona information
	 */
	public void editPersona(String gamertag){
		  Player player = playerDao.getPlayer(gamertag);
		  
		  JTextField fName = new JTextField(15);
	      JTextField lName = new JTextField(15);
	      JTextField email = new JTextField(15);
	      JTextField persona = new JTextField(15);
	      
	      //Set textfield information
	      fName.setText(player.getfName()); 
	      lName.setText(player.getlName());
	      email.setText(player.getEmail());
	      persona.setText(gamertag);
	      
	      JPanel myPanel = new JPanel();
	      myPanel.setLayout(new BorderLayout());
	      myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
	      myPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	      //First name
	      myPanel.add(new JLabel("First Name:"));
	      myPanel.add(fName);
	     
	      
	      //Last name
	      myPanel.add(new JLabel("Last Name:"));
	      myPanel.add(lName);
	      
	      
	      //Email
	      myPanel.add(new JLabel("Email:"));
	      myPanel.add(email);
	      
	      
	      //Gamertag or persona
	      myPanel.add(new JLabel("Gamertag:"));
	      myPanel.add(persona);	      

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Edit persona", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  //Update persona and player information
	    	  player.setfName(fName.getText());
	    	  player.setlName(lName.getText());
	    	  player.setEmail(email.getText());
	    	  System.out.println(player.toString());
	    	  playerDao.update(player);
	    	  try {
				dao.update(gamertag, persona.getText());
			} catch (SQLException e) {
				LOG.error("Cannot update persona information.");
			}
	     }
	}

}
