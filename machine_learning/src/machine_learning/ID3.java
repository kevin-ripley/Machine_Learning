package machine_learning;

import java.util.ArrayList;

/**
 * FML
 *
 * @author Bryan
 */
public class ID3 {

    // File name
    String file = "";
    // Data to be supplied in the constructor
    ArrayList<Node> data;
    // Test and training sets
    ArrayList<Node> test = new ArrayList<>();
    ArrayList<Node> train = new ArrayList<>();
    // Set of attributes for gain evaluation
    ArrayList<String> attributes = new ArrayList<>();
    // Set of classes
    ArrayList<String> classList = new ArrayList<>();
    

    // constructor
    public ID3(ArrayList<Node> d, String f, ArrayList<String> c) {
        this.data = d;
        this.file = f;
        this.classList = c;
    }

    /**
     * The fiveXTwo Method runs a test using 5x2 Fold Cross Validation on the
     * dataset held by the current instance of ID3.
     */
    public void fiveXTwo() {
        // repeat the two fold test 5 times
        for (int i = 0; i < 5; i++) {
            // run once
            splitData();
            runID3();
            testTree();
            // swap sets and run again
            switchSets();
            runID3();
            testTree();
        }
    }

    /**
     * The splitData method splits the data into a test and training set for 5x2
     * Fold Cross Validation
     */
    private void splitData() {
        for (int i = 0; i < data.size(); i++) {
            if (i < data.size() / 2) {
                test.add(i, data.get(i));
            } else {
                train.add(i, data.get(i));
            }
        }
    }

    /**
     * switchSets flips the training and test sets (basically just swaps names
     * but there's not a really easy way to do that)
     */
    private void switchSets() {
        ArrayList<Node> temp = new ArrayList<>();
        temp.addAll(this.test);
        test.clear();
        test.addAll(this.train);
        train.clear();
        train.addAll(temp);
        temp.clear();
    }

    private void runID3() {
        // this is where you need to put the algorithm
        // it's supposed to be recursive, so good luck with that.
        // 
        // attributes list is empty => return empty tree
        // else compute the entropy for all remaining attributes
        // 
        // 
         
    }

    private void testTree() {

    }
    
    public void printlist() {
        for (int i = 0; i < classList.size(); i++) {
            System.out.println(classList.get(i));
        }
    }
    

}
