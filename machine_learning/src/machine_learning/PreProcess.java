package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class PreProcess {
    // class lists
    private String[] houseClass = {"republican", "democrat"};
    private String[] irisClass = {"Iris-setosa", "Iris-versicolor", "Iris-virginica"};
    private String[] bcwClass = {"2", "4"};
    private String[] soyClass = {"D1", "D2", "D3", "D4"};
    private String[] glassClass = {"1", "2", "3", "4", "5", "6", "7"};
    
    public ArrayList<Node> missingValues(ArrayList<Node> data, String file) {

        // Another nexted for loop to handle all missing values... Gross I know, but work with what ya got. 
        // Improvement would be to not enter these loops on data sets with no missing values known. Can't assume this is the case everytime though
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> current = data.get(i).inputData;
            for (int j = 0; j < current.size(); j++) {
                // if the value is missing, we need to add it. We also need the enter escape value for the last value because Java
                if (current.get(j).equals("?") || current.get(j).equals("?\r")) {
                    addValueBasedOnFile(file, current, j);
                }
            }
        }

        return data;
    }

    public void addValueBasedOnFile(String file, ArrayList<String> current, int i) {
        switch (file) {
            // A ? in this case does not mean unidentified information, just that there was not a Y or N. We will put these values into class o for other
            case "house-votes-84.data.txt": {
                current.set(i, "o");
                break;
            }
            case "iris.data.txt": {
                //do nothing, there is no missing data according to iris.names.txt
                break;
            }
            case "breast-cancer-wisconsin.data.txt": {
                // do something.
                break;
            }
            default: {
                System.out.println("ERROR");
            }

        }
    }

    //Will round continuous double values to the nearest int. These ints will be used as bins, or discrete values.
    public void roundedBins(ArrayList<Node> data) {
        for (int i = 0; i < data.size(); i++) {
            ArrayList<String> current = data.get(i).inputData;
            //we dont want the last value because we know that is the Class provided by the data
            for (int j = 0; j < current.size() - 1; j++) {
                long rounded = Math.round(Double.parseDouble(current.get(j)));
                current.set(j, rounded + "");
            }
        }
    }

    public void discretize(String file, ArrayList<Node> data) {
        switch (file) {
            case "house-votes-84.data.txt": {
                // do nothing, the values are already discrete
                break;
            }
            case "iris.data.txt": {
                //the values are continuous, so we need to discretize them.
                //to this we will round the double to the nearest int. The int values will become our new bins.
                //Note this may not be the best approach, due to ability to skew and possible high cardinality, but it will work for now.
                roundedBins(data);
                break;
            }
            default: {
                System.out.println("ERROR");
            }
        }
    }

    // print the current dataSet
    public void print(ArrayList<Node> data) {

        for (int i = 0; i < data.size(); i++) {
            System.out.println("");
            for (int j = 0; j < data.get(i).inputData.size(); j++) {
                System.out.print(data.get(i).inputData.get(j));
            }
        }
    }
    
    
    /**
     * getClassList returns an array list of classes based on the file type provided as an argument
     * @param file name of the dataset file
     * @return arraylist of classes for the file
     */
    public ArrayList<String> getClassList(String file) {
        ArrayList<String> tempAList = new ArrayList<>();
        String[] tempArray;
        // because java is fun, can't assign strings to an array without an .add line per string
        switch (file) {
            case "iris.data.txt": {
                tempArray = irisClass;
                break;
            }
            case "house-votes-84.data.txt": {
                tempArray = houseClass;
                break;
            }
            case "glass.data.txt": {
                tempArray = glassClass;
                break;
            }
            case "soybean-small.data.txt": {
                tempArray = soyClass;
                break;
            }
            default: {
                tempArray = bcwClass;
                break;
            }
        }
        for (int i = 0; i < tempArray.length; i++) {
            tempAList.add(tempArray[i]);
        }
        return tempAList;
    }
    
    
    /**
     * Shuffle randomly rearranges a dataset
     * @param d dataset to be shuffled
     */
    public void shuffle(ArrayList<Node> d) {
        long seed = System.nanoTime();
        Collections.shuffle(d, new Random(seed));
    }

    /**
     * The stratify method stratifies the data set into halves with
     * approximately equal class distribution. Due to time constraints, this
     * only works for the given datasets in the assignment, and is meant to
     * model 5x2 cross validation.
     *
     * @param file filename to be stratified
     * @param d dataset as ArrayList<Node>
     * @return Stratified list where index 0 to size/2 and size/2 to size have
     * approximately the same class distribution
     */
    public void stratify(String file, ArrayList<Node> d) {
        switch (file) {
            case "iris.data.txt": {
                stratIris(d);
                break;
            }
            case "house-votes-84.data.txt": {
                stratHouse(d);
                break;
            }
            case "glass.data.txt": {
                stratGlass(d);
                break;
            }
            case "soybean-small.data.txt": {
                stratSoy(d);
                break;
            }
            case "breast-cancer-wisconsin.data.txt": {
                stratBCW(d);
                break;
            }
            default:
                break;
        }
    }

    /**
     * Iris has 3 classifications, the sets will be stratified accordingly
     *
     * @param d unstratified dataset of iris data
     */
    private void stratIris(ArrayList<Node> d) {
        // set up lists for each class
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        int seto = 0;
        int vers = 0;
        int virg = 0;

        // iterate through dataset and add matching classes to respective list
        for (int i = 0; i < d.size(); i++) {
            if (d.get(i).getValue().toString().contains("Iris-setosa")) {
                if (seto % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                seto++;
            } else if (d.get(i).getValue().toString().contains("Iris-versicolor")) {
                if (vers % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                vers++;
            } else {
                if (virg % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                virg++;
            }
        }

       // clear out dataset pointing to and rebuild with weighted halves
        d.clear();
        first.addAll(second);
        d.addAll(first);
    }

    /**
     * House data is of type republican or democrat, stratify accordingly
     * @param d the dataset to be sorted.
     */
    private void stratHouse(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        int rep = 0;
        int dem = 0;
        // iterate though the list of data and add to two lists
        for (int i = 0; i < d.size(); i++) {
            // divy republicans
            if (d.get(i).toString().contains("republican")) {
                if (rep % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                rep++;
            // divy democrats
            } else {
                if (dem % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                dem++;
            }

        }

        d.clear();
        first.addAll(second);
        d.addAll(first);
    }

    /**
     * Glass has 6 classes (7 - 1 not used), switch statement to stratify 
     * @param d the dataset to be sorted
     */
    private void stratGlass(ArrayList<Node> d) {
        // two halves again
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        // class c for each used up to 6, not identical to actual dataset classes
        int c0 = 0;
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        int c5 = 0;
        String temp; 
        
        // big ugly switch statement
        for (int i = 0; i < d.size(); i++) {
            temp = d.get(i).getValue().toString().substring(
                    d.get(i).getValue().toString().length() - 1);
            switch (d.get(i).getValue().toString()) {
                case "1": {
                    if (c0 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    c0++;
                    break;
                }
                case "2": {
                    if (c1 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    c1++;
                    break;
                }
                case "3": {
                    if (c2 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    c2++;
                    break;
                }
                case "5": {
                    if (c3 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    c3++;
                    break;
                }
                case "6": {
                    if (c4 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    c4++;
                    break;
                }
                default: {
                    if (c5 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    c5++;
                }
            }
        }
 
        d.clear();
        first.addAll(second);
        d.addAll(first);
    }

    /**
     * Soybean-small data has 4 types, stratify accordingly
     * @param d dataset to be stratified
     */
    private void stratSoy(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        int d1 = 0;
        int d2 = 0;
        int d3 = 0;
        int d4 = 0;

        for (int i = 0; i < d.size(); i++) {
            switch (d.get(i).getValue().toString()) {
                case "D1": {
                    if (d1 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    d1++;
                    break;
                }
                case "D2": {
                    if (d2 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    d2++;
                    break;
                }
                case "D3": {
                    if (d3 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    d3++;
                    break;
                }
                default: {
                    if (d4 % 2 == 0) {
                        first.add(d.get(i));
                    } else {
                        second.add(d.get(i));
                    }
                    d4++;
                    break;
                }

            }
            d.clear();
            first.addAll(second);
            d.addAll(first);
        }
    }

    /**
     * Breast Cancer Data has 2 classes, stratify accordingly
     * @param d the dataset to be stratified
     */
    private void stratBCW(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        String temp;
        int x1 = 0;
        int x2 = 0;
        // list assignment
        for (int i = 0; i < d.size(); i++) {
            temp = d.get(i).getValue().toString().substring(
                    d.get(i).getValue().toString().length() - 1);
            if (temp == "2") {
                if (x1 % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                x1++;
            } else {
                if (x2 % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                x2++;
            }
        }
        d.clear();
        first.addAll(second);
        d.addAll(first);
    }

}
