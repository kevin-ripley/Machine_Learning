package machine_learning;

import java.util.ArrayList;

/**
 * FML
 *
 * @author Bryan
 */
public class ID3 {

    // instance variables
    String file = "";
    ArrayList<Node> data;
    ArrayList<Node> test = new ArrayList<>();
    ArrayList<Node> train = new ArrayList<>();

    // constructor
    public ID3(ArrayList<Node> d, String f) {
        this.data = d;
        this.file = f;
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
            buildTree();
            testTree();
            // swap sets and run again
            switchSets();
            buildTree();
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

    private void buildTree() {

    }

    private void testTree() {

    }

}
