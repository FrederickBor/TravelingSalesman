
/**
 * Main Class
 * 
 * @author Frederick Ernesto Borges Noronha
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.List;
import java.util.LinkedList;

public class Main {

    public static Graph createGraphFromFile(File file) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st; 

        // Know if the graph is bidirectional.
        st = br.readLine();
        boolean bidirectional = Boolean.parseBoolean(st.split("=")[1]);

        Graph graph = new Graph(bidirectional);

        br.readLine(); // Deletes the first line on file
        
        while ((st = br.readLine()) != null) {
            String[] result = st.split(";");

            graph.addEdge(Integer.parseInt(result[0]), 
                          Integer.parseInt(result[1]), 
                          Double.parseDouble(result[2]));

        } 

        br.close();

        return graph;
    }

    public static String generateFile(List<TravelingSalesman> solutions) throws IOException {
        String fileSeparator = System.getProperty("file.separator");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");  
        Date date = new Date();  

        String filename = "outputs" + fileSeparator + "output" + formatter.format(date) + ".csv";

        File file = new File(filename);
        file.createNewFile();

        FileWriter csvWriter = new FileWriter(filename);
        
        csvWriter.append("Graph file");
        csvWriter.append(",");
        csvWriter.append("Number of explored nodes (Only Factible)");
        csvWriter.append(",");
        csvWriter.append("Solution time (Only Factible) [in ns]");
        csvWriter.append(",");
        csvWriter.append("Average time per node (Only Factible) [in ns]");
        csvWriter.append(",");
        csvWriter.append("Number of explored nodes (First Branch and Bound)");
        csvWriter.append(",");
        csvWriter.append("Solution time (First Branch and Bound) [in ns]");
        csvWriter.append(",");
        csvWriter.append("Average time per node (First Branch and Bound) [in ns]");
        csvWriter.append(",");
        csvWriter.append("Number of explored nodes (Second Branch and Bound)");
        csvWriter.append(",");
        csvWriter.append("Solution time (Second Branch and Bound) [in ns]");
        csvWriter.append(",");
        csvWriter.append("Average time per node (Second Branch and Bound) [in ns]");
        csvWriter.append(",");
        csvWriter.append("Solution");
        csvWriter.append(",");
        csvWriter.append("Solution cost");
        csvWriter.append("\n");

        for (TravelingSalesman solution : solutions){
            csvWriter.append(solution.toString());
        }

        csvWriter.flush();
        csvWriter.close();  

        return filename;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        List<TravelingSalesman> solutions = new LinkedList<>();
        File graph_folder = new File("graphs/");
        
        for (final File file : graph_folder.listFiles()){
            Graph g = createGraphFromFile(file);
            TravelingSalesman problem = new TravelingSalesman(g, file.getName());
            problem.solve();
            solutions.add(problem);
        }

        String file = generateFile(solutions);
        // This is a program o create graphs with the CSV generated before
        Process p = Runtime.getRuntime().exec("python3 generate_plots.py " + file);
    }
}