import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {

	static class Node {
		int row;
		int col;

		public Node(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}

		public boolean equals(Object object) {
			Node node = (Node) object;
			return node.row == row && node.col == col;
		}

	}

	public static int min_moves(int[][] board) {
		System.out.println("Starting bfs");
		int[][] distance = new int[board.length][board[0].length];
		boolean[][] visited = new boolean[board.length][board[0].length];
		Queue<Node> queue = new LinkedList<Node>();

		Node startNode = new Node(0, 0);
		distance[0][0] = 0;
		visited[0][0] = true;
		queue.add(startNode);

		while (!queue.isEmpty()) {
			// do BFS
			Node currentNode = queue.poll();
			System.out.println(currentNode.row + "," + currentNode.col);
			int curMoves = board[currentNode.row][currentNode.col];

			// append adjancent nodes to Queue
			if (currentNode.col + curMoves < board[0].length && !visited[currentNode.row][currentNode.col + curMoves]) {
				Node childNode = new Node(currentNode.row, currentNode.col + curMoves);
				queue.add(childNode);
				distance[childNode.row][childNode.col] = distance[currentNode.row][currentNode.col] + 1;
				visited[childNode.row][childNode.col] = true;
			}

			if (currentNode.col - curMoves >= 0 && !visited[currentNode.row][currentNode.col - curMoves]) {
				Node childNode = new Node(currentNode.row, currentNode.col - curMoves);
				queue.add(childNode);
				distance[childNode.row][childNode.col] = distance[currentNode.row][currentNode.col] + 1;
				visited[childNode.row][childNode.col] = true;
			}

			if (currentNode.row + curMoves < board.length && !visited[currentNode.row + curMoves][currentNode.col]) {
				Node childNode = new Node(currentNode.row + curMoves, currentNode.col);
				queue.add(childNode);
				distance[childNode.row][childNode.col] = distance[currentNode.row][currentNode.col] + 1;
				visited[childNode.row][childNode.col] = true;
			}

			if (currentNode.row - curMoves >= 0 && !visited[currentNode.row - curMoves][currentNode.col]) {
				Node childNode = new Node(currentNode.row - curMoves, currentNode.col);
				queue.add(childNode);
				distance[childNode.row][childNode.col] = distance[currentNode.row][currentNode.col] + 1;
				visited[childNode.row][childNode.col] = true;
			}
		}
		return distance[board.length - 1][board[0].length - 1] == 0 ? -1
				: distance[board.length - 1][board[0].length - 1]; // placeholder
	}

	public void test(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int[][] board = new int[num_rows][num_columns];
		for (int i = 0; i < num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j = 0; j < num_columns; j++) {
				board[i][j] = (int) (line[j] - '0');
			}

		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Starting");
		// Honors honors = new Honors();
		// honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java
		// Honors testfilename'

		// or you can test like this
		int[][] board = { { 2, 1, 2, 0 }, { 1, 2, 0, 3 }, { 3, 1, 1, 3 }, { 1, 1, 2, 0 }, { 1, 1, 1, 0 } }; // not
		// actual
		// example
		// int[][] board = { { 2, 2 }, { 2, 2 } };
		int answer = min_moves(board);
		System.out.println(answer);
	}

}
