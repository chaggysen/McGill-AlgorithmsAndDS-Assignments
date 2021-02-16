import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello");
        int[][] board = new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        System.out.println(isPossible(board, 1, 2, 3));

    }

    // method isPossible
    private static ArrayList<Integer> isPossible(int[][] currentBoard, int value, int row, int col) {
        // check for adjacent values
        ArrayList<Integer> list = new ArrayList<Integer>();
        int rows = currentBoard.length;
        int cols = currentBoard[0].length;
        // iterate each adjacent row and col
        for (int j = row - 1; j <= row + 1; j++) {
            for (int i = col - 1; i <= col + 1; i++) {
                // cehck if at the border of matrix and check if it's the row and col itself
                if (i >= 0 && j >= 0 && i < cols && j < rows && !(j == row && i == col)) {
                    list.add(currentBoard[j][i]);
                }
            }
        }
        // check for same region
        return list;
    }
}
