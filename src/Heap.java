import java.util.ArrayList;

public class Heap {
    private ArrayList<City> minHeap;

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minCost
     * Time Complexity - O(n)
     *
     * @param cities
     */
    
    private int parent(int pos) {
    	return (pos-1)/2;
    }
    private int leftChild(int pos) {
    	return pos*2+1;
    }
    private boolean hasLeftChild(int pos) {
    	if(leftChild(pos) < minHeap.size()) { return true; }
    	return false;
    }
    private int rightChild(int pos) {
    	return pos*2+2;
    }
    private boolean hasRightChild(int pos) {
    	if(rightChild(pos) < minHeap.size()) {return true; }
    	return false;
    }
    
    private void swap(int pos1, int pos2) {
    	City temp;
    	temp = minHeap.get(pos1);
    	minHeap.set(pos1, minHeap.get(pos2));
    	minHeap.set(pos2,  temp);
    }
    
    private void heapifyDown(int index) {
    	int switchIndex = 0;
    	int minCost, leftCost, rightCost;
    	int name, leftName, rightName;
    		
    		if(hasLeftChild(index)) {
    			minCost = minHeap.get(index).getMinCost();
    			leftCost = minHeap.get(leftChild(index)).getMinCost();
    			
    			
    			// if there's only left child, compare only left and swap (including when minCosts are equal)
    			if(!hasRightChild(index)) {
    				if(minCost > leftCost) {
    					switchIndex = leftChild(index);
        			}
    				if(minCost == leftCost) {
    					name = minHeap.get(index).getCityName();
    					leftName = minHeap.get(leftChild(index)).getCityName();
    					if(name > leftName) {
        					switchIndex = leftChild(index);
    					}
    				}
    			}
    			
    			// if there are two children
    			else {
    				rightCost = minHeap.get(rightChild(index)).getMinCost();
    				
    				if(leftCost < rightCost) {
    					if(minCost > leftCost) {
            				switchIndex = leftChild(index);
            			}
        				if(minCost == leftCost &&
        				   minHeap.get(index).getCityName() > minHeap.get(leftChild(index)).getCityName()) {
        					switchIndex = leftChild(index);
        				}
    				}
    				else if(rightCost < leftCost) {
    					if(minCost > rightCost) {
            				switchIndex = rightChild(index);
            			}
        				if(minCost == rightCost &&
        				   minHeap.get(index).getCityName() > minHeap.get(rightChild(index)).getCityName()) {
        					switchIndex = rightChild(index);
        				}
    				}
    				else if(leftCost == rightCost) {
    					if(minCost >= leftCost) {
    						name = minHeap.get(index).getCityName();
    						leftName = minHeap.get(leftChild(index)).getCityName();
    						rightName = minHeap.get(rightChild(index)).getCityName();
    						if(leftName < rightName && leftName < name) {
    							switchIndex = leftChild(index);
    						}
    						if(rightName < leftName && rightName < name) {
    							switchIndex = rightChild(index);
    						}
    					
    					}
    				}
    			}
    			if(switchIndex != 0) {
    				
    				// debug
    				//int minCost1 = minHeap.get(index).getMinCost();
    				//int minCost2 = minHeap.get(switchIndex).getMinCost();
    				//System.out.println("Swapping " + minCost1 + " " + minCost2);
    				//
    				swap(index, switchIndex);
    				
    				// debug
    				//System.out.println("After swapping " + toString())
    				//
;    				heapifyDown(switchIndex);
    			}
    			
    		}
    }
    
    public void buildHeap(ArrayList<City> cities) {
    	
    	minHeap = (ArrayList)cities.clone();
    	
    	if(minHeap.size() != 0 || minHeap.size() != 1) {
    		for(int i = (cities.size()/2) - 1; i >= 0; i--) {
        		heapifyDown(i);
        		
        	}
    	}
    }

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        minHeap.add(in);
        int pos = minHeap.size()-1;
        while(pos != 0 && minHeap.get(pos).getMinCost() <= minHeap.get(parent(pos)).getMinCost()) {
        	if(minHeap.get(pos).getMinCost() < minHeap.get(parent(pos)).getMinCost()) {
        		swap(pos, parent(pos));
            	pos = parent(pos);
        	}
        	else if(minHeap.get(pos).getMinCost() == minHeap.get(parent(pos)).getMinCost()) {
        		if(minHeap.get(pos).getCityName() < minHeap.get(parent(pos)).getCityName()) {
        			swap(pos, parent(pos));
                	pos = parent(pos);
        		}
        		else{
        			break;
				}
        	}
        	
        }
    }

    /**
     * findMin()
     *
     * @return the minimum element of the heap. Must run in constant time.
     */
    public City findMin() {
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        City min = findMin();
        
        swap(0, minHeap.size()-1);
        minHeap.remove(minHeap.size()-1);
        
        heapifyDown(0);
        return min;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        swap(index, minHeap.size()-1);
        minHeap.remove(minHeap.size()-1);
        
        heapifyDown(index);
    }

    /**
     * changeKey(City c, int newCost)
     * Updates and rebalances a heap for City c.
     * Time Complexity - O(log(n))
     *
     * @param c       - the city in the heap that needs to be updated.
     * @param newCost - the new cost of city c in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(City c, int newCost) {
    	minHeap.remove(c);
    	c.setMinCost(newCost);
    	insertNode(c);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getCityName() + " ";
        }
        return output;
    }

    public boolean isEmpty(){
    	return minHeap.isEmpty();
	}

	public boolean contains(City c){
    	for(int i = 0; i < minHeap.size(); i++){
    		if(minHeap.get(i) == c){
    			return true;
			}
		}
    	return false;
	}

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
