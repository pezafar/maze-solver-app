package uiPackage_mouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MazeSaver extends JFrame {

	private JTextField nameField = new JTextField("my Maze");
	private final JButton cancelButton = new JButton("CANCEL");
	private final JButton okButton = new JButton("OK");
	
	private JPanel mainPanel = new JPanel();
	
	public MazeSaver(MazeEditor editor) { 
		
		
		cancelButton.setBackground(Color.WHITE);
		okButton.setBackground(Color.WHITE);
		

		
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
		panelButtons.add(cancelButton);
		panelButtons.add(okButton);
		
		JPanel panelText = new JPanel();
		panelText.setLayout(new GridLayout(1, 2));
		panelText.add(new JLabel("nom : "));
		panelText.add(nameField);
		panelText.setSize(new Dimension(200, 100));
		//panelText.setBackground(Color.RED);
		
		int borderSize = 50;
		mainPanel.setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));;
		//mainPanel.add(Box.createVerticalStrut(50));
		mainPanel.add(panelText);
		mainPanel.add(Box.createVerticalStrut(30));
		mainPanel.add(panelButtons);
		

		
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = nameField.getText() + ".txt";
				editor.getModel().saveMaze(fileName);
				
				getSelf().setVisible(false);
				getSelf().dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getSelf().dispose();
			}
		});
		
		
		
		
		
		
		this.setContentPane(mainPanel);
		//this.setSize(600,400);
		this.pack();
		this.setVisible(true);
		
	}
	
	
	public MazeSaver getSelf() {
		return (this);
	}
	
	
	
}
