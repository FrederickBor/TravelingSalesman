import java.util.Arrays;

/**
 * Node Class
 * 
 * This Node Class is for use it on Traveler Class
 * 
 * @author Frederick Ernesto Borges Noronha
 * @version 1.0
 */
public class Node implements Comparable{

    // Atributes
    /** Solution variables*/
    private int solution[];
    /** Indicates which nodes are visited until k level */
    private boolean visited[];
    /** Level of the solution */
    private int k;
    /** Cost of the solution */
    private double cost;
    /** Optimistic cost of the solution */
    private double optCost;

    // Constructors
    /**
     * Constructor with parameters
     * @param n number of nodes in the solution.
     */
    public Node(final int n) {
        solution = new int[n];
        visited = new boolean[n];
        k = 0;
        cost = 0;
        optCost = 0;

        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }
    }

    /**
     * Constructor with parameters.
     * @param solution
     * @param visited
     * @param k
     * @param cost
     * @param optCost
     */
    public Node(int[] solution, boolean[] visited, int k, double cost, double optCost) {
        this.solution = solution;
        this.visited = visited;
        this.k = k;
        this.cost = cost;
        this.optCost = optCost;
    }

    // Methods
    public int[] getSolution() {
        return solution.clone();
    }

    public void setSolution(final int[] solution) {
        this.solution = solution;
    }

    public void setSolution(final int position, final int value) {
        this.solution[position] = value;
    }

    public boolean[] getVisited() {
        return visited.clone();
    }

    public boolean isVisited(int vertex) {
        return visited[vertex];
    }

    public void setVisited(final boolean[] visited) {
        this.visited = visited;
    }

    public void setVisited(final int position, final boolean value) {
        this.visited[position] = value;
    }

    public int getK() {
        return k;
    }

    public void setK(final int k) {
        this.k = k;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(final double cost) {
        this.cost = cost;
    }

    public double getOptCost() {
        return optCost;
    }

    public void setOptCost(final double optCost) {
        this.optCost = optCost;
    }

    /**
     * Calculates the optimistic cost for the node
     * 
     * @param minOut
     * @param minIn
     * @return
     */
    public double calculateOptimistCostFBB(double minCost) {
        return (solution.length - k + 1) * minCost;
    }

    /**
     * Calculates the optimistic cost for the node
     * 
     * @param minOut
     * @param minIn
     * @return
     */
    public double calculateOptimistCostSBB(double[] minOut, double[] minIn) {
        double sumIn = minIn[0];
        double sumOut = minIn[solution[k - 1]];

        int i = 1;
        while (i < visited.length) {
            if (!visited[i]) {
                sumIn += minIn[i];
                sumOut += minOut[i];
            }
            i++;
        }

        return cost + Math.max(sumIn, sumOut);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(cost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + k;
        temp = Double.doubleToLongBits(optCost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + Arrays.hashCode(solution);
        result = prime * result + Arrays.hashCode(visited);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
            return false;
        if (k != other.k)
            return false;
        if (Double.doubleToLongBits(optCost) != Double.doubleToLongBits(other.optCost))
            return false;
        if (!Arrays.equals(solution, other.solution))
            return false;
        if (!Arrays.equals(visited, other.visited))
            return false;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        Node n2  = (Node) o;
        int ret = 0;

        if (this.optCost < n2.optCost) ret = -1;
        else if (this.optCost > n2.optCost) ret = 1;

        return ret;
    }

    @Override
    public String toString() {
        return "Node [cost=" + cost + ", k=" + k + ", optCost=" + optCost + ", solution=" + Arrays.toString(solution)
                + ", visited=" + Arrays.toString(visited) + "]";
    }    

}