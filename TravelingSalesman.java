
/**
 * TravelingSalesman Class
 *  
 * This class is to solve all the problems related with the traveling salesman problem.
 * 
 * @author Frederick Ernesto Borges Noronha
 * @version 1.0
 */
import java.util.Arrays;
import java.util.Date;
import java.util.PriorityQueue;

public class TravelingSalesman {

    // Common Atributes
    /** Graph to be solved */
    private Graph graph;
    /** Number of vertex on graph */
    private int numVertex;
    /** Input file name */
    private String fileIn;

    // Solution Atributes 
    /** Root of the solution */
    private Node rootFactible, rootFBB, rootSBB;
    /** Priority Queue to solve this problem */
    private PriorityQueue<Node> priorQueueFactible, priorQueueFBB, priorQueueSBB;
    /** Solution array */
    private int solutionFactible[], solutionFBB[], solutionSBB[];
    /** Best cost of solution */
    private double bestCostFactible, bestCostFBB, bestCostSBB;

    // Second Branch and Bound exclusives
    /** Minimum cost to get out */
    private double minOut[];
    /** Minimum cost to get in */
    private double minIn[];

    // Measurement Atributes

    /** Solution time */
    private long solutionTimeFactible, solutionTimeFBB, solutionTimeSBB;
    /** Average time */
    private long avgNodeTimeFactible, avgNodeTimeFBB, avgNodeTimeSBB;
    /** Number of explored nodes */
    private int numExploredNodesFactible, numExploredNodesFBB, numExploredNodesSBB;
    

    // Constructor
    /**
     * Constructor with parameters.
     * 
     * @param graph Graph to be solved.
     */
    public TravelingSalesman(Graph graph, String fileIn) {
        // Common Atributes
            this.graph = graph;
            this.fileIn = fileIn;
            numVertex = graph.getVertexCount();
        // End Commin Atributes
        
        // Factible
            solutionTimeFactible = 0;
            priorQueueFactible = new PriorityQueue<Node>();
            rootFactible = new Node(numVertex);
            bestCostFactible = Double.POSITIVE_INFINITY;
            
            rootFactible.setSolution(0, 0);
            rootFactible.setK(1);
            rootFactible.setVisited(0, true);
            priorQueueFactible.add(rootFactible);
            numExploredNodesFactible = 1;
        // End Factible

        // First Branch and Bound (FBB)
            solutionTimeFBB = 0;
            priorQueueFBB = new PriorityQueue<Node>();
            rootFBB = new Node(numVertex);
            bestCostFBB = Double.POSITIVE_INFINITY;
            
            rootFBB.setSolution(0, 0);
            rootFBB.setK(1);
            rootFBB.setVisited(0, true);
            rootFBB.setOptCost(rootFBB.calculateOptimistCostFBB(graph.getMinCost()));
            priorQueueFBB.add(rootFBB);
            numExploredNodesFBB = 1;
        // End First Branch and Bound (FBB)
        
        // Second Branch and Bound (SBB)
            solutionTimeSBB = 0;
            priorQueueSBB = new PriorityQueue<Node>();
            rootSBB = new Node(numVertex);
            bestCostSBB = Double.POSITIVE_INFINITY;

            Auxiliar minInOut = graph.getMinimumCost(); // O(n)
            minOut = minInOut.getMinOut();
            minIn = minInOut.getMinIn();
            
            rootSBB.setSolution(0, 0);
            rootSBB.setK(1);
            rootSBB.setVisited(0, true);
            rootSBB.setOptCost(rootSBB.calculateOptimistCostSBB(minOut, minIn));
            priorQueueSBB.add(rootSBB);
            numExploredNodesSBB = 1;
        // End Second Branch and Bound (SBB)
    }

    // Methods

    /**
     * This method solve the traveling salesman problem 
     * with only with factible condition.
     */
    private void solveFactible(){
        long init_time = System.nanoTime();
        while ((!priorQueueFactible.isEmpty())) {
            Node node = priorQueueFactible.poll();
            this.numExploredNodesFactible++;
            long init_node_time = System.nanoTime();
            int vertex = 1;
            while (vertex < numVertex) {                
                Node aux = new Node(node.getSolution(),
                                    node.getVisited(),
                                    node.getK(),
                                    node.getCost(),
                                    node.getOptCost());
                
                if ((!aux.isVisited(vertex)) && (graph.hasEdge(aux.getSolution()[aux.getK() - 1], vertex))) {
                    aux.setSolution(aux.getK(), vertex);
                    aux.setVisited(vertex, true);
                    aux.setCost(aux.getCost() + graph.getCost(aux.getSolution()[aux.getK() - 1], vertex));
                    aux.setK(aux.getK() + 1);
                    if (aux.getK() == numVertex) {
                        if ((graph.hasEdge(aux.getSolution()[numVertex - 1], 0))
                        && (aux.getCost() + graph.getCost(aux.getSolution()[numVertex - 1], 0) < bestCostFactible)) {
                            // There is a better solution
                            solutionFactible = aux.getSolution();
                            bestCostFactible = aux.getCost() + graph.getCost(aux.getSolution()[numVertex - 1], 0);
                        }
                    } else {
                        priorQueueFactible.add(aux);
                    }
                }
                long final_node_time = System.nanoTime();
                this.avgNodeTimeFactible += final_node_time - init_node_time;
                vertex++;
            }
        }
        this.avgNodeTimeFactible /= this.numExploredNodesFactible;
        long final_time = System.nanoTime();
        this.solutionTimeFactible = final_time - init_time;
    }

