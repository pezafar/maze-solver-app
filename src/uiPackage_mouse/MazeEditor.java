package uiPackage_mouse;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import mazePackage_mouse.Maze;


/* This class is the windows of the editor (view) */

public class MazeEditor extends JFrame {
	
	private MazeEditorModel  model ;
	private final JMenuBar barreMenu;	
	private final MazePanel panneauMaze;
	private final EditorButtonsPanel buttonsPanel;
	
	public MazeEditor(Maze mazeToDraw) {		
		super("fenetre cours");
		
		model = new MazeEditorModel(mazeToDraw, this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 400));
		
		barreMenu = new EditorMenuBar(model);
		panneauMaze = new MazePanel(model.getMaze(), this);
		buttonsPanel = new EditorButtonsPanel(this);
		
		//Window organization
		JPanel PanelTotal = new JPanel();
		PanelTotal.setLayout(new BorderLayout());
		PanelTotal.add(panneauMaze, BorderLayout.CENTER);
		PanelTotal.add(buttonsPanel, BorderLayout.SOUTH );
		PanelTotal.setBackground(Color.WHITE);
		
		this.setJMenuBar(barreMenu);
		this.setContentPane(PanelTotal);
		
		this.setSize(1200,700);
		setVisible(true);
		
		
	}
	
	
	public MazeEditorModel getModel() {
		return model;
	}


	public MazePanel getDisplayPanel() {
		return (panneauMaze);
	}


	public MazeEditor getSelf() {
		return (this);
	}


	public EditorButtonsPanel getButtonPanel() {
		return(buttonsPanel);
	}
	
}
