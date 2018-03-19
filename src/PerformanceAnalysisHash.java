import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import  java.lang.Runtime;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

    // The input data from each file is stored in this/ per file
    private ArrayList<String> inputData;
    private LinkedList<String> fileNames;
    private final String DUMMY_VALUE;
    private File results;
    private final String LINE_SEPARATOR;
    private FileWriter writer;
	private Runtime runTime;
	private TreeMap testTree;
	private HashTable testTable;
    
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
        //TODO: Complete this function which compares the ds and generates the details
    	
    	try {
        	writer.write("The report name : Performance Analysis Report\n");
        	writer.write(LINE_SEPARATOR + "\n");
        	writer.write("|            FileName|      Operation| Data Structure|   Time Taken (nano sec)|     Bytes Used|\n");
        	writer.write(LINE_SEPARATOR + "\n");
        	while (!fileNames.isEmpty()) {
	    		loadData("." + File.separator + "data" + File.separator  + fileNames.peek());
	        	compareInsertion();
	        	compareSearch();
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
        //TODO: Complete this method
    }

    @Override
    public void compareInsertion() {
    	//records when the program starts making TreeMap insertions
    	long treeStart = System.nanoTime();
    	long treeStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		testTree.put(input, DUMMY_VALUE);
    	}
    	//records when the program stops making TreeMap insertions
    	long treeMemory  = treeStartMemory - runTime.freeMemory();
    	long treeRun = System.nanoTime() - treeStart;
    	 	
    	//records when the program starts making HashTable insertions
    	long hashStart = System.nanoTime();
    	long hashStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		testTable.put(input, DUMMY_VALUE);
    	}
    	
    	//records when the program stops making HashTable insertions
    	long hashMemory = hashStartMemory - runTime.freeMemory();
    	long hashRun = System.nanoTime() - hashStart;
    	addRow("|            PUT", hashRun, hashMemory, treeRun, treeMemory);    	
    }

    @Override
    public void compareDeletion() {
    	//records when the program starts making TreeMap deletions
    	long treeStart = System.nanoTime();
    	long treeStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		testTree.remove(input);
    	}
    	//records when the program stops making TreeMap deletions
    	long treeMemory  = treeStartMemory - runTime.freeMemory();
    	long treeRun = System.nanoTime() - treeStart;
    	 	
    	//records when the program starts making HashTable deletions
    	long hashStart = System.nanoTime();
    	long hashStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		testTable.remove(input);
    	}
    	
    	//records when the program stops making HashTable deletions
    	long hashMemory = hashStartMemory - runTime.freeMemory();
    	long hashRun = System.nanoTime() - hashStart;
    	addRow("|         REMOVE", hashRun, hashMemory, treeRun, treeMemory);    	
    }

    @Override
    public void compareSearch() {
    	//records when the program starts making TreeMap searches
    	long treeStart = System.nanoTime();
    	long treeStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		testTree.get(input);
    	}
    	//records when the program stops making TreeMap searches
    	long treeMemory  = treeStartMemory - runTime.freeMemory();
    	long treeRun = System.nanoTime() - treeStart;
    	 	
    	//records when the program starts making HashTable searches
    	long hashStart = System.nanoTime();
    	long hashStartMemory = runTime.freeMemory();
    	for (String input : inputData) {
    		testTable.get(input);
    	}
    	
    	//records when the program stops making HashTable searches
    	long hashMemory = hashStartMemory - runTime.freeMemory();
    	long hashRun = System.nanoTime() - hashStart;
    	addRow("|            GET", hashRun, hashMemory, treeRun, treeMemory);    	
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
    
    private String getBlankSpaces(String dataString, int maxLength) {
    	String result = "|";
		for (int i = 0; i < maxLength - (dataString + "").length(); i++) {
			result += " ";
		}
		
		return result;
    }
    
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
