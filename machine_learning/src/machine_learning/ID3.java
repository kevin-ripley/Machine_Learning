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
        private ID3Node(int  n) {
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
        // compute gain for the root node
        int n = getGainMax(data, attributes);
        // create tree root with max gain of dataset
        tree = new ID3Node(n);
        // pass index of most gain with dataset and index list to build tree
        buildTree(n, data, attributes, tree);
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
        }
        // out of attributes make leaf with most common class at node as class string
        if (attList.isEmpty()) {
            tree.setLeafClass("");
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
            // attribute value...
            buildTree(n, splitSet(d, node.getChildren().get(i).getAttVal(), n), attList, node);
        }
        
    }

    private int getGainMax(ArrayList<Node> d, ArrayList<Integer> attList) {
        return 5;
    }

    private boolean allSameClass(ArrayList<Node> d) {
        
        switch (file) {
            case "breast-cancer-wisconsin.data.txt": {
                // search for last index = 2 or 4
            }
            case "glass.data.txt": {
                // search for last index = 1 - 7
            }
            default: {
                // search for string in class list
            }
        }
        return true;
    }
    
    /**
     * splitSet takes a data set, the attribute value, and the attribute index
     * and splits the set into a new set containing only data with the attribute
     * index == the attribute value
     * 
     * @param d dataset to be split by attribute value, index
     * @param s string containing the attribute value
     * @param n index of the attribute
     * @return 
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
