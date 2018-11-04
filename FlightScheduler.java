import java.util.*;

public class FlightScheduler {
	static ArrayList<String> cities = new ArrayList<String> (Arrays.asList("Singapore", "Hanoi", "Bali", "Seoul", "Tokyo", "Hokkaido", "Manila", "Sydney", "Hong Kong", "Sydney", "Beijing", "Shanghai", "Los Angeles"));	
	private int noOfCities = cities.size();

	public FlightScheduler(){}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		double start_time = 0;
		double end_time = 0;
		double time = 0;
		String depart, arrive;

		FlightScheduler flight = new FlightScheduler();
		int[][] matrix = flight.adjacencyMatrix();

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
			flight.bfs(depart, arrive, matrix);
			end_time = System.nanoTime();
			time = (end_time - start_time)/1000000.0;
			System.out.println("Execution time (in nanosecond): " + time);
			System.out.println("==================================================");
		}
	}

	public int[][] adjacencyMatrix(){
		int[][] matrix = new int[noOfCities][noOfCities];
		int a, b;
		Random rand = new Random();

		for (a = 0; a < noOfCities; a++){
			for (b = 0; b < noOfCities; b++){
				matrix[a][b] = rand.nextInt(2); 
			}
		}
		
		// Set Matrix to symmetric diagonally
		for (a = 0; a < noOfCities; a++){
			for (b = 0; b < a; b++){
				matrix[a][b] = matrix [b][a];
			}
			matrix[a][a] = 0;
		}
		return matrix;
	}

	public void bfs(String depart, String arrive, int[][] matrix){
		int city;
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
				} while (prev != departure);
				break;
			}else{
				for (int i = 0; i < noOfCities; i++){
					if (matrix[city][i] == 1 && visited[i] == false){ // Check if there's an route with current city & if i is not visited
						q.add(i);
						previous.put(i, city); // Connects parents city to sub cities
					} 
				}
			}
		}
		
		if (found){
			Collections.reverse(route);
			System.out.println("The shortest route is: ");
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
