import java.util.*;

class Assignment implements Comparator<Assignment> {
	int number;
	int weight;
	int deadline;

	protected Assignment() {
	}

	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}

	/**
	 * This method is used to sort to compare assignment objects for sorting.
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// greedy decisions on weight
		if (a1.weight > a2.weight) {
			return -1;
		} else if (a1.weight < a2.weight) {
			return 1;
		} else {
			// weight are equal, greedy on deadline
			if (a1.deadline < a2.deadline) {
				return -1;
			} else if (a1.deadline > a2.deadline) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
