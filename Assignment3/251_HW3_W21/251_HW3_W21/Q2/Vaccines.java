import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Vaccines {

	public class Country {
		private int ID;
		private int vaccine_threshold;
		private int vaccine_to_receive;
		private ArrayList<Integer> allies_ID;
		private ArrayList<Integer> allies_vaccine;

		public Country() {
			this.allies_ID = new ArrayList<Integer>();
			this.allies_vaccine = new ArrayList<Integer>();
			this.vaccine_threshold = 0;
			this.vaccine_to_receive = 0;
		}

		public int get_ID() {
			return this.ID;
		}

		public int get_vaccine_threshold() {
			return this.vaccine_threshold;
		}

		public ArrayList<Integer> get_all_allies_ID() {
			return allies_ID;
		}

		public ArrayList<Integer> get_all_allies_vaccine() {
			return allies_vaccine;
		}

		public int get_allies_ID(int index) {
			return allies_ID.get(index);
		}

		public int get_allies_vaccine(int index) {
			return allies_vaccine.get(index);
		}

		public int get_num_allies() {
			return allies_ID.size();
		}

		public int get_vaccines_to_receive() {
			return vaccine_to_receive;
		}

		public void set_allies_ID(int value) {
			allies_ID.add(value);
		}

		public void set_allies_vaccine(int value) {
			allies_vaccine.add(value);
		}

		public void set_ID(int value) {
			this.ID = value;
		}

		public void set_vaccine_threshold(int value) {
			this.vaccine_threshold = value;
		}

		public void set_vaccines_to_receive(int value) {
			this.vaccine_to_receive = value;
		}

	}

	public int vaccines(Country[] graph) {

		// inititalize data
		int safeContries = graph.length;
		boolean[] lockdown = new boolean[graph.length];
		boolean[] visited = new boolean[graph.length];
		Queue<Integer> queue = new LinkedList<Integer>();
		// country 1 goes to lockdown
		lockdown[0] = true;
		visited[0] = true;
		queue.add(0);

		while (!queue.isEmpty()) {
			int currentIndex = queue.poll();
			// append country 1's neighbors
			if (currentIndex == 0) {
				// System.out.println("Strting with Country 1");
				for (int i = 0; i < graph[currentIndex].get_num_allies(); i++) {
					int allyIdx = graph[currentIndex].get_allies_ID(i) - 1;
					if (!lockdown[allyIdx]) {
						int allyVaccine = graph[currentIndex].get_allies_vaccine(i);
						// reduce ally's vaccine to receive
						int currentAllyVaccineToReceive = graph[allyIdx].get_vaccines_to_receive();
						int newAllyVaccineToReceive = currentAllyVaccineToReceive - allyVaccine;
						graph[allyIdx].set_vaccines_to_receive(newAllyVaccineToReceive);

						queue.add(allyIdx);
						visited[allyIdx] = true;
						// System.out.println("Added allyIdx " + allyIdx);
					}
				}
			} else {
				// current country has enough vaccines
				if (graph[currentIndex].get_vaccine_threshold() <= graph[currentIndex].get_vaccines_to_receive()) {
					// System.out.println("Country with id (enough vaccine)" + currentIndex);
					for (int i = 0; i < graph[currentIndex].get_num_allies(); i++) {
						int allyIdx = graph[currentIndex].get_allies_ID(i) - 1;
						if (!lockdown[allyIdx] && !visited[allyIdx]) {
							queue.add(allyIdx);
							visited[allyIdx] = true;
							// System.out.println("Added allyIdx " + allyIdx);
						}
					}
				} else {
					// System.out.println("Country with id (not enough vaccine)" + currentIndex);
					// not enough vaccine and not in lockdown yet
					if (!lockdown[currentIndex]) {
						lockdown[currentIndex] = true;
						safeContries -= 1;
						for (int i = 0; i < graph[currentIndex].get_num_allies(); i++) {
							int allyIdx = graph[currentIndex].get_allies_ID(i) - 1;
							if (!lockdown[allyIdx]) {
								int allyVaccine = graph[currentIndex].get_allies_vaccine(i);
								// reduce ally's vaccine to receive
								int currentAllyVaccineToReceive = graph[allyIdx].get_vaccines_to_receive();
								int newAllyVaccineToReceive = currentAllyVaccineToReceive - allyVaccine;
								graph[allyIdx].set_vaccines_to_receive(newAllyVaccineToReceive);
								queue.add(allyIdx);
								visited[allyIdx] = true;
								// System.out.println("Added allyIdx " + allyIdx);
							}
						}
					}
				}
			}
		}

		return safeContries - 1;
	}

	public void test(String filename) throws FileNotFoundException {
		Vaccines hern = new Vaccines();
		Scanner sc = new Scanner(new File(filename));
		int num_countries = sc.nextInt();
		Country[] graph = new Country[num_countries];
		for (int i = 0; i < num_countries; i++) {
			graph[i] = hern.new Country();
		}
		for (int i = 0; i < num_countries; i++) {
			if (!sc.hasNext()) {
				sc.close();
				sc = new Scanner(new File(filename + ".2"));
			}
			int amount_vaccine = sc.nextInt();
			graph[i].set_ID(i + 1);
			graph[i].set_vaccine_threshold(amount_vaccine);
			int other_countries = sc.nextInt();
			for (int j = 0; j < other_countries; j++) {
				int neighbor = sc.nextInt();
				int vaccine = sc.nextInt();
				graph[neighbor - 1].set_allies_ID(i + 1);
				graph[neighbor - 1].set_allies_vaccine(vaccine);
				graph[i].set_vaccines_to_receive(graph[i].get_vaccines_to_receive() + vaccine);
			}
		}
		sc.close();
		int answer = vaccines(graph);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Vaccines vaccines = new Vaccines();
		vaccines.test(args[0]); // run 'javac Vaccines.java' to compile, then run 'java Vaccines testfilename'
	}

}