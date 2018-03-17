
import java.io.IOException;

/**
 * Interface for implementing the Performance analysis across data structures
 */
public interface PerformanceAnalysis {

    /**
     * The important function that compares the implemented HashTable with
     * TreeMap of Java and generates the table with all the comparision details
     * This can internally call - compareInsertion, compareDeletion, CompareSearch
     * for all the test data provided.
     */
    void compareDataStructures() ;

    /** Function used to print out the final report
     *
     */
    void printReport();

    /**
     * Standalone method for comparing insertion operation
     * across HashTable and TreeMap
     */
    void compareInsertion();

    /**
     * Standalone method for comparing deletion operation
     * across HashTable and TreeMap
     */
    void compareDeletion();

    /**
     * Standalone method for comparing search operation
     * across HashTable and TreeMap
     */
    void compareSearch();

    /**
     *
     * @param filename: Loads the data from the test file to local data structure
     *                This is already implemented for you.
     * @throws IOException
     */
    void loadData(String filename) throws IOException;

}
