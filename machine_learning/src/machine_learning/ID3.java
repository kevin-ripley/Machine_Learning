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
    ArrayList<Integer> attributes = new ArrayList<>();
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
        // build the attribute index list
        
        // repeat the two fold test 5 times
        for (int i = 0; i < 5; i++) {
            // run once
            buildAttributes();
            splitData();
            runID3();
            testTree();
            // swap sets and run again
            buildAttributes();
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

    private void runID3(int attribute) {
        // this is where you need to put the algorithm
        // it's supposed to be recursive, so good luck with that.
        // 
        // attributes list is empty => return empty tree
        if(attributes.isEmpty()) 
            return;
        
        // A <- choose best attribute
        // Assign A as decision attribute for Node
        // for each value of A create a descendent of node
        // sort training examples to leaves
        // if examples perfectly classified stop (eh?)
        // else iterate over the leaves until done

    }
    

    private void testTree() {

    }
    
    public void printlist() {
        for (int i = 0; i < classList.size(); i++) {
            System.out.println(classList.get(i));
        }
    }
    
    
    /**
     * buildAttributes is a quick and ugly way to separate the attributes from
     * classes and id's.
     */
    private void buildAttributes() {
        int[] atts;
        int[] def = {0};
        // list all indexes that are attributes for each dataset
        int[] houseAtt = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        int[] irisAtt = {0,1,2,3};
        int[] glassAtt = {1,2,3,4,5,6,7,8,9};
        int[] soyAtt = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,
                        20,21,22,23,24,25,26,27,28,29,30,31,32,33,34};
        int[] bcwAtt = {1,2,3,4,5,6,7,8,9};
        
        // assign the attribute index list based on file name
        switch (this.file) {
            case "house-votes-84.data.txt": {
                 atts = houseAtt;
            }
            case "iris.data.txt": {
                atts = irisAtt;
            }
            case "glass.data.txt": {
                atts = glassAtt;
            }
            case "soybean-small.data.txt": {
                atts = soyAtt;
            }
            case "breast-cancer-wisonsin.data.txt": {
                atts = bcwAtt;
            }
            default:
                atts = def;
        }
        // clear old list incase something got left behind (CYA)
        attributes.clear();
        for (int i = 0; i < atts.length; i++) {
            attributes.add(atts[i]);
        }
        
    }
    

}
