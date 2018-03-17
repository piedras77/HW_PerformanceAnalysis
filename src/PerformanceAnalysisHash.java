import java.io.*;
import java.util.ArrayList;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

    // The input data from each file is stored in this/ per file
    private ArrayList<String> inputData;
    
    public PerformanceAnalysisHash(){
    }

    public PerformanceAnalysisHash(String details_filename){
        //TODO: Save the details of the test data files
    }
    @Override
    public void compareDataStructures() {
        //TODO: Complete this function which compares the ds and generates the details
    }

    @Override
    public void printReport() {
        //TODO: Complete this method
    }

    @Override
    public void compareInsertion() {
        //TODO: Complete this method
    }

    @Override
    public void compareDeletion() {
        //TODO: Complete this method
    }

    @Override
    public void compareSearch() {
        //TODO: Complete this method
    }

    /*
    An implementation of loading files into local data structure is provided to you
    Please feel free to make any changes if required as per your implementation.
    However, this function can be used as is.
     */
    @Override
    public void loadData(String filename) throws IOException {

        // Opens the given test file and stores the objects each line as a string
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        inputData = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            inputData.add(line);
            line = br.readLine();
        }
        br.close();
    }
}
