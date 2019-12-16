package dijkstraPackage;

import java.util.HashSet;

public class ASet implements ASetInterface {
	
	private HashSet<VertexInterface> hashset;
	private VertexInterface root;
	
	public ASet(VertexInterface root) {
		hashset = new HashSet<VertexInterface>();
		this.root = root;
		add(root);
	}

	@Override
	public void add(VertexInterface vertex) {
		hashset.add(vertex);
	}

	@Override
	public boolean contains(VertexInterface vertex) {
		return hashset.contains(vertex);
	}
	
	public String toString() {
		return (hashset.toString() );
	}


}
