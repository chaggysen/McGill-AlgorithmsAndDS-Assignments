import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		boolean[] visited = new boolean[graph.getNbNodes()];
		path = dfsRecursive(source, destination, graph, visited);
		/* YOUR CODE GOES HERE */

		return path;
	}

	private static ArrayList<Integer> dfsRecursive(Integer source, Integer destination, WGraph graph,
			boolean[] visited) {

		ArrayList<Integer> path = new ArrayList<Integer>();
		ArrayList<Integer> subPath = new ArrayList<Integer>();
		// initialize dfs
		path.add(source);
		visited[source] = true;

		if (source == destination) {
			return path;
		}

		for (int i = 0; i < graph.getEdges().size(); i++) {
			if (graph.getEdges().get(i).nodes[0] == source && graph.getEdges().get(i).weight != 0) {
				if (!visited[graph.getEdges().get(i).nodes[1]]) {
					subPath = dfsRecursive(graph.getEdges().get(i).nodes[1], destination, graph, visited);
					if (!subPath.isEmpty() && subPath.get(subPath.size() - 1) == destination) {
						path.addAll(subPath);
					}
				}
			}

			if (path.get(path.size() - 1) == destination) {
				return path;
			}
		}

		return new ArrayList<Integer>();
	}

	public static String fordfulkerson(WGraph graph) {
		String answer = "";
		int maxFlow = 0;

		// initialize variables
		int source = graph.getSource();
		int destination = graph.getDestination();

		// initialize residual graph
		WGraph residualGraph = new WGraph(graph);

		// add backward edges to residual graph
		for (Edge edge : graph.getEdges()) {
			Edge backwardEdge = new Edge(edge.nodes[1], edge.nodes[0], 0);
			if (graph.getEdge(edge.nodes[1], edge.nodes[0]) == null) {
				residualGraph.addEdge(backwardEdge);
			}
		}

		// calculate path
		ArrayList<Integer> currentPath = pathDFS(source, destination, residualGraph);
		if (currentPath.size() == 0) {
			maxFlow = -1;
		}

		// Update flow while there is a path from source to destination in residualGraph
		while (currentPath.size() > 0) {
			int bottleNeck = Integer.MAX_VALUE;

			// find bottleneck
			for (int i = 0; i < currentPath.size() - 1; i++) {
				int currentWeight = residualGraph.getEdge(currentPath.get(i), currentPath.get(i + 1)).weight;
				bottleNeck = Math.min(currentWeight, bottleNeck);

			}

			for (int i = 0; i < currentPath.size() - 1; i++) {
				// substract bottleNeck from forward edges and add to backward edges
				Edge backwardEdge = residualGraph.getEdge(currentPath.get(i + 1), currentPath.get(i));
				Edge forwardEdge = residualGraph.getEdge(currentPath.get(i), currentPath.get(i + 1));

				forwardEdge.weight -= bottleNeck;
				backwardEdge.weight += bottleNeck;

				if (forwardEdge.weight == 0) {
					residualGraph.getEdges().remove(forwardEdge);
				}
			}

			currentPath = pathDFS(source, destination, residualGraph);
			maxFlow += bottleNeck;

			// update residual graph
		}

		for (int i = 0; i < graph.getEdges().size() - 1; i++) {
			Edge curEdge = residualGraph.getEdges().get(i);
			int weight = residualGraph.getEdge(curEdge.nodes[0], curEdge.nodes[1]).weight;
			int newWeight = graph.getEdges().get(i).weight - weight;
			graph.setEdge(curEdge.nodes[0], curEdge.nodes[1], newWeight);
		}

		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}

	public static void main(String[] args) {
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
		System.out.println(fordfulkerson(g));
	}
}
