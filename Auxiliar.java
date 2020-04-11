import java.util.Arrays;

/**
 * Auxiliar Class
 * 
 * This Auxilar class helps to return a tuple of elements.
 * 
 * @author Frederick Ernesto Borges Noronha
 * @version 1.0
 */
public class Auxiliar {
    // Atributes
    private double minIn[];
    private double minOut[];

    // Constructors
    public Auxiliar(int n) {
        minIn = new double[n];
        minOut = new double[n];
    }

    public Auxiliar(double[] minIn, double[] minOut) {
        this.minIn = minIn;
        this.minOut = minOut;
    }

    // Methods
    public double[] getMinIn() {
        return minIn;
    }

    public void setMinIn(int position, double value) {
        this.minIn[position] = value;
    }

    public double[] getMinOut() {
        return minOut;
    }

    public void setMinOut(int position, double value) {
        this.minOut[position] = value;
    }

    @Override
    public String toString() {
        return "Auxiliar [minIn=" + Arrays.toString(minIn) + ", minOut=" + Arrays.toString(minOut) + "]";
    }

    

}