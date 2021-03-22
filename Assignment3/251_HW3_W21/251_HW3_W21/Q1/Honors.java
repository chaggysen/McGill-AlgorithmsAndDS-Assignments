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
		int[][] distance = new int[board.length][board[0].length];
		Boolean[][] visited = new Boolean[board.length][board[0].length];
		Queue<Node> queue = new LinkedList<>();

		Node startNode = new Node(0, 0);
		distance[0][0] = 0;
		visited[0][0] = true;
		queue.add(startNode);

		while (!queue.isEmpty()) {
			// do BFS
		}
		return 0; // placeholder
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
		Honors honors = new Honors();
		honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'

		// or you can test like this
		// int [][]board = {1,2,3}; // not actual example
		// int answer = min_moves(board);
		// System.out.println(answer);
	}

}
