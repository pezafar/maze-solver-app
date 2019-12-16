import mazePackage_mouse.Maze;
import uiPackage_mouse.MazeEditor;

public class Main { 

	public static void main(String[] args) {
		//Create an instance of a maze, and an instance of an editor
		Maze maze = new Maze(15, 15);
		MazeEditor monEditor = new MazeEditor(maze);
	}

}
 