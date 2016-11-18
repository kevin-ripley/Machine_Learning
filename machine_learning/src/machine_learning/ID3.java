package machine_learning;

import java.util.ArrayList;

/**
 * FML
 *
 * @author Bryan
 */
public class ID3 {

    // File name
    private String file = "";
    // Data to be supplied in the constructor
    private ArrayList<Node> data;
    // Test and training sets
    private ArrayList<Node> test = new ArrayList<>();
    private ArrayList<Node> train = new ArrayList<>();
    private ArrayList<Node> train2;
    // Set of attributes for gain evaluation
    private ArrayList<Integer> attributes = new ArrayList<>();
    // Set of classes
    private ArrayList<String> classList = new ArrayList<>();
    private ID3Node tree;

    /**
     * Nested class id3Node is the haphazard data structure for holding the tree
     * it is not meant to be optimized for time or storage complexity it must
     * simply, work.
     */
    private class ID3Node {

        // root node pointed to by all nodes (understandably inefficient)
        private ID3Node root;
        // parent node of the current child
        private ID3Node parent;
        private ArrayList<ID3Node> children = new ArrayList<>();
        private String attVal;
        private int index;
        // class variable for leaves
        private String c;

        //
        // constructor for root = null
        private ID3Node(int n) {
            this.root = this;
            this.index = n;
        }

        // constructor for subsequent root
        private ID3Node(ID3Node parent, String s) {
            this.parent = parent;
            this.attVal = s;
        }

        // get the root of the node
        private ID3Node getRoot() {
            return root;
        }

        /**
         * add child to the current node that points to this instance as parent
         *
         * @param i integer to add to the decision list
         */
        private void addChild(String s) {
            this.children.add(new ID3Node(this, s));
        }

        // get a child at index n
        private ID3Node getChild(String s) {
            return children.get(children.indexOf(s));
        }

        // get class string, null if not leaf
        private String getLeafClass() {
            return c;
        }

        // set the class string of a leaf
        private void setLeafClass(String s) {
            this.c = s;
        }

        // get the index of the attribute the node splits
        private int getIndex() {
            return index;
        }

        // set index of attribute over which the node splits
        private void setIndex(int n) {
            this.index = n;
        }

        // get children list
        private ArrayList<ID3Node> getChildren() {
            return this.children;
        }

        // get attribute value
        private String getAttVal() {
            return this.attVal;
        }
    }

    // ID3 constructor
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
        train2 = new ArrayList<>(test);
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

    /**
     * runID3 is the entry point to the application. It creates the root node and
     * starts the recursive calls to build the tree.
     */
    private void runID3() {
        // compute gain for the root node
        int n = getGainMax(data, attributes);
        // create tree root with max gain of dataset
        tree = new ID3Node(n);
        
        
        // the code below is just an ugly way to get the recursion going
        
        // remove index from list of attributes
        attributes.remove(n);

        // hold all possible values of selected attribute in this array list
        // assuming discrete otherwise, welp, cya later
        ArrayList<String> values = new ArrayList<>();

        // iterate over remaining data to find all possible values of attribute
        // make a list of attribute values
        for (int i = 0; i < data.size(); i++) {
            // if the value isn't in the list add it
            if (!values.contains(data.get(i).inputData.get(n))) {
                values.add(data.get(i).inputData.get(n));
            }
        }

        // create a child for each value in values
        for (int i = 0; i < values.size(); i++) {
            tree.addChild(values.get(i));
        }

        // for all the children recurse!
        for (int i = 0; i < tree.getChildren().size(); i++) {
            // split the data for each time through the recursion based on 
            // attribute value associated with each child...
            buildTree(n, splitSet(data, tree.getChildren().get(i).getAttVal(), n),
                    attributes, tree.getChildren().get(i));
        }
    }

    /**
     * Build Tree method is recursive and assembles the tree using the training
     * set taken as parameter d.
     *
     * @param index the index of the attribute over which to split
     * @param d data set (training set)
     * @param att list of attribute indexes on which to split
     */
    private void buildTree(int index, ArrayList<Node> d, ArrayList<Integer> attList, ID3Node node) {
        // if only one class remains, don't continue
        if (allSameClass(d)) {
            // have leaf, no kids, set string to attribute value
            node.setLeafClass(getClassString(d));
        }
        // out of attributes make leaf with most common class at node as class string
        if (attList.isEmpty()) {
            tree.setLeafClass(getMostCommon(d));
            return;
        }
        // otherwise we have more work to do

        // set index of current node
        this.tree.setIndex(index);

        // find maximum information gain over remaining attributes
        int n = getGainMax(d, attList);

        // remove index from list of attributes
        attList.remove(n);

        // hold all possible values of selected attribute in this array list
        // assuming discrete otherwise, welp, cya later
        ArrayList<String> values = new ArrayList<>();

        // iterate over remaining data to find all possible values of attribute
        // make a list of attribute values
        for (int i = 0; i < d.size(); i++) {
            // if the value isn't in the list add it
            if (!values.contains(d.get(i).inputData.get(n))) {
                values.add(d.get(i).inputData.get(n));
            }
        }

        // create a child for each value in values
        for (int i = 0; i < values.size(); i++) {
            node.addChild(values.get(i));
        }

        // for all the children recurse!
        for (int i = 0; i < node.getChildren().size(); i++) {
            // split the data for each time through the recursion based on 
            // attribute value associated with each child...
            buildTree(n, splitSet(d, node.getChildren().get(i).getAttVal(), n),
                    attList, node.getChildren().get(i));
        }

    }

