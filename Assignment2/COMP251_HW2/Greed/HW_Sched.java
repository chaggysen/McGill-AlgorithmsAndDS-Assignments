import java.util.*;

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;

	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i = 0; i < size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m = size;
	}

	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment that will be done
	 *         at time i.
	 */
	public int[] SelectAssignments() {
		// TODO Implement this

		// Sort assignments
		// Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());

		// If homeworkPlan[i] has a value -1, it indicates that the
		// i'th timeslot in the homeworkPlan is empty
		// homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i = 0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}

		for (int i = 0; i < Assignments.size(); i++) {
			int deadline = Assignments.get(i).deadline - 1;
			// the day before the deadline is empty
			if (homeworkPlan[deadline] == -1) {
				homeworkPlan[Assignments.get(i).deadline - 1] = Assignments.get(i).number;
			} else {
				// the day before the dealine is taken. Check for previous days
				for (int j = Assignments.get(i).deadline - 1; j >= 0; j--) {
					if (homeworkPlan[j] == -1) {
						homeworkPlan[j] = Assignments.get(i).number;
					}
				}
			}
		}

		return homeworkPlan;
	}
}
