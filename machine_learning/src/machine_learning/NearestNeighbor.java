/**
 * 
 */
package machine_learning;

import java.util.ArrayList;

/**
 * Basic idea for this thing is:
 * get item from the dataset
 * classify that item based on euclidian distance to neighbors for all dimensions
 */
public class NearestNeighbor {
    // instance variables
    private ArrayList<Node> data = new ArrayList<>();
    private ArrayList<String> classList = new ArrayList<>();
    private ArrayList<String> classifications = new ArrayList<>();
    private int kVal;
    private String file = "";

    // constructor args are... idfk
    public NearestNeighbor(ArrayList<Node> d, String f, int k) {
        this.data = d;
        this.file = f;
        this.kVal = k;
    }

    // methods
    private void getClassList(ArrayList<Node> list){

    }



    
}