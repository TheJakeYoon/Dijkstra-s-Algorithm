import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.*;

public class Program2 {
    private ArrayList<City> cities;     //this is a list of all cities, populated by Driver class.
    private Heap minHeap;

    // feel free to add any fields you'd like, but don't delete anything that's already here
    private int numCities;
    
    public Program2(int numCities) {
    	this.numCities = numCities;
        minHeap = new Heap();
        cities = new ArrayList<City>();
    }

    /**
     * findCheapestPathPrice(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return the minimum cost possible to get from start to dest.
     * Assume a path always exists.
     */
    public int findCheapestPathPrice(City start, City dest) {

        // init
    	ArrayList<Integer> cost = new ArrayList<>();
    	for(int i = 0; i < numCities; i++) {
    		cost.add(Integer.MAX_VALUE);
    	}
    	cost.set(start.getCityName(), 0);
    	
    	Heap unvisited = new Heap();
        unvisited.buildHeap(cities);
        unvisited.changeKey(start, 0);

    	ArrayList<City> neighbors;
    	ArrayList<Integer> weights;
    	
    	City currentCity;
    	int pathPrice;

    	// algorithm
    	while(!unvisited.isEmpty()) {
        	currentCity = unvisited.extractMin();
    		neighbors = currentCity.getNeighbors();
    		weights = currentCity.getWeights();
    		
    		for(int i = 0; i < neighbors.size(); i++) {
    			pathPrice = currentCity.getMinCost() + weights.get(i);
    			if(pathPrice < cost.get(neighbors.get(i).getCityName())) {
    				cost.set(neighbors.get(i).getCityName(), pathPrice);
    				if(unvisited.contains(neighbors.get(i))){
                        unvisited.changeKey(neighbors.get(i), pathPrice);
                    }
    			}
    		}
    	}
        return cost.get(dest.getCityName());
    }

    /**
     * findCheapestPath(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return an ArrayList of nodes representing a minimum-cost path on the graph from start to dest.
     * Assume a path always exists.
     */
    public ArrayList<City> findPath(City start, City dest){
        ArrayList<City> path = new ArrayList<>();
        path.add(start);
        path.add(dest);

        return path;
    }

    public ArrayList<City> findCheapestPath(City start, City dest) {

        // init
        ArrayList<City> path = new ArrayList<>();
        // if City 2 is in index 0, City 0 has City 2 has previous City.
        ArrayList<City> previousCity = new ArrayList<>();
        for(int i = 0; i < numCities; i++){
            previousCity.add(null);
        }
        previousCity.set(start.getCityName(), start);
        ArrayList<Integer> cost = new ArrayList<>();
        for(int i = 0; i < numCities; i++) {
            cost.add(Integer.MAX_VALUE);
        }
        cost.set(start.getCityName(), 0);

        Heap unvisited = new Heap();
        unvisited.buildHeap(cities);

        ArrayList<City> neighbors;
        ArrayList<Integer> weights;

        City currentCity;
        int pathPrice;

        // dijkstra algorithm
        while(!unvisited.isEmpty()) {
            currentCity = unvisited.extractMin();
            neighbors = currentCity.getNeighbors();
            weights = currentCity.getWeights();

            for(int i = 0; i < neighbors.size(); i++) {
                pathPrice = currentCity.getMinCost() + weights.get(i);
                if(pathPrice < cost.get(neighbors.get(i).getCityName())) {
                    cost.set(neighbors.get(i).getCityName(), pathPrice);
                    previousCity.set(neighbors.get(i).getCityName(), currentCity);
                    if(unvisited.contains(neighbors.get(i))){
                        unvisited.changeKey(neighbors.get(i), pathPrice);
                    }
                }
            }
        }

        // get path list
        int index = dest.getCityName();
        City prev = previousCity.get(index);
        path.add(dest);

        while(start != prev){
            path.add(prev);
            index = previousCity.get(index).getCityName();
            prev = previousCity.get(index);
        }
        path.add(start);
        // reverse since we started at dest but we want it to end at dest
        Collections.reverse(path);
        return path;
    }

    private void getPathFromStart(){

    }
    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (City v : cities) {
            boolean first = true;
            o += "City ";
            o += v.getCityName();
            o += " has neighbors: ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getCityName() : ", " + n.getCityName();
                first = false;
            }
            first = true;
            o += " with weights ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<City> getAllCities() {
        return cities;
    }

    //used by Driver class to populate each Node with correct neighbors and corresponding weights
    public void setEdge(City curr, City neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    //This is used by Driver.java and sets vertices to reference an ArrayList of all nodes.
    public void setAllNodesArray(ArrayList<City> x) {
        cities = x;
    }
}
