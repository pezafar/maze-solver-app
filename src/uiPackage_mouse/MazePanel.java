package uiPackage_mouse;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import dijkstraPackage.VertexInterface;
import mazePackage_mouse.MBox;
import mazePackage_mouse.Maze;





/* Panel where the maze is drawn */
public class MazePanel extends JPanel implements MouseMotionListener, MouseListener, Observer{
	private int coteCarre = 50;
	private int xMaze = 0;
	private int yMaze = 0;
	
	private int xSelected = -1;
	private int ySelected = -1;
	
	//display settings
	private int espace_cases = 1;
	private int horizontalFreeSpace;
	private int leftMargin;
	private int verticalFreeSpace;
	private int topMargin;
	private int paddingMaze;
	
	//Colors of different boxes
	private final Color couleurVertEnd = new Color(0, 173, 57);
	private final Color couleurBleuStart = new Color(0, 71, 173);
	private final Color couleurBlancBloc = new Color(242, 242, 242);
	private final Color couleurRougeBloc = new Color(204, 0, 0);
	
	private Maze maze;
	private MazeEditor editor;
	
	private int editOption = 1;
	private boolean showPath = false;
	private ArrayList<VertexInterface> pathList;
	
	public MazePanel(Maze mazeToDrawinPanel, MazeEditor fenetreMere){
		
		fenetreMere.getModel().addObserver(this);
		editor = fenetreMere;
		maze = editor.getModel().getMaze();
		xMaze = maze.getXlength();
		yMaze = maze.getYheight();
		addMouseMotionListener(this);
		addMouseListener(this);
		this.setOpaque(false);
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		editOption = editor.getModel().getEditMode();
		maze = editor.getModel().getMaze();
		xMaze = maze.getXlength();
		yMaze = maze.getYheight();

		paddingMaze = (int) (getHeight() * 0.008);
		topMargin = (int) (getHeight()*0.1);
		leftMargin = (int) (getWidth()*0.1);
		espace_cases = Math.max(1,(int) (getHeight()*0.002)) ;
				
		coteCarre = Math.min( (getHeight()-topMargin)/(yMaze) - espace_cases, (getWidth()-leftMargin)/(xMaze)) -espace_cases;
		horizontalFreeSpace =  (getWidth() -  getXcumulated() );
		verticalFreeSpace = (getHeight()- getYcumulated());
		
		//drawing the background
		g.setColor(Color.BLACK);
		g.fillRect(horizontalFreeSpace/2 + leftMargin , verticalFreeSpace/2 + topMargin, getHorizontalPixelsMaze(),getVerticalPixelsMaze());
		
		ArrayList<VertexInterface> listeVertices = maze.getEveryVertice();

		for (VertexInterface vertex : listeVertices) {
			MBox box = (MBox) vertex;
			drawBox(box, g, false);
		}
		if (showPath) {drawPath(pathList, g);}
	}

	
	
	//This method draw an individual box depending on its type
	public void drawBox(MBox box, Graphics g, boolean pathBox ) {
		if (box.getLabel() == "D") { g.setColor(couleurBleuStart);}
		else if (box.getLabel() == "A") { g.setColor(couleurVertEnd);}
		else if (box.getLabel() == "W") { g.setColor(new Color(30,30,30));}
		else  { g.setColor(couleurBlancBloc);}
		
		int x = box.getXCoord();
		int y = box.getYCoord();
		
		if (xSelected == x && ySelected == y) {	
			g.setColor(Color.YELLOW);
		}
		if (pathBox) {
			g.setColor(couleurRougeBloc);
		}
		g.fillRect((coteCarre + espace_cases)* (x) + horizontalFreeSpace/2 + paddingMaze+ leftMargin,
				y * (coteCarre + espace_cases) + verticalFreeSpace/2 + paddingMaze+topMargin , coteCarre, coteCarre);
		
		
	}

	//Draws the path from the start box to the end box
	public void drawPath(ArrayList<VertexInterface> liste, Graphics g) {
		for (VertexInterface vertex : liste) {
			MBox box = (MBox) vertex;
			if (!isStartBox(box.getXCoord(), box.getYCoord()) && !isEndBox(box.getXCoord(), box.getYCoord())) {
				drawBox(box, g, true);
			}
		}
	}
	
