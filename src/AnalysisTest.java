
import java.util.ArrayList;

public class AnalysisTest {
    public static void main(String[] args)  {
                // TODO Add code for checking command line arguments
                PerformanceAnalysisHash ana = new PerformanceAnalysisHash("data_details");
                ana.compareDataStructures();
                ana.printReport();
            }
}
