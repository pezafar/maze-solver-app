package mazePackage_mouse;

import dijkstraPackage.VertexInterface;


/*
	Abstract class MBox
	General box : Departure (DBox), Arrival (ABox), Walls (Wbox) and clear path (Ebox) inherits from this class
 */
public abstract class MBox implements VertexInterface{
	
	private int x;
	private int y;
	
	
	public MBox(Maze maze, int xCoord, int yCoord) {
		x = xCoord;
		y = yCoord;
	}
	
	public abstract String getLabel();
	
	public int getXCoord() {
		return (x);
	}
	
	
	public int getYCoord() {
		return (y);
	}
	
	public void printCoord() {
		System.out.println("Case : (" + this.getXCoord() + "," + this.getYCoord() + ")");
	}

	public String toString() {
		return("(" + this.getXCoord() + "," + this.getYCoord() + ")");
		
	}
	
	public boolean hasSameCoordinates(MBox boxCompared) {
		if (this.getXCoord() == boxCompared.getXCoord() && this.getYCoord() == boxCompared.getYCoord()) {
			return(true);
		}
		else {
			return(false);
		}
	}
	
	

}
