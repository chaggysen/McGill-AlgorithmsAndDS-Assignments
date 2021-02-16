import java.util.*;
import java.lang.*;
import java.io.*;

public class Game {

	Board sudoku;
	private static Boolean solved = false;

	public class Cell {
		private int row = 0;
		private int column = 0;

		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
	}

	public class Region {
		private Cell[] matrix;
		private int num_cells;

		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}

		public Cell[] getCells() {
			return matrix;
		}

		public void setCell(int pos, Cell element) {
			matrix[pos] = element;
		}

	}

	public class Board {
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;

		public Board(int num_rows, int num_columns, int num_regions) {
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}

		public int[][] getValues() {
			return board_values;
		}

		public int getValue(int row, int column) {
			return board_values[row][column];
		}

		public Region getRegion(int index) {
			return board_regions[index];
		}

		public Region[] getRegions() {
			return board_regions;
		}

		public void setValue(int row, int column, int value) {
			board_values[row][column] = value;
		}

		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}

		public void setValues(int[][] values) {
			board_values = values;
		}

	}

	public int[][] solver() {
		// To Do => Please start coding your solution here
		// iterate the board and check for empty cells
		for (int row = 0; row < sudoku.num_rows; row++) {
			for (int col = 0; col < sudoku.num_columns; col++) {
				// found an empty cell
				if (sudoku.getValues()[row][col] == -1) {
					// get the region length
					int regionLength = Game.getRegionLength(row, col, sudoku);
					// System.out.println(regionLength);
					// iterate all possible values and check ifPossible
					for (int value = 1; value <= regionLength; value++) {
						// if possible, assign value and call solver() recursively
						if (isPossible(sudoku, value, row, col)) {
							System.out.println("Value " + value + " is possible at row " + row + " and col " + col);
							sudoku.setValue(row, col, value);
							solver();
							// previous call was not possible, backtrack
							if (!solved) {
								sudoku.setValue(row, col, -1);
							}
							// sudoku.setValue(row, col, -1);
						}
					}
					System.out.println("Run out of values, backtracking...");
					// not possible, return
					return sudoku.getValues();
				}
			}
		}
		System.out.println("Finished algorithm");
		solved = true;
		return sudoku.getValues();
	}

	private static int getRegionLength(int row, int col, Board currentBoard) {
		// perform binary Search?
		for (int i = 0; i < currentBoard.getRegions().length; i++) {
			for (int j = 0; j < currentBoard.getRegions()[i].getCells().length; j++) {
				if ((currentBoard.getRegions()[i].getCells()[j].getRow() == row)
						&& (currentBoard.getRegions()[i].getCells()[j].getColumn() == col)) {
					return currentBoard.getRegions()[i].num_cells;
				}
			}
		}
		return 0;
	}

	private static Boolean isPossible(Board currentBoard, int value, int row, int col) {
		Boolean isValid = true;
		// check for adjacent values
		int rows = currentBoard.num_rows;
		int cols = currentBoard.num_columns;
		// iterate each adjacent row and col
		for (int j = row - 1; j <= row + 1; j++) {
			for (int i = col - 1; i <= col + 1; i++) {
				// cehck if at the border of matrix and check if it's the row and col itself
				if (i >= 0 && j >= 0 && i < cols && j < rows && !(j == row && i == col)) {
					if (currentBoard.getValue(j, i) == value) {
						isValid = false;
						return isValid;
					}
				}
			}
		}
		// check for same region
		for (int i = 0; i < currentBoard.getRegions().length; i++) {
			Boolean seen = false;
			Boolean isCurrentRegion = false;
			for (int j = 0; j < currentBoard.getRegion(i).getCells().length; j++) {
				// if we got to the cell itself
				if (currentBoard.getRegion(i).getCells()[j].getRow() == row
						&& currentBoard.getRegion(i).getCells()[j].getColumn() == col) {
					// seen value before, not valid
					if (seen) {
						isValid = false;
						return isValid;
						// store that we are at the interested region
					} else {
						isCurrentRegion = true;
					}
					// we are at the interested reggion and we see the same value, not valid
				} else if (currentBoard.getValue(currentBoard.getRegion(i).getCells()[j].getRow(),
						currentBoard.getRegion(i).getCells()[j].getColumn()) == value && isCurrentRegion) {
					isValid = false;
					return isValid;
					// we see the value but we don't know if we are at the intrerested region yet,
					// set seen to true
				} else if (currentBoard.getValue(currentBoard.getRegion(i).getCells()[j].getRow(),
						currentBoard.getRegion(i).getCells()[j].getColumn()) == value) {
					seen = true;
				}
			}
		}
		return isValid;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		// Reading the board
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				} else {
					try {
						board[i][j] = Integer.valueOf(value);
					} catch (Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
		game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i = 0; i < regions; i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j = 0; j < num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1) - 1, Integer.valueOf(value2) - 1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i = 0; i < answer.length; i++) {
			for (int j = 0; j < answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j < answer[0].length - 1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

}
