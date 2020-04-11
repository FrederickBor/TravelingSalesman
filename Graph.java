/**
 * Graph Class
 * 
 * This graph class is represented by Adjacency List.
 * 
 * @author Frederick Ernesto Borges Noronha
 * @version 1.0
 */
import java.util.Map;
import java.util.HashMap;

class Graph { 
    
    // Atributes
    private Map<Integer, Map<Integer, Double> > map;
    private Map<Integer, Double> minCostsIn;
    private Map<Integer, Double> minCostsOut;
    private boolean bidirectional; 
    private double minCost;
    
    // Constructors
    /**
     * Constructor without parameters.
     * This creates a bidirectional graph.
     */
    public Graph(){
        map = new HashMap<Integer, Map<Integer, Double> >();
        minCostsIn = new HashMap<Integer, Double>();
        minCostsOut = new HashMap<Integer, Double>();
        bidirectional = true;
        minCost = Double.POSITIVE_INFINITY;
    }

    /**
     * Constructor with parameters.
     * @param bidirectional Indicates if the graph is bidirectional or not.
     */
    public Graph(boolean bidirectional){
        map = new HashMap<Integer, Map<Integer, Double> >();
        this.bidirectional = bidirectional;
        minCostsIn = new HashMap<Integer, Double>();
        minCostsOut = new HashMap<Integer, Double>();
        minCost = Double.POSITIVE_INFINITY;
    }

    // Methods
    /**
     * Add a new vertex to the graph.
     * @param source New vertex to be added.
     */
    public void addVertex(int source) { 
        map.put(source, new HashMap<>());
        minCostsIn.put(source, Double.POSITIVE_INFINITY);
        minCostsOut.put(source, Double.POSITIVE_INFINITY);
    } 

    /**
     * Add an edge between two vertexes. 
     * If the graph is bidirectional will add 2 edges.
     * @param source Source vertex.
     * @param destination Destination vertex.
     */
    public void addEdge(int source, int destination) { 
  
        if (!map.containsKey(source)) 
            addVertex(source); 
  
        if (!map.containsKey(destination)) 
            addVertex(destination); 
        
        map.get(source).put(destination, 1.0);
        minCostsOut.put(source, Math.min(minCostsOut.get(source), 1.0));
        minCostsIn.put(destination, Math.min(minCostsIn.get(destination), 1.0));
        minCost = Math.min(minCost, 1.0);
        

        // If it's a bidirectional Graph add the other edge
        if (bidirectional == true) { 
            map.get(destination).put(source, 1.0);
            minCostsOut.put(destination, Math.min(minCostsOut.get(destination), 1.0));
            minCostsIn.put(source, Math.min(minCostsIn.get(source), 1.0));
        } 
    } 

    /**
     * Add an edge between two vertexes with costs diferent to 1.
     * If the graph is bidirectional will add 2 edges.
     * @param source Source vertex.
     * @param destination Destination vertex.
     * @param cost Cost to move between Source vertex and Destination vertex.
     */
    public void addEdge(int source, 
                        int destination, 
                        Double cost) { 
  
        if (!map.containsKey(source)) 
            addVertex(source); 
  
        if (!map.containsKey(destination)) 
            addVertex(destination); 
  
        map.get(source).put(destination, cost);
        minCostsOut.put(source, Math.min(minCostsOut.get(source), cost));
        minCostsIn.put(destination, Math.min(minCostsIn.get(destination), cost));
        minCost = Math.min(minCost, cost);

        // If it's a bidirectional Graph add the other edge
        if (bidirectional == true) { 
            map.get(destination).put(source, cost);
            minCostsOut.put(destination, Math.min(minCostsOut.get(destination), cost));
            minCostsIn.put(source, Math.min(minCostsIn.get(source), cost));
        } 
    } 

    /**
     * Get the number of vertexes on the graph.
     * @return Number of vertexes.
     */
    public int getVertexCount(){ 
        return map.keySet().size();
    } 

    /**
     * Get the number of edges on the graph.
     * @return Number of edges.
     */
    public int getEdgesCount(){ 
        int count = 0; 

        for (int v : map.keySet()) { 
            count += map.get(v).size(); 
        } 
        if (bidirectional == true) { 
            count = count / 2; 
        } 

        return count;
    } 

    /**
     * Indicates if a vertex exists in the graph.
     * @param source Vertex to be evaluated.
     * @return True if the vertex is in the graph if not return false.
     */
    public boolean hasVertex(int source){ 
        return map.containsKey(source);
    } 

    /**
     * Indicates if an edge exists in the graph.
     * @param source Source vertex.
     * @param destination Destination Vertex.
     * @return True if source vertext has an edge with destination vertex
     */
    public boolean hasEdge(int source, int destination){ 
        return map.get(source).containsKey(destination);
    } 

    /**
     * Indicates if the graph is bidirectional or not.
     * @return True if the graph is bidirectional
     */
    public boolean isBidirectional(){
        return bidirectional;
    }

    /**
     * Gets the minimun cost of all edges in the graph
     * @return Minimun cost of all edges in the graph
     */
    public double getMinCost(){
        return minCost;
    }

    /**
     * Calculates the minimum cost to get In and Out in each node.
     * @return Auxiliar class with two list with the minCostIn and minCostOut.
     */
    public Auxiliar getMinimumCost(){
        Auxiliar aux = new Auxiliar(getVertexCount());
        
        int i = 0;
        for (int vertex : map.keySet()){
            aux.setMinIn(i, minCostsIn.get(vertex));
            aux.setMinOut(i, minCostsOut.get(vertex));
            i++;
        }

        return aux;
    }

    /**
     * Get the cost to go from Source vertex to Destination vertex.
     * @param source Source vertex.
     * @param destination Destination vertex.
     * @return
     */
    public double getCost(int source, int destination){
        return map.get(source).get(destination);
    }
  
    /**
     * Prints the adjancency list of each vertex. 
     */
    @Override
    public String toString(){ 
        StringBuilder sb = new StringBuilder(); 
  
        for (int source : map.keySet()) { 
            sb.append(source + ": "); 
            for (int destination : map.get(source).keySet()) { 
                sb.append(destination + "(" + map.get(source).get(destination) + ") "); 
            } 
            sb.append("\n"); 
        } 
  
        return (sb.toString()); 
    } 
} 