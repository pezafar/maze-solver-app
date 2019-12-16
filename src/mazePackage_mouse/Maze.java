package mazePackage_mouse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import dijkstraPackage.GraphInterface;
import dijkstraPackage.VertexInterface;
import exceptionsPackage_mouse.MazeReadingException;

public class Maze implements GraphInterface {

	private int height ;
	private int length ;
	
	private int xStart = 0;
	private int yStart = 0;
	private int xEnd = 0;
	private int yEnd = 1;
	
	private String dataFolderName = "data_red";
	
	private VertexInterface[][] boxes; 
	
	
	public Maze(int lengthofMaze, int heightOfMaze) {
		this.height = heightOfMaze;
		this.length = lengthofMaze;
		boxes = new VertexInterface[height][length];
		for (int j = 0; j < this.height; j++) {
			for (int i = 0; i < this.length; i++) {
				
				boxes[j][i] = new WBox(this,i,j);
				if (i == xStart && j == yStart) {
					boxes[j][i] = new DBox(this,i,j);
				}
				if (i == xEnd && j == yEnd) {
					boxes[j][i] = new ABox(this,i,j);
				}
			}
		} 
		
		
	}
	
	

	public ArrayList<VertexInterface> getEveryVertice(){
		ArrayList<VertexInterface> list_Vertices = new ArrayList<>();
		for (VertexInterface[] t : boxes) {
					
					for (VertexInterface vert : t) {
						list_Vertices.add(vert);
					}
				}
		return (list_Vertices);
	}

	@Override
	public ArrayList<VertexInterface> getAllVertices() {
		
		ArrayList<VertexInterface> list_Vertices = new ArrayList<>();
		for (VertexInterface[] t : boxes) {
			
			for (VertexInterface vert : t) {
				if (vert.getLabel() != "W") {
					list_Vertices.add(vert);		
				}
				
			}
		}
		
		return(list_Vertices);
		
	}

