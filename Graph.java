import java.util.*;

public class Graph {
    private int numVertices;
    private int numEdges;
    private int[][] matrix;
    private Random rand = new Random();
    public int getnumVertices(){ return numVertices; }
    public int getNumEdges(){ return numEdges; }
    public int[][] getMatrix(){ return matrix; }


    public Graph(int numVertices, int numEdges){

        // Check that the graph constructed can be a connected undirected graph.
        if (numEdges > numVertices*(numVertices-1)/2) {
            throw new IllegalArgumentException("Too many connecting flights for given number of cities!\nSome flights are duplicated.");
        } else if (numEdges < numVertices-1) {
            throw new IllegalArgumentException("Too few connecting flights for given number of cities!\nSome cities cannot be reached.");
        }

        this.numVertices = numVertices;
        this.numEdges = numEdges;
        this.matrix = new int[numVertices][numVertices];  //Initialize adjacency list
        basicConnectedGraph(); // Create a connected graph to ensure every city can be reached
        addRemainingEdges(numEdges-numVertices+1); // Connect the remaining edges randomly
    }

    public void basicConnectedGraph() {
        ArrayList<Integer> unvisitedVertices = new ArrayList<>();
        ArrayList<Integer> visitedVertices = new ArrayList<>();
        for (int i = 0; i<numVertices; i++) {unvisitedVertices.add(i);}

        // Choose randomly a starting node as the graph
        Collections.shuffle(unvisitedVertices);
        visitedVertices.add(unvisitedVertices.get(0));
        unvisitedVertices.remove(0);

        int idx1, idx2;
        // Choose randomly another unvisited node and randomly connect it to one of the nodes in the existing graph
        while (!unvisitedVertices.isEmpty()) {
            idx1 = unvisitedVertices.get(0);
            unvisitedVertices.remove(0);
            idx2 = visitedVertices.get(rand.nextInt(visitedVertices.size()));
            matrix[idx1][idx2] = 1; // Connect the new node to the randomly chosen existing node
            matrix[idx2][idx1] = 1; // Ensure symmetry of the adjacency matrix
            visitedVertices.add(idx1); // Mark the new node as visited
        }
    }

    public void addRemainingEdges(int edges) {
        int idx1, idx2;

        for (int i = edges; i>0; i--) {
            // Choose 2 random nodes to connect
            do {
                idx1 = rand.nextInt(numVertices);
                idx2 = rand.nextInt(numVertices);
            } while (idx1 == idx2 || matrix[idx1][idx2] != 0 );
            System.out.println(idx1 + " " + idx2);
            matrix[idx1][idx2] += 1;
            matrix[idx2][idx1] += 1;
        }
    }

  public static void main(String[] args) {
      Graph graph = new Graph(5, 7);
      System.out.println(Arrays.deepToString(graph.matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
  }
}
