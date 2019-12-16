package uiPackage_mouse;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/* Panel of buttons in the maze editor */

public class EditorButtonsPanel extends JPanel {
	
	private final JButton button1 = new JButton("EDIT WALLS");
	private final JButton button2 = new JButton("START/END");
	private final JButton button3 = new JButton("SOLVE");
	private final JButton button4 = new JButton("RESET");
	private final SpinnerNumberModel heightSpinnerModel;
	private final SpinnerNumberModel lengthSpinnerModel;
	private final JSpinner heightSpinner;
	private final JSpinner lengthSpinner;
	private MazeEditor editor;
	public EditorButtonsPanel( MazeEditor editor) {
		this.editor = editor;
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		heightSpinnerModel = new SpinnerNumberModel(editor.getModel().getMaze().getYheight(), 2, 1000, 1);
		heightSpinner = new JSpinner(heightSpinnerModel);
		lengthSpinnerModel = new SpinnerNumberModel(editor.getModel().getMaze().getXlength(), 2, 1000, 1);
		lengthSpinner =new JSpinner(lengthSpinnerModel);
		
		//set up of the elements on the panel
		addListeners();
		this.stylizeButton(button1);
		this.stylizeButton(button2);
		this.stylizeButton(button3);
		this.stylizeButton(button4);
		
		LayoutManager layout = this.getLayout();

		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.X_AXIS));
		panelBoutons.add(button1);
		panelBoutons.add(button2);
		panelBoutons.add(button3);
		panelBoutons.add(button4);

		JPanel panelSpinners1 = new JPanel();
		
		JLabel label_height = new JLabel("Nombre de lignes : " );
		JLabel label_width = new JLabel("Nombre de colonnes: " );
		
		heightSpinner.setMaximumSize(new Dimension(500, 100));
		lengthSpinner.setMaximumSize(new Dimension(50, 100));
		
		panelSpinners1.add(label_height);
		panelSpinners1.add(heightSpinner);
		panelSpinners1.add(label_width);
		panelSpinners1.add(lengthSpinner);		
		
		panelSpinners1.setOpaque(false);
		panelBoutons.setOpaque(false);
		this.setOpaque(false);
		label_height.setOpaque(false);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(panelBoutons);
		this.add(Box.createVerticalStrut(10));
		this.add(panelSpinners1);
		this.setSize(400,100);

	}
	
	//Sets the listeners on the different elements of the panel
	private void addListeners() {
		
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("changment");
				
				MazeEditorModel model = editor.getModel();
				model.setEditMode(1);
			}
		});
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MazeEditorModel model = editor.getModel();
				model.setEditMode(2);
				
			}
		});
		button3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						MazeEditorModel model = editor.getModel();
						model.solveMaze();
						
					}
				});
		button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.getModel().resetMaze();
				
			}
		});
		
		heightSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				editor.getModel().changeMazeHeight((Integer)heightSpinner.getValue());
			}
		});
		
		
		lengthSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				editor.getModel().changeMazeLength((Integer)lengthSpinner.getValue());
			}
		});
	}
	
	private void stylizeButton(JButton button) {
		button.setContentAreaFilled(false);
	}
	
	
	public void setSpinnerHeight(int value){
		heightSpinnerModel.setValue(value);
	}
	
	
	public void setSpinnerLength(int value){
		lengthSpinnerModel.setValue(value);
	}
}
