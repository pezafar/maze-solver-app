package dijkstraPackage;

/*
	Implements Dijkstra algorithm
 */

public class Dijkstra {

	public static PreviousInterface dijkstra(GraphInterface g, VertexInterface r) {

		return dijkstra( g , r , new ASet(r) , new Pi(g,r) , new Previous(r) );
		
	}
	
	private static PreviousInterface dijkstra(GraphInterface g, VertexInterface r, ASetInterface a, PiInterface pi, PreviousInterface previous) {
		
		int n = g.getAllVertices().size();
		VertexInterface pivot = r;
		
		for (int compteur = 1; compteur < n; compteur++) {
			
			//Update distance estimations of pivot's neighbours:
			for (VertexInterface successeur : g.getSuccessors(pivot)) {
				if (!(a.contains(successeur))) {
					if (pi.getValue(pivot) + g.getWeight(pivot, successeur) < pi.getValue(successeur)) {
						pi.setValue(successeur, pi.getValue(pivot) + g.getWeight(pivot, successeur));
						previous.setFather(successeur, pivot);
					}
				}
			}
			//Next pivot determination :
			int distanceMin = Integer.MAX_VALUE;
			VertexInterface nextPivot = r;
			for (VertexInterface vertex : g.getAllVertices()) {
				if (!(a.contains(vertex))) {
					if (pi.getValue(vertex) < distanceMin) {
						nextPivot = vertex;
						distanceMin = pi.getValue(nextPivot);
					}
				}
			}
			pivot = nextPivot;
			a.add(pivot);
		}
		
		return previous;
		
	}

}
