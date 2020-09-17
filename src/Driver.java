import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

/*
	This Driver file will be replaced by ours during our grading.
*/
public class Driver {
    private static String filename; // input file name
    private static boolean testHeap; // set to true by -h flag
    private static boolean testDijkstra; // set to true by -d flag
    private static Program2 testProgram2; // instance of your graph
    private static ArrayList<City> cities;

    private static void usage() { // error message
        System.err.println("usage: java Driver [-h] [-d] <filename>");
        System.err.println("\t-h\tTest Heap implementation");
        System.err.println("\t-d\tTest Dijkstra implementation");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        cities = new ArrayList<City>();
        parseArgs(args);
        parseInputFile(filename);
        testRun();
    }

    public static void parseArgs(String[] args) {
        boolean flagsPresent = false;
        if (args.length == 0) {
            usage();
        }

        filename = "";

        for (String s : args) {
            if (s.equals("-h")) {
                flagsPresent = true;
                testHeap = true;
            } else if (s.equals("-d")) {
                flagsPresent = true;
                testDijkstra = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testHeap = true;
            testDijkstra = true;
        }
    }

    public static void parseInputFile(String filename)
            throws Exception {
        int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        numE = Integer.parseInt(inputSize[1]);
        HashMap<Integer, ArrayList<NeighborWeightTuple>> tempNeighbors = new HashMap<>();
        testProgram2 = new Program2(numV);
        for (int i = 0; i < numV; ++i) {

            String[] pairs = sc.nextLine().split(" ");
            String[] weightPairs = sc.nextLine().split(" ");

            Integer currNode = Integer.parseInt(pairs[0]);
            City currentCity = new City(currNode);
            cities.add(currNode, currentCity);
            ArrayList<NeighborWeightTuple> currNeighbors = new ArrayList<>();
            tempNeighbors.put(currNode, currNeighbors);

            for (int k = 1; k < pairs.length; k++) {
                Integer neighborVal = Integer.parseInt(pairs[k]);
                Integer weightVal = Integer.parseInt(weightPairs[k]);
                currNeighbors.add(new NeighborWeightTuple(neighborVal, weightVal));
            }
        }
        for (int i = 0; i < cities.size(); ++i) {
            City currCity = cities.get(i);
            ArrayList<NeighborWeightTuple> neighbors = tempNeighbors.get(i);
            for (NeighborWeightTuple neighbor : neighbors) {
                testProgram2.setEdge(currCity, cities.get(neighbor.neighborID), neighbor.weight);
            }
        }

        testProgram2.setAllNodesArray(cities);
    }

    // feel free to alter this method however you wish. We will replace it with our own version for testing.
    public static void testRun() {
        if (testHeap) {
            System.out.println("Heap test");
            Heap minHeap = new Heap();
            minHeap.buildHeap(cities);
            System.out.println("Initial min Heap");
            System.out.println(minHeap.toString());
            
            System.out.println("Changing minCost");
            minHeap.changeKey(minHeap.toArrayList().get(0), 0);
            minHeap.changeKey(minHeap.toArrayList().get(1), 1);
            minHeap.changeKey(minHeap.toArrayList().get(2), 2);
            minHeap.changeKey(minHeap.toArrayList().get(3), 3);
            minHeap.changeKey(minHeap.toArrayList().get(4), 4);
            minHeap.changeKey(minHeap.toArrayList().get(5), 5);
            System.out.println(minHeap.toString());
            
            System.out.println("Extracting min");
            minHeap.extractMin();
            System.out.println(minHeap.toString());
            System.out.println("Deleting a city");
            minHeap.delete(0);
            System.out.println(minHeap.toString());

            City insert = new City(0);
            insert.setMinCost(0);
            System.out.println("Inserting 0");
            minHeap.insertNode(insert);
            System.out.println(minHeap.toString());
            City insert2 = new City(1);
            insert2.setMinCost(4);
            minHeap.insertNode(insert2);
            System.out.println("Inserting 1 wtih cost 4");
            System.out.println(minHeap.toString());
            
            System.out.println("Changing Key");
            minHeap.changeKey(insert2, 1);
            System.out.println(minHeap.toString());
            
            System.out.println("Minimum is " + minHeap.findMin().getMinCost());
        }

        if (testDijkstra) {
            // test out Program2.java here!
            System.out.println("Given graph: ");
            System.out.println(testProgram2);
            System.out.println("Cost of shortest path from start to dest: \n" +
                    testProgram2.findCheapestPathPrice(cities.get(0), cities.get(3)));

            System.out.print("The shortest path from start to dest: \n");
            for (City c : testProgram2.findCheapestPath(cities.get(0), cities.get(3))) {
                System.out.print(c.getCityName() + " -> ");
            }
            System.out.print("(done)\n");
        }
    }

    private static class NeighborWeightTuple {
        public Integer neighborID;
        public Integer weight;

        NeighborWeightTuple(Integer neighborID, Integer weight) {
            this.neighborID = neighborID;
            this.weight = weight;
        }
    }

}
