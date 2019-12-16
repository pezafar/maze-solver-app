package uiPackage_mouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import javax.swing.JOptionPane;

import dijkstraPackage.Dijkstra;
import dijkstraPackage.PreviousInterface;
import dijkstraPackage.VertexInterface;
import exceptionsPackage_mouse.MazeReadingException;
import mazePackage_mouse.Maze;



/* Model of the editor */
public class MazeEditorModel extends Observable {
	
	private int editMode = 1;
	private Maze maze;
	private MazeEditor editor;

	
	public MazeEditorModel(Maze mazeToDraw, MazeEditor mazeEditor) { 
		maze = mazeToDraw;
		editor = mazeEditor;
	}
	
	public void update() {
		notifyForUpdate();
	}
	
	public void notifyForUpdate(){
	}

	public void setEditMode(int mode) {
		editMode = mode;
		setChanged();
		notifyObservers();
	}

	public int getEditMode() {
		return editMode;
	}

	public Maze getMaze() {
		return maze;
	}

	public void resetMaze() {
		System.out.println(maze.getXlength() + " " + maze.getYheight()); 
		maze = new Maze(maze.getXlength(), maze.getYheight());
		editor.getDisplayPanel().hidePath();
		setChanged();
		notifyObservers();
		
	}
	
	public void solveMaze() {
		PreviousInterface lePrev = Dijkstra.dijkstra(maze, maze.getStartBox());
		ArrayList<VertexInterface> liste = lePrev.getShortestPathTo(maze.getEndBox());
		
		//If there is no path
		if (liste == null) {
			JOptionPane.showMessageDialog(editor, "This configuration has no solution", "Sorry", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			//otherwise we ask the panel to show the solution
			Collections.reverse(liste);
			editor.getDisplayPanel().displayPath(liste);
			setChanged();
			notifyObservers();
		}
	}

	public void changeMazeHeight( int height) {
		maze.setHeight(height);
		setChanged();
		notifyObservers();
		
	}

	public void changeMazeLength(int length) {
		maze.setLength(length);
		setChanged();
		notifyObservers();
	}

	public void openMaze(String mazeName) {
		try {
			maze.initFromTextFile(mazeName);
			editor.getButtonPanel().setSpinnerHeight(maze.getYheight());
			editor.getButtonPanel().setSpinnerLength(maze.getXlength());
		} catch (MazeReadingException e) {
			JOptionPane.showMessageDialog(editor, "Erreur dans le fichier", "Erreur", JOptionPane.OK_OPTION);
			resetMaze();
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();
		editor.getDisplayPanel().hidePath();
	}

	public void saveMaze(String fileName) {
		maze.saveToTextFile(fileName);
		setChanged();
		notifyObservers();
	
	}
	
	public MazeEditor getEditor() {
		return editor;
	}
	
	
	
}
