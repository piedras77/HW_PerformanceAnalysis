////////////////////////////////////////////////////////////////////////////////
//
// Title: Performance Analysis
// Course: C.S.400: Programming III, Spring, 2018
// Due Date: 03/19/18
//
// Author: Sebastian Piedra
// Email: piedra@wisc.edu
// Lecturer's Name: Debra Deppeler
// 
////////////////////////////////////////////////////////////////////////////////
//
// No Outside help was used and there are no known bugs
//
////////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import  java.lang.Runtime;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

   
    private ArrayList<String> inputData;  // The input data from each file is stored in this/ per file
    private LinkedList<String> fileNames;
    //private constants for readibility
    private final String DUMMY_VALUE;
    private final int PUT = 0;
    private final int GET = 1;
    private final int REMOVE = 2;
    private final String LINE_SEPARATOR;
    
    private File results;
    private FileWriter writer;
	private Runtime runTime;
	private TreeMap testTree;
	private HashTable testTable;
    
    /**
     * Initializes the private fields of the class
     */
    public PerformanceAnalysisHash(){
    	LINE_SEPARATOR  = "-----------------------------------------------------------"
        		+ "------------------------------------- ";
    	DUMMY_VALUE = "dummy_data";
    	results = new File("results.txt");
    	fileNames = new LinkedList<>();
    	runTime = Runtime.getRuntime();
    	testTree = new TreeMap<>();
    	testTable = new HashTable<>();
    	try {
    		writer = new FileWriter(results);
    	} catch (IOException e) {}
    }

    /**
     * makes a call to initialize the private fields of the class
     * stores the names of the files with the data
     * @param details_filename the name of the file that stores the name of the files with the data
     */
    public PerformanceAnalysisHash(String details_filename){
    	this();
    	try {
    		loadData("." + File.separator + "data" + File.separator  + details_filename + ".txt");
    		inputData.remove(0);
    		for (String fileName : inputData) {
        		fileNames.add(fileName.split(",")[0]);    			
    		}
    	} catch (IOException e) {
    		System.out.println("Error: " + e);
    	}
    }
    
    @Override
    public void compareDataStructures() {
    	try {
        	writer.write("The report name : Performance Analysis Report\n");
        	writer.write(LINE_SEPARATOR + "\n");
        	writer.write("|            FileName|      Operation| Data Structure|   Time Taken (nano sec)|     Bytes Used|\n");
        	writer.write(LINE_SEPARATOR + "\n");
        	while (!fileNames.isEmpty()) {
	    		loadData("." + File.separator + "data" + File.separator  + fileNames.peek());
	    		runTime.gc();
	        	compareInsertion();
	        	runTime.gc();
	        	compareSearch();
	        	runTime.gc();
	        	compareDeletion();
	        	fileNames.remove();
	    	}
	    	
        	writer.write(LINE_SEPARATOR);
	    	writer.flush();
	    	writer.close();
    	} catch (IOException e) {
    		System.out.println("Error: " + e);
    	}
    	
    	
    	
    }

    @Override
    public void printReport() {
    	try {
    		loadData("results.txt");
    		for (String dataRow : inputData) {
    			System.out.println(dataRow);
    		}
    	} catch(IOException e) {
    		System.out.println("Error: cannot find file results.txt");
    	}
    }

    @Override
    public void compareInsertion() {
    	long[] results = performanceComparisons(PUT);
    	addRow("|            PUT", results[0], results[1], results[2], results[3]);    	
    }

    @Override
    public void compareDeletion() {
    	long[] results = performanceComparisons(REMOVE);
    	addRow("|         REMOVE", results[0], results[1], results[2], results[3]);    	
    }

    @Override
    public void compareSearch() {
    	long[] results = performanceComparisons(GET);
    	addRow("|            GET", results[0], results[1], results[2], results[3]);    	
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
    
    /**
     * helper method that compares the memory and time performance of
     * the TreeMap vs the implemented HashTable in the operations PUT, GET, REMOVE
     * @param operationType either PUT, GET, OR REMOVE
     * @return an array with the results of the memory and time performance
     */
    private long[] performanceComparisons(int operationType) {
    	//records when the program starts performing TreeMap operations
    	long treeStart = System.nanoTime();
    	long treeStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		switch (operationType) {
	    		case PUT:
	    			testTree.put(input, DUMMY_VALUE);
	    			break;
	    		case GET:
	    			testTree.get(input);
	    			break;
	    		case REMOVE:
	    			testTree.remove(input);
    		}
    	}
    	//records when the program stops making TreeMap operations
    	long treeMemory  = treeStartMemory - runTime.freeMemory();
    	long treeRun = System.nanoTime() - treeStart;
    	 	
    	//records when the program starts making HashTable operations
    	long hashStart = System.nanoTime();
    	long hashStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		switch (operationType) {
    			case PUT:
    				testTable.put(input, DUMMY_VALUE);
    				break;
    			case GET:
    				testTable.get(input);
    				break;
    			case REMOVE:
    				testTable.remove(input);
    		}
    	}
    	
    	//records when the program stops making HashTable operations
    	long hashMemory = hashStartMemory - runTime.freeMemory();
    	long hashRun = System.nanoTime() - hashStart;
    	long[] results = { hashRun, hashMemory, treeRun, treeMemory, };
    	return results;
    }
    
    /**
     * generates the left blank spaces to shape the cell correctly 
     * @param dataString the data that must go on that cell
     * @param maxLength the maxLength of the cell
     * @return blank spaces to fill the cell
     */
    private String getBlankSpaces(String dataString, int maxLength) {
    	String result = "|";
		for (int i = 0; i < maxLength - (dataString + "").length(); i++) {
			result += " ";
		}
		
		return result;
    }
    
    /**
     * writes a new Row to the results file
     * @param operationCol the name of the operation that will be inserted
     * @param hashTime data info of the Time Taken for the HashTable
     * @param hashMem data info of the memory used by the HashTable
     * @param treeTime data info of the Time Taken for the TreeMap
     * @param treeMem data info of the memory used by the TreeMap
     */
    private void addRow(String operationCol, long hashTime, long hashMem, long treeTime, long treeMem) {
    	String commonHeader = fileNames.peek().charAt(0) == 'I'  ? "|    " : "|     ";
    	commonHeader += fileNames.peek() + operationCol;
    	try {
    		writer.write(commonHeader + "|      HASHTABLE" + getBlankSpaces(hashTime + "", 24) + hashTime
    				+ getBlankSpaces(hashMem + "", 15) + hashMem + "|\n");
    		writer.write(commonHeader + "|        TREEMAP" + getBlankSpaces(treeTime + "", 24) + treeTime
    				+ getBlankSpaces(treeMem + "", 15) + treeMem + "|\n");
    	} catch (IOException e) {
    		System.out.println("Could not write data into file");
    	}
    }
}