	@Override
	public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) {
		
		MBox vertexBox = (MBox) vertex;
		ArrayList<VertexInterface> successeursListe = new ArrayList<>();
		int i = vertexBox.getXCoord();
		int j = vertexBox.getYCoord();
		
		
		if (j != 0 && boxes[j-1][i].getLabel() != "W" ) {
			successeursListe.add(boxes[j-1][i] );
		}
		if (i != 0 && boxes[j][i -1].getLabel() != "W" ) {
			successeursListe.add(boxes[j][i -1] );
		}
		if (i != length-1 && boxes[j][i+1].getLabel() != "W") {
			successeursListe.add(boxes[j][i+1]);
		}
		
		if (j != height-1 && boxes[j+1][i].getLabel() != "W") {
			successeursListe.add(boxes[j+1][i]);
		}
		
		return (successeursListe);
	}

	@Override
	public int getWeight(VertexInterface src, VertexInterface dst) {
		
		return 1;
	}

	//To open a maze from a text file
	public final void initFromTextFile(String fileName) throws MazeReadingException{
		
		
		System.out.println("Debut lecture schéma labyrinthe");
		try {
 
			BufferedReader fileReader = new BufferedReader(new FileReader(this.dataFolderName+"/" + fileName));
			
			String lineMaze;
			int rowCount = 0;
			try {
				lineMaze = fileReader.readLine();
				 
				//Maze dimensions are written on the firstLine
				String[] dimensionArray = lineMaze.split("_");
				if (dimensionArray.length == 2) {
					String widthString = dimensionArray[0];
					String heightString = dimensionArray[1];
					
					try {
						int newX = Integer.parseInt(widthString);
						int newY = Integer.parseInt(heightString);
						
						this.setHeight(newY);
						this.setLength(newX);
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					throw new MazeReadingException(fileName, 0, "probleme de dimensions du labyrinthe dans le fichier");
				}
				
				lineMaze = fileReader.readLine();
				while( lineMaze != null){
					
					if (rowCount +1 > this.height) {
						throw new MazeReadingException(fileName, rowCount, "Trop de lignes");
					}
					
					if(lineMaze.length() != this.length ) {
						throw new MazeReadingException(fileName, rowCount, "Erreur: la ligne " + rowCount+  "du fichier donnée possède trop de cases" );
					
					
					} 
					
					else {
						for (int i = 0; i < length; i++) {
							char boxCharacter = lineMaze.charAt(i);
							
							
							if (boxCharacter == 'W') {
								boxes[rowCount][i] = new WBox(this, i, rowCount);
							}
							else if(boxCharacter == 'E') {
								boxes[rowCount][i] = new EBox(this, i, rowCount);
							}
							else if(boxCharacter == 'D') {
								boxes[rowCount][i] = new DBox(this, i, rowCount);
								xStart = i;
								yStart = rowCount;
							}
							else if(boxCharacter == 'A') {
								boxes[rowCount][i] = new ABox(this, i, rowCount);
								xEnd = i;
								yEnd = rowCount;
							}
							else {
								throw new MazeReadingException(fileName, rowCount, "erreur : symbole non reconnu");
							}
						}
					}
					rowCount++;
					lineMaze = fileReader.readLine();
					
				}
			
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public final void saveToTextFile(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(this.dataFolderName + "/" + fileName);
			writer.println(this.length+"_"+ this.height);
			for (int i = 0; i < height; i++) {
				String temporaryLine = "";
				for (int j = 0; j < length; j++) {
					temporaryLine+=boxes[i][j].getLabel();
				}
				writer.println(temporaryLine);	
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	public int getXlength() {
		return length;
	}
	public int getYheight() {
		 return height;
	 }
	 
	 public void changeLabel(int xCoordonate, int yCoordonate, String label) {
		 if (xCoordonate >= 0 && xCoordonate < this.length && yCoordonate <= this.height && yCoordonate >= 0) {
			 if (label == "W") {boxes[yCoordonate][xCoordonate] = new WBox(this,xCoordonate, yCoordonate); }
			 else if (label == "E") {boxes[yCoordonate][xCoordonate] = new EBox(this,xCoordonate, yCoordonate); }
			 else if (label == "D") {
				 boxes[yCoordonate][xCoordonate] = new DBox(this,xCoordonate, yCoordonate);
				 xStart = xCoordonate;
				 yStart = yCoordonate;
				 
			 }
			 else {boxes[yCoordonate][xCoordonate] = new ABox(this,xCoordonate, yCoordonate);
			 xEnd = xCoordonate;
			 yEnd = yCoordonate;} 
		 }
	 }
	 
	 public DBox getStartBox() {
		 DBox boite =  (DBox) boxes[yStart][xStart] ;
		 return ( boite);
	 }
	 public ABox getEndBox() {
		 ABox boite =  (ABox) boxes[yEnd][xEnd];
		 return ( boite);
	 }





	//Sets the new height of the maze

	public void setHeight(int height2) {
		// TODO Auto-generated method stub
		
		VertexInterface[][] newBoxes = new VertexInterface[height2][this.length];
		
		int parcoursY = java.lang.Math.min(height, height2);
				
		
		
		if (  getStartBox().getYCoord() >= height2 || getEndBox().getYCoord() >= height2) {
			resetStartEndBoxes();	}
		
		for (int i = 0; i < parcoursY; i++) {
			for (int j = 0; j < this.length; j++) {
				newBoxes[i][j] = boxes[i][j];
			}
		}
		
		if (height2>height) {
			for (int i = height; i < height2; i++) {
				for (int j = 0; j < this.length; j++) {
					newBoxes[i][j] = new WBox(this, j, i);
				}
			}
		}
		boxes = newBoxes;
		height = height2;
	}
	
	
	//Sets the start and end bow at (1,0) and (0,1);
	private void resetStartEndBoxes() {

		changeLabel(xStart, yStart, "W");
		changeLabel(xEnd, yEnd, "W");
		changeLabel(0, 1, "D");
		changeLabel(1, 0, "A");
	
	}


	//Sets the new length of the maze
	public void setLength(int length2) {
		VertexInterface[][] newBoxes = new VertexInterface[this.height][length2];
		int parcoursX = java.lang.Math.min(length, length2);
		
		if (  getStartBox().getXCoord() >= length2|| getEndBox().getXCoord() >= length2) {
			resetStartEndBoxes();	}
		
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < parcoursX; j++) {
				newBoxes[i][j] = boxes[i][j];
			}
		}
		

		for (int i = 0; i < this.height; i++) {
			for (int j = length; j < length2; j++) {
					newBoxes[i][j] = new WBox(this, j, i);
			}
		}
		
		boxes = newBoxes;
		length = length2;
	}
	
	
	

}
