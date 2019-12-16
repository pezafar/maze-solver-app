package dijkstraPackage;

import java.util.ArrayList;

public interface PreviousInterface {
	
	public void setFather(VertexInterface vertex, VertexInterface father) ;
	
	public VertexInterface getFather(VertexInterface vertex) ;
	
	public ArrayList<VertexInterface> getShortestPathTo(VertexInterface goal) ;

}
