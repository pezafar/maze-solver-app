package uiPackage_mouse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



/* Allows to open a maze from a file*/
public class MazeOpener extends JFrame{

	
	private JList<String> listDisplay = new JList<String>();
	private String[] mazeNameList;
	private final  JPanel panelButtons = new JPanel();
	private final JButton button1 = new  JButton("CANCEL");
	private final JButton button3 = new JButton("DELETE");
	private final JButton button2 = new  JButton("LOAD");
	private final JPanel panelList = new JPanel();
	private final JPanel panelMain = new JPanel();
	private MazeEditor editor;
	
	public MazeOpener(MazeEditor editor) {
		this.editor = editor;
		this.setVisible(true);
		this.makeList(); 
		this.organiseWindow();
		this.addListeners();
		
	}
	

	private void addListeners() {
		button2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
					
						if (!listDisplay.isSelectionEmpty()) {
							editor.getModel().openMaze( mazeNameList[listDisplay.getSelectedIndex()] );
							getSelf().dispose();
						}
						else {
							JOptionPane.showMessageDialog(getSelf(), "Veuillez sélectionner un fichier", "Erreur", JOptionPane.DEFAULT_OPTION);
						}
					}
				});
				
				button1.addActionListener(new ActionListener() {
					
							@Override
							public void actionPerformed(ActionEvent e) {
								getSelf().dispose();
							}
						});
				
				button3.addActionListener(new ActionListener() {
					
					@Override 
					public void actionPerformed(ActionEvent e) {	
						try {
							int deletedIndex = listDisplay.getSelectedIndex();
							File file = new File("data_red/" + mazeNameList[listDisplay.getSelectedIndex()] );
							if (file.delete()) {
								getSelf().makeList();
							}
							else {
								JOptionPane.showMessageDialog(getSelf(), "Erreur suppression fichier", "Erreur", JOptionPane.DEFAULT_OPTION);
								
							}
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				});
		
	}


	MazeOpener getSelf() {
		return (this);
	}
	
	void makeList() {
		File dataFolder = new File("data_red");		
		File[] mazeFileList = dataFolder.listFiles();
		mazeNameList = new String[mazeFileList.length];
		
		for (int i = 0; i < mazeFileList.length; i++) {
			if (mazeFileList[i].isFile()) {
				mazeNameList[i] = mazeFileList[i].getName();
			} 
		}
		listDisplay.setListData(mazeNameList);
	}
	
	
	//add elements to the window
	void organiseWindow() {
		listDisplay.setListData(mazeNameList);
		listDisplay.setPreferredSize(new Dimension(400, 400));
		
		panelList.setLayout(new BorderLayout());
		panelList.add(listDisplay);
		panelList.setBackground(Color.BLUE);
		
		button1.setBackground(Color.WHITE);
		button2.setBackground(Color.WHITE);
		button3.setBackground(Color.WHITE);
		
		panelButtons.add(button1);
		panelButtons.add(Box.createHorizontalStrut(20));
		panelButtons.add(button3);
		panelButtons.add(Box.createHorizontalStrut(20));
		panelButtons.add(button2);
		button1.setPreferredSize(new Dimension(120, 40));
		button2.setPreferredSize(new Dimension(120, 40));
		button3.setPreferredSize(new Dimension(120, 40));
	
	
		this.setContentPane(panelMain);
		
		int borderSize = 20;
		panelMain.setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
		panelMain.setLayout((new BoxLayout(panelMain, BoxLayout.PAGE_AXIS)));
		panelMain.add(panelList);
		panelMain.add(Box.createVerticalStrut(30));
		panelMain.add(panelButtons);

		
		
		this.setTitle("Ouvrir une sauvegarde de labyrinthe");
		this.pack();
		this.setResizable(false);
		
		
	}
	
	
}