	public int getXcumulated() {
		return (xMaze*(coteCarre + espace_cases) + paddingMaze*2 + leftMargin*2);
	}
	
	public int getYcumulated() {
		return (yMaze*(coteCarre + espace_cases) + paddingMaze*2 + topMargin*2  );
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		buildWall(e);
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		updateSelectedBox(e.getX(), e.getY());
		this.repaint();
	}

	//Updates the box where the mouse is (tobe colored on yellow)
	public void updateSelectedBox(int X, int Y) {
		
		if (X < xMaze*(coteCarre+espace_cases) + horizontalFreeSpace/2 + paddingMaze + leftMargin
			&& X > horizontalFreeSpace/2 + paddingMaze + leftMargin	
			&& Y< yMaze*(coteCarre+espace_cases) + verticalFreeSpace/2 + paddingMaze + topMargin
			&& Y > verticalFreeSpace/2 + paddingMaze+ topMargin) {
			xSelected =((int) X - horizontalFreeSpace/2 - paddingMaze - leftMargin)/(coteCarre + espace_cases);
			ySelected  = ((int) Y - verticalFreeSpace/2 - paddingMaze - topMargin)/(coteCarre + espace_cases);		
		}
		else {xSelected = -1; ySelected = -1; }
	
	}

	
	//Modifies the type of the box clicked EBox or WBox, left or right click)
	public void buildWall(MouseEvent e) {
		
		
			int xMouse = e.getX();
			int yMouse = e.getY();
			updateSelectedBox(xMouse, yMouse);
			String label;
			
		if (editOption == 1 && !isStartBox(xSelected, ySelected) && !isEndBox(xSelected, ySelected)) {
			showPath = false;
			if (SwingUtilities.isRightMouseButton(e)) { label = "W";}
			else {label = "E";}
			
			maze.changeLabel(xSelected, ySelected,label);
			//System.out.print("builingWall");
		}
			this.repaint();
	}
	
	//Set the start or end box (left or right click)
	public void setStartStop(MouseEvent e) {
		int xMouse = e.getX();
		int yMouse = e.getY();
		updateSelectedBox(xMouse, yMouse);
		String label;

		if (editOption == 2 && xSelected >= 0 && ySelected >= 0) {
			showPath = false;
		
			//Depending on the mouse button clicked currently
			
			if (SwingUtilities.isRightMouseButton(e)) { 
				if (maze.getStartBox().getXCoord()!= xSelected || maze.getStartBox().getYCoord() != ySelected) {
					label = "A";
					maze.changeLabel(maze.getEndBox().getXCoord(), maze.getEndBox().getYCoord(), "W");
					maze.changeLabel(xSelected, ySelected,label);
				}
				
			}
			else {
				
				if ((maze.getEndBox().getXCoord()!= xSelected || maze.getEndBox().getYCoord() != ySelected)) {
					label = "D";
					maze.changeLabel(maze.getStartBox().getXCoord(), maze.getStartBox().getYCoord(), "W");
					maze.changeLabel(xSelected, ySelected,label);
				}
			}				
		}
		this.repaint();

	}

	//check if the box selected by the mouse is the start box
	public boolean isStartBox(int x, int y) {
		if (maze.getStartBox().getXCoord() == x && maze.getStartBox().getYCoord() == y) {
			return (true);
		}
		else {
			return (false);
		}
	}
	
	public boolean isEndBox(int x, int y) {
		if (maze.getEndBox().getXCoord() == x && maze.getEndBox().getYCoord() == y ) {
			return (true);
		}
		else {
			return (false);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		setStartStop(e);		
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}


	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buildWall(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}
	
	public void setMode(int option) {
		editOption  = option;
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
		
	}

	public void displayPath(ArrayList<VertexInterface> liste) {
		showPath = true;
		pathList = liste;
	}
	
	public void hidePath() {
		showPath = false;
	}
	
	public int getHorizontalPixelsMaze() {
		return ((xMaze*(coteCarre + espace_cases)) + paddingMaze*2);
	}
	
	public int getVerticalPixelsMaze() {
		return ((yMaze*(coteCarre + espace_cases)) + paddingMaze*2);
	}
	
	
}