    /**
     * This method solve the traveling salesman problem 
     * with the First Branch and Bound method.
     * Optimistic cost = (n - k + 1) * minCost
     */
    private void solveFBB(){
        long init_time = System.nanoTime();
        while ((!priorQueueFBB.isEmpty()) && (priorQueueFBB.peek().getOptCost() < bestCostFBB)) {
            Node node = priorQueueFBB.poll();
            this.numExploredNodesFBB++;
            long init_node_time = System.nanoTime();
            int vertex = 1;
            while (vertex < numVertex) {                
                Node aux = new Node(node.getSolution(),
                                    node.getVisited(),
                                    node.getK(),
                                    node.getCost(),
                                    node.getOptCost());
                
                if ((!aux.isVisited(vertex)) && (graph.hasEdge(aux.getSolution()[aux.getK() - 1], vertex))) {
                    aux.setSolution(aux.getK(), vertex);
                    aux.setVisited(vertex, true);
                    aux.setCost(aux.getCost() + graph.getCost(aux.getSolution()[aux.getK() - 1], vertex));
                    aux.setK(aux.getK() + 1);
                    if (aux.getK() == numVertex) {
                        if ((graph.hasEdge(aux.getSolution()[numVertex - 1], 0))
                        && (aux.getCost() + graph.getCost(aux.getSolution()[numVertex - 1], 0) < bestCostFBB)) {
                            // There is a better solution
                            solutionFBB = aux.getSolution();
                            bestCostFBB = aux.getCost() + graph.getCost(aux.getSolution()[numVertex - 1], 0);
                        }
                    } else {
                        aux.setOptCost(aux.calculateOptimistCostFBB(graph.getMinCost()));
                        if (aux.getOptCost() < bestCostFBB) {
                            priorQueueFBB.add(aux);
                        }
                    }
                }
                long final_node_time = System.nanoTime();
                this.avgNodeTimeFBB += final_node_time - init_node_time;
                vertex++;
            }
        }
        this.avgNodeTimeFBB /= this.numExploredNodesFBB;
        long final_time = System.nanoTime();
        this.solutionTimeFBB = final_time - init_time;
    }

    /**
     * This method solve the traveling salesman problem 
     * with the Second Branch and Bound method.
     * (Optimistic cost = )
     */
    private void solveSBB(){
        long init_time = System.nanoTime();
        while ((!priorQueueSBB.isEmpty()) && (priorQueueSBB.peek().getOptCost() < bestCostSBB)) {
            Node node = priorQueueSBB.poll();
            this.numExploredNodesSBB++;
            long init_node_time = System.nanoTime();
            int vertex = 1;
            while (vertex < numVertex) {                
                Node aux = new Node(node.getSolution(),
                                    node.getVisited(),
                                    node.getK(),
                                    node.getCost(),
                                    node.getOptCost());
                
                if ((!aux.isVisited(vertex)) && (graph.hasEdge(aux.getSolution()[aux.getK() - 1], vertex))) {
                    aux.setSolution(aux.getK(), vertex);
                    aux.setVisited(vertex, true);
                    aux.setCost(aux.getCost() + graph.getCost(aux.getSolution()[aux.getK() - 1], vertex));
                    aux.setK(aux.getK() + 1);
                    if (aux.getK() == numVertex) {
                        if ((graph.hasEdge(aux.getSolution()[numVertex - 1], 0))
                        && (aux.getCost() + graph.getCost(aux.getSolution()[numVertex - 1], 0) < bestCostSBB)) {
                            // There is a better solution
                            solutionSBB = aux.getSolution();
                            bestCostSBB = aux.getCost() + graph.getCost(aux.getSolution()[numVertex - 1], 0);
                        }
                    } else {
                        aux.setOptCost(aux.calculateOptimistCostSBB(minOut, minIn));
                        if (aux.getOptCost() < bestCostSBB) {
                            priorQueueSBB.add(aux);
                        }
                    }
                }
                long final_node_time = System.nanoTime();
                this.avgNodeTimeSBB += final_node_time - init_node_time;
                vertex++;
            }
        }
        this.avgNodeTimeSBB /= this.numExploredNodesSBB;
        long final_time = System.nanoTime();
        this.solutionTimeSBB = final_time - init_time;
    }

    public void solve(){
        solveFactible();
        solveFBB();
        solveSBB();
    }

    @Override
    public String toString() {

        String solution = "";

        for (int i = 0; i < solutionSBB.length; i++){
            solution += solutionSBB[i];
            if (i != solutionSBB.length - 1){
                solution += "->";
            }            
        }

        String ret = fileIn + "," +
            numExploredNodesFactible + "," +
            solutionTimeFactible + "," +
            avgNodeTimeFactible + "," +
            numExploredNodesFBB + "," +
            solutionTimeFBB + "," +
            avgNodeTimeFBB + "," +
            numExploredNodesSBB + "," +
            solutionTimeSBB + "," +
            avgNodeTimeSBB + "," + 
            solution + "," +
            Double.toString(bestCostSBB) + "\n";
        return ret;
    }
}