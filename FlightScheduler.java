import java.util.*;

public class FlightScheduler {
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static ArrayList<String> cities = new ArrayList<String> (Arrays.asList("Singapore","Kuala Lumpur","Jakarta","Bali","Bangkok",
            "Hanoi","Manila","Cebu","Perth","Melbourne","Sydney","Auckland","Port Moresby","Taipei","Tokyo","Osaka","Sapporo",
            "Hong Kong","Shanghai", "Beijing","Seoul","New Delhi", "Mumbai","Doha","Dubai","Tel Aviv","Istanbul","Cairo","Johannesburg",
            "Cape Town","Casa Blanca","Kiev","Vienna", "Athens","London","Geneva","Stockholm","Madrid","Lisbon","Frankfurt","Copenhagen",
            "Amsterdam", "Paris","Berlin","Rome","Moscow","Havana","Port-au-Prince","New York", "Los Angeles","Chicago","San Francisco",
            "Mexico City","Ottawa","Brasilia","Rio de Janeiro","Santiago", "Lima","La Paz","Buenos Aires"));//60 cities

    public static void main(String[] args){
        FlightScheduler flight = new FlightScheduler();

        while (true){
            System.out.println("Choose mode:");
            System.out.println("1. Showcase mode");
            System.out.println("2. Experiment");
            System.out.println("3. Exit");
            System.out.println("==================\n");
            switch (sc.nextInt()) {
                case 1:
                    flight.showMode();
                    break;
                case 2:
                    flight.experimentalMode();
                    break;
                case 3: return;
                default: System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public void bfs(int departure, int arrival, int[][] matrix, int noOfCities, boolean mode){
        int city;

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

        // Print out the route for showcase mode
        if (mode) {
            if (found) {
                Collections.reverse(route);
                System.out.println("\nThe shortest route is: ");
                for (Integer i : route) {
                    if (i != arrival) {
                        System.out.print(cities.get(i) + " --> ");
                    } else {
                        System.out.print(cities.get(i));
                    }
                }
                System.out.println();
            } else {
                System.out.println("There is no route found...");
            }
        } else {
            return;
        }
    }

    public void showMode() {
        int numCities, maxEdges, minEdges, depart, arrive;
        double start_time, end_time, time;
        String departCity, arriveCity;

        do {
            System.out.println("Choose number of cities (maximum 60):");
            numCities = sc.nextInt();
        } while (numCities > 60 || numCities < 1 );
        maxEdges = numCities * (numCities - 1) / 2;
        minEdges = numCities - 1;

        Graph graph = new Graph(numCities, rand.nextInt(maxEdges - minEdges + 1) + minEdges);
        int[][] matrix = graph.getMatrix();
        Collections.shuffle(cities); // Randomize cities order

        System.out.println("City order:");
        for (int i = 0; i < numCities; i++){
            System.out.print((i+1) + " - "+ cities.get(i) + ";  ");
        }
        System.out.println("\nAdjacency Matrix:");
        System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        System.out.println("\n");

        while (true){
            System.out.println("Enter Departure city (type -1 to quit):");
            departCity = sc.nextLine();
            if (departCity.equals("-1")){ break; }
            else if (!cities.contains(departCity)){
                System.out.println("--City not found. Please try again!!--");
                continue;
            }

            System.out.println("Enter Arrival city: ");
            arriveCity = sc.nextLine();
            if (!cities.contains(arriveCity)){
                System.out.println("--City not found. Please try again!!--");
                continue;
            }

            // convert depart and arrive city into integer
            depart = cities.indexOf(departCity);
            arrive = cities.indexOf(arriveCity);

            start_time = System.nanoTime();
            bfs(depart, arrive, matrix, numCities, true);
            end_time = System.nanoTime();
            time = (end_time - start_time) / 1000000.0;
            System.out.println("Execution time (in nanosecond): " + time);
            System.out.println("==================================================");
        }
    }

    public void experimentalMode() {
        int numCities, numEdges, numLoop, depart, arrive;
        double start_time, end_time, time, aveTime = 0;

        System.out.println("Choose number of cities: ");
        numCities = sc.nextInt();
        System.out.println("Choose number of flight connections: ");
        numEdges = sc.nextInt();

        Graph graph = new Graph(numCities, numEdges);
        int[][] matrix = graph.getMatrix();
        System.out.println("Graph of "+ numCities +" cities and "+ numEdges +" flight connections is created!");
        System.out.println("Choose number of test cases for the generated graph:");
        numLoop = sc.nextInt();

        for (int i=0; i<numLoop; i++) {
            // Choose 2 cities randomly
            do {
                depart = rand.nextInt(numCities);
                arrive = rand.nextInt(numCities);
            } while (depart==arrive);

            start_time = System.nanoTime();
            bfs(depart, arrive, matrix, numCities, false);
            end_time = System.nanoTime();
            time = (end_time - start_time) / 1000000.0;
            aveTime += time;
            System.out.println("Execution time round "+ i +": " + time);
        }
        System.out.println("==================================================");
        System.out.println("Average execution time: " + (aveTime/numLoop));
        System.out.println("==================================================");
    }
}