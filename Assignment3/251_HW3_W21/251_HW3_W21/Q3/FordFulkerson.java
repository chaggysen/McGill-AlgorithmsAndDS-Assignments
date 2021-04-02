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

		/* YOUR CODE GOES HERE */

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
