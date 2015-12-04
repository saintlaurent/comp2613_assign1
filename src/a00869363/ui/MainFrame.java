package a00869363.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import a00869363.dao.Database;
import a00869363.dao.LeaderboardDAO;
import a00869363.dao.PersonasDAO;
import a00869363.dao.ScoresDAO;
import a00869363.io.GamesFormat;
import a00869363.io.Leaderboard;
import a00869363.io.LeaderboardReportWriter;
import a00869363.io.PersonaFormat;
import a00869363.io.PlayerFormat;
import a00869363.io.ScoreFormat;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Sorting
	public static boolean sortDescending = false;
	public static String filterGamertag = "";
	
	//Logger
	private static final Logger LOG = LogManager.getLogger(MainFrame.class);

	//Components
	private JPanel contentPane;
	private JMenuItem byGameMenuItem;
	private JMenuItem byCountMenuItem;
	private JMenuItem gamertagMenuItem;
	private JMenuItem totalMenuItem;
	
	@SuppressWarnings("unused")
	private static LeaderboardReportWriter lrw; 
	PlayerFormat pf;
	PersonaFormat personaFormat;
	GamesFormat gamesFormat;
	ScoreFormat scoreFormat;
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		this.loadData();
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 473);
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_1);
		menuBar.add(fileMenu);
		
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
		quitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		fileMenu.add(quitMenuItem);
		
		JMenu listsMenu = new JMenu("Lists");
		listsMenu.setMnemonic(KeyEvent.VK_2);
		menuBar.add(listsMenu);
		
		JMenuItem playersMenuItem = new JMenuItem("Players", KeyEvent.VK_P);
		playersMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new PlayersDialog();
			}
		});
		listsMenu.add(playersMenuItem);
		
		JMenuItem personasMenuItem = new JMenuItem("Personas", KeyEvent.VK_E);
		personasMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PersonasDialog();
			}
		});
		listsMenu.add(personasMenuItem);
		
		JMenuItem scoresMenuItem = new JMenuItem("Scores", KeyEvent.VK_S);
		scoresMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ScoresDialog();
			}
		});
		listsMenu.add(scoresMenuItem);
		
		JMenu reportsMenu = new JMenu("Reports");
		reportsMenu.setMnemonic(KeyEvent.VK_3);
		menuBar.add(reportsMenu);
		
		totalMenuItem = new JMenuItem("Totals", KeyEvent.VK_T);
		reportsMenu.add(totalMenuItem);
		
		byGameMenuItem = new JMenuItem("By Game", KeyEvent.VK_G);
		reportsMenu.add(byGameMenuItem);
		
		byCountMenuItem = new JMenuItem("By Count", KeyEvent.VK_C);
		reportsMenu.add(byCountMenuItem);
		
		gamertagMenuItem = new JMenuItem("Gamertag", KeyEvent.VK_X);
		reportsMenu.add(gamertagMenuItem);
		
		JCheckBoxMenuItem descMenuItem = new JCheckBoxMenuItem("Descending");
		descMenuItem.addItemListener(new ItemListener() {		     
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(descMenuItem.isSelected()){
					sortDescending = true;
				} else {
					sortDescending = false;
				}
			}
		});
		
		reportsMenu.add(descMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_4);
		menuBar.add(helpMenu);
		
		JMenuItem aboutMenuItem = new JMenuItem("About", KeyEvent.VK_U);
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Assignment 2 \n by Catherine Li A00869363");
			}
		});
		helpMenu.add(aboutMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[]", "[]"));
	}
	
	public void loadData(){
		pf = new PlayerFormat(); 
		personaFormat = new PersonaFormat(); 
		gamesFormat = new GamesFormat();
		scoreFormat = new ScoreFormat();
		lrw = new LeaderboardReportWriter();
	}
	
	
	void addByGameActionListener(ActionListener listener){
		byGameMenuItem.addActionListener(listener);
	}
	
	void addGamertagActionListener(ActionListener listener){
		gamertagMenuItem.addActionListener(listener);
	}
	
	void addByCountActionListener(ActionListener listener){
		byCountMenuItem.addActionListener(listener);
	}
	
	void addTotalsActionListener(ActionListener listener){
		totalMenuItem.addActionListener(listener);
	}
	

}
