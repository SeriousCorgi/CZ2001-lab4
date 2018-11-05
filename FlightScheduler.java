import java.util.*;

public class FlightScheduler {
	static ArrayList<String> cities = new ArrayList<String> (Arrays.asList("Singapore","Kuala Lumpur","Jakarta","Bali","Bangkok",
            "Hanoi","Manila","Cebu","Perth","Melbourne","Sydney","Auckland","Port Moresby","Taipei","Tokyo","Osaka","Sapporo",
            "Hong Kong","Shanghai", "Beijing","Seoul","New Delhi", "Mumbai","Doha","Dubai","Tel Aviv","Istanbul","Cairo","Johannesburg",
            "Cape Town","Casa Blanca","Kiev","Vienna", "Athens","London","Geneva","Stockholm","Madrid","Lisbon","Frankfurt","Copenhagen",
            "Amsterdam", "Paris","Berlin","Rome","Moscow","Havana","Port-au-Prince","New York", "Los Angeles","Chicago","San Francisco",
            "Mexico City","Ottawa","Brasilia","Rio de Janeiro","Santiago", "Lima","La Paz","Buenos Aires"));//60 cities

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		FlightScheduler flight = new FlightScheduler();
		double start_time = 0;
		double end_time = 0;
		double time = 0;
		String depart, arrive;

		while (true){
			System.out.println("Choose mode:");
			System.out.println("1. Showcase mode");
			System.out.println("2. Experiment");
			System.out.println("3. Exit");
			System.out.println("==================\n");
			switch (sc.nextInt()) {
				case 1: System.out.println("Choose number of cities");
					int c = sc.nextInt();
					int max = c * (c - 1) / 2;
					int min = c - 1;
					Random rand = new Random();
					Graph graph = new Graph(c, rand.nextInt(max - min + 1) + min);
					int[][] matrix = graph.getMatrix();
					Collections.shuffle(cities); // Randomize cities order

					for (int i = 0; i < c; i++){
						System.out.print(flight.cities.get(i) + "  ");
					}
					System.out.println("\n\nAdjacency Matrix");
					System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
					System.out.println("\n");
					
					while (true){
						System.out.println("Enter Departure city (type -1 to quit):");
						depart = sc.next();
						if (depart.equals("-1")){ break; }
						else if (!cities.contains(depart)){
							System.out.println("--City not found. Please try again!!--");
							continue;
						}

						System.out.println("Enter Arrival city: ");
						arrive = sc.next();
						if (!cities.contains(arrive)){
							System.out.println("--City not found. Please try again!!--");
							continue;
						}
						
						start_time = System.nanoTime();
						flight.bfs(depart, arrive, matrix, c);
						end_time = System.nanoTime();
						time = (end_time - start_time) / 1000000.0;
						System.out.println("Execution time (in nanosecond): " + time);
						System.out.println("==================================================");
					}
					break;
				case 2:
				case 3: return;
				default: System.out.println("Invalid choice");
					 break;
			}
		}
	}

	public void bfs(String depart, String arrive, int[][] matrix, int noOfCities){
		int city;

		// convert depart and arrive city into integer 
		int departure = cities.indexOf(depart);
		int arrival = cities.indexOf(arrive);

		Boolean[] visited = new Boolean[noOfCities];
		Arrays.fill(visited, Boolean.FALSE);
		Queue<Integer> q = new LinkedList<>();
		ArrayList<Integer> route = new ArrayList<>(); 
		HashMap<Integer, Integer> previous = new HashMap<Integer, Integer>();
		Boolean found = false;
		
		q.add(departure);

		while (q.peek() != null){
			city = q.poll();
			visited[city] = true;
			if (city == arrival){
				found = true;
				int prev = city;
				route.add(prev);
				do{
					prev = previous.get(prev);
					route.add(prev);
				} while(prev != departure);
				break;
			} else{
				for (int i = 0; i < noOfCities; i++){
					if (matrix[city][i] == 1 && !q.contains(i) && visited[i] == false){ // Check if there's an route with current city & if i is not visited
						q.add(i);
						previous.put(i, city); // Connects parents city to sub cities
					}
				}
			}
		}
		
		if (found){
			Collections.reverse(route);
			System.out.println("\nThe shortest route is: ");
			for (Integer i : route){
				if ( i != arrival){
					System.out.print(cities.get(i) + " --> ");
				} else{
					System.out.print(cities.get(i));
				}
			}
			System.out.println();
		} else{
			System.out.println("There is no route from " + depart + " to " + arrive);
		}
	}
}
