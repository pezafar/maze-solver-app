package dijkstraPackage;

import java.util.ArrayList;
import java.util.Hashtable;

public class Previous implements PreviousInterface {
	
	private Hashtable<VertexInterface/*type des clés de la fonction de hashage*/, VertexInterface/*type des valeurs sur lesquelles les clés sont envoyées*/> hashtable;
	private VertexInterface root;
	
	public Previous(VertexInterface root) {
		
		hashtable = new Hashtable<VertexInterface, VertexInterface>();
		this.root = root;
	}

	
	@Override
	public void setFather(VertexInterface vertex, VertexInterface father) {
		
		hashtable.put(vertex, father);
	}

	@Override
	public VertexInterface getFather(VertexInterface vertex) {
		
		return hashtable.get(vertex);
	}

	@Override
	public ArrayList<VertexInterface> getShortestPathTo(VertexInterface goal) { //la liste de sommets renvoyés est dans l'ordre : le sommet racine, son fils, etc jusqu'au sommet goal
		
		//le cas où goal et root sont confondus n'arrive jamais : on s'en assure dans l'implémentation de dijkstra
		ArrayList<VertexInterface> path = new ArrayList<VertexInterface>();
		path.add(goal);
		VertexInterface father = getFather(goal);
		if (father == null) { //Si l'arrivée n'a pas de père alors il n'existe pas de solution, et l'on renvoie null au lieu d'un chemin.
			return null;
		}
		
		while (father != root) { // ne termine pas si goal n'est pas atteignable depuis root cependant dijkstra évite ce cas grâce au if précédent
			path.add(father);
			father = getFather(father);
		}
		path.add(father); //father = root ici 
		ArrayList<VertexInterface> orderedPath = new ArrayList<VertexInterface>();
		for (int i = path.size()-1; i >= 0; i--) {
			orderedPath.add(path.get(i));
		}
		return orderedPath; // orderedPath est simplement path à l'envers pour avoir la liste des sommets dans le bon sens
	}
	
}
