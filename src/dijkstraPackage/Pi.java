package dijkstraPackage;

import java.util.Hashtable;

public class Pi implements PiInterface {
	
	private Hashtable<VertexInterface, Integer> hashtable;
	private GraphInterface graph;
	private VertexInterface root;
	
	public Pi(GraphInterface graph, VertexInterface root) {
		hashtable = new Hashtable<VertexInterface, Integer>();
		this.graph = graph;
		this.root = root;
		for (VertexInterface vertex : graph.getAllVertices()) {
			
			hashtable.put(vertex, Integer.MAX_VALUE);
		}
		hashtable.put(root,0);
	}

	@Override
	public void setValue(VertexInterface vertex, int value) {
		
		hashtable.put(vertex, value);
	}

	@Override
	public int getValue(VertexInterface vertex) {
		
		return hashtable.get(vertex);
	}
	
}