    /**
     * getGainMax takes a dataset and a list of attribute indexes and returns
     * the index of the attribute that maximizes the information gain ratio.
     * 
     * @param d dataset to determine most gain
     * @param attList list of attribute indexes
     * @return integer value representing the index of most gain
     */
    private int getGainMax(ArrayList<Node> d, ArrayList<Integer> attList) {
        double entropy;
        double ig;
        double iv;
        double temp = 0;
        
        // For each attribute
        if (attList.size() == 1) {
            return attList.get(0);
        } else {
            for (int i = 0; i < attList.size(); i++) {
                // compute I(class)
                for (int j = 0; j < d.size(); j++) {
                    d.get(j).inputData.get(i);
                }
                // compute entropy of the attribute
                
            }            
        }
        
        

        // compute information gain
        
        // compute intrinsic value
        
        // divide information gain by intrinsic value
        
        // return it
        return 5;
    }

    /**
     * allSameClass checks a dataset to see if all entries are of the same class
     *
     * @param d dataset to check
     * @return true if all same class, else false
     */
    private boolean allSameClass(ArrayList<Node> d) {
        int count = 0;
        switch (file) {
            case "glass.data.txt":
            case "breast-cancer-wisconsin.data.txt": {
                String compare = d.get(0).inputData.get(d.get(0).inputData.size() - 1);
                // iterate through the dataset remaining
                for (int i = 0; i < d.size(); i++) {
                    if (d.get(i).inputData.get(d.get(i).inputData.size() - 1) != compare) {
                        return false;
                    }
                }
                return true;
            }
            default: {
                for (int i = 0; i < d.size(); i++) {
                    for (int j = 0; j < classList.size(); j++) {
                        if (d.get(i).getValue().toString().contains(classList.get(j))) {
                            count++;
                        }
                    }
                }
                if (d.size() == count) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /**
     * Get Most Common returns the most common class from a dataset
     *
     * @param d dataset to be analyzed
     * @return String of the most common class
     */
    private String getMostCommon(ArrayList<Node> d) {
        ArrayList<Integer> counts = new ArrayList<>();
        int k = 0;
        // build a list the same size as classes to hold counts
        for (int i = 0; i < classList.size(); i++) {
            counts.add(0);
        }
        // count up the classes in the data set
        for (int i = 0; i < d.size(); i++) {
            switch (file) {
                case "iris.data.txt":
                case "glass.data.txt": {
                    
                }
                default:
                    for (int j = 0; j < classList.size(); j++) {
                        if (d.get(i).getValue().toString().contains(classList.get(j))) {
                            k = counts.get(i);
                            counts.set(i, k);
                        }
                    }
            }
        }
        
        // match the index of the highest count to the index of the class list and return
        k = counts.get(0);
        for (int i = 0; i < counts.size(); i ++) {
            // if there is a greater value in the counts list, return it
            if (k < counts.get(i)) {
                k = counts.get(i);
            }
        }
        
        // return the String at index of k -- remember the lists mirror eachother
        return classList.get(counts.indexOf(k));

    }

    /**
     * splitSet takes a data set, the attribute value, and the attribute index
     * and splits the set into a new set containing only data with the attribute
     * index == the attribute value
     *
     * @param d dataset to be split by attribute value, index
     * @param s string containing the attribute value
     * @param n index of the attribute
     * @return a set with the selected attribute having the same value across all members
     */
    private ArrayList<Node> splitSet(ArrayList<Node> d, String s, int n) {
        ArrayList<Node> temp = new ArrayList<>();
        for (int i = 0; i < d.size(); i++) {
            if (d.get(i).inputData.get(n) == s) {
                temp.add(d.get(i));
            }
        }
        return temp;
    }

    private void testTree() {
        // ran out of time... but this would look something like
        for (int i = 0; i < test.size(); i++) {
            // get index to split on
            // while node has children find child to compare at next level
            // if node.getChildren() == null assign class
            // compare class to actual class
            // if same assign 1 for ith item in the test set
            // if class != actual class assign 0 to ith item in the test set
        }
    }

    public void printlist() {
        for (int i = 0; i < classList.size(); i++) {
            System.out.println(classList.get(i));
        }
    }

    /**
     * PruneTree Prunes the date from a given tree. Takes the root node as
     * parameter.
     *
     * @param node root node of the tree that must be pruned.
     */
    private void pruneTree(ID3Node node) {

    }

    /**
     * getClassString returns the string to be associated with a leaf
     *
     * @param d data set of the same class type from which to pull the class
     * @return String value for the root
     */
    private String getClassString(ArrayList<Node> d) {
        switch (this.file) {
            case "house-votes-84.data.txt":
            case "iris.data.txt": {
                return d.get(0).inputData.get(d.get(0).inputData.size() - 1);
            }
            default:
                for (int i = 0; i < classList.size(); i++) {
                    if (d.get(0).getValue().toString().contains(classList.get(i))) {
                        return classList.get(i);
                    }
                }
        }
        // if the above failed, something went wrong. Return a random class so the
        // program still executes
        return classList.get((int) (Math.random() * classList.size()));
    }

    /**
     * buildAttributes is a quick and ugly way to separate the attributes from
     * classes and id's.
     */
    private void buildAttributes() {
        int[] atts;
        int[] def = {0};
        // list all indexes that are attributes for each dataset
        int[] houseAtt = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int[] irisAtt = {0, 1, 2, 3};
        int[] glassAtt = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] soyAtt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34};
        int[] bcwAtt = {1, 2, 3, 4, 5, 6, 7, 8, 9};

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
