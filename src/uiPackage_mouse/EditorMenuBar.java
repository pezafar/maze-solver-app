package uiPackage_mouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


//The menu bar of the editor


public class EditorMenuBar extends JMenuBar {
	
	private final JMenu menuFile = new JMenu("File");
	private final JMenu menuHelp = new JMenu("?");
	private final JMenuItem itemOpen = new JMenuItem("Open");
	private final JMenuItem itemSave = new JMenuItem("Save");
	private final JMenuItem itemHelp = new JMenuItem("Help");
	
	private MazeEditorModel model;
	
	public EditorMenuBar(MazeEditorModel model) {
		this.model = model;
		initMenu();
		
		//Creation of ActionListeners to add to menu items
		ActionListener listenerOpenFile = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {  
				new MazeOpener(model.getEditor());
			}
		};
		
		ActionListener listenerSaveFile = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MazeSaver(model.getEditor());}
		};
		
		ActionListener listenerHelp = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String helpMessage = "HOW TO USE THE MAZE EDITOR\n\n"
									+ "You can resize the window at your convenience\n"
									+ "white : empty boxes\n"
									+ "black : walls\n\n"
									+ "DIMENSIONS :\n"
									+ "		* set height and length with the spinners at the bottom\n"
									+ "\n"
									+ "SAVE/OPEN :\n"
									+ "* You can save and open mazes from the Open and Save items in the File Menu\n"
									+ "\n"
									+ "WALL EDIT BUTTON :\n"
									+ "		* right click/drag : creates empty space\n"
									+ " 	* left click/drag : creates some walls\n"
									+ "\n"
									+ "START/END BUTTON :\n"
									+ "		* right click : set start box (blue)\n"
									+ "		* left click : set end box (red)\n"
									+ "\n"
									+ "SOLVE BUTTON :\n"
									+ "		* to solve the Maze\n"
									+ "\n"
									+ "You can reset the Maze at any time with the RESET button\n\n\n";
				JOptionPane.showMessageDialog(getSelf(), helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		itemOpen.addActionListener(listenerOpenFile);
		itemSave.addActionListener(listenerSaveFile);
		itemHelp.addActionListener(listenerHelp);
	}
	
	
	private void initMenu() {
		this.menuFile.add(itemOpen);
		this.menuFile.add(itemSave);
		itemSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		itemOpen.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		
		this.menuHelp.add(itemHelp);
	
		this.add(menuFile);
		this.add(menuHelp);
		
	}

	private EditorMenuBar getSelf() {
		return this;
	}
	

}


