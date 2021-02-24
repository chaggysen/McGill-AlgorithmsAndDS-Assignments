import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;
	private static Hashtable<Integer, Integer> memo = new Hashtable<>();

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i = 0; i < chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int answer = lost_marks(penalization);
		System.out.println(answer);
	}

	public static int lost_marks(int[] penalization) {
		// To Do => Please start coding your solution here
		int start = 0;
		int end = penalization.length - 1;
		int prevJump = 0;
		memo.clear();
		return lost_marks_helper(penalization, start, end, prevJump); // placeholder
	}

	private static int lost_marks_helper(int[] array, int start, int end, int prevJump) {
		// check is already calculated
		if (memo.containsKey(start)) {
			return memo.get(start);
		}
		// check for out of bounds
		if (start < 0 || start > end || end > array.length) {
			return Integer.MAX_VALUE;
		}
		// base case: arrived at the end
		if (start == end) {
			return array[end];
		}

		// initialize vars
		int penality;
		int currentJump = prevJump + 1;
		int forward = Integer.MAX_VALUE;
		int backward = Integer.MAX_VALUE;

		// calculate forward penality
		forward = lost_marks_helper(array, start + currentJump, end, currentJump);
		// calculate backward penality
		if (start != 0) {
			backward = lost_marks_helper(array, start - prevJump, end, prevJump);
		}
		// choose path to take: the min
		int pathToTake = Math.min(forward, backward);
		// append penality to array[start] iff start is not 0 and prevJump is not 0
		if (start == 0 && prevJump == 0) {
			penality = forward;
		} else {
			penality = pathToTake + array[start];
		}
		// store value to table and return
		memo.put(start, penality);
		return memo.get(start);
	}

}
