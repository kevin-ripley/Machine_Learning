package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class PreProcess {

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
    public ArrayList<Node> stratify(String file, ArrayList<Node> d) {
        switch (file) {
            case "iris.data.txt": {
                return stratIris(d);
            }
            case "house-votes-84.data.txt": {
                return stratHouse(d);
            }
            case "glass.data.txt": {
                return stratGlass(d);
            }
            case "soybean-small.data.txt": {
                return stratSoy(d);
            }
            case "breast-cancer-wisconsin.data.txt": {
                return stratBCW(d);
            }
            default:
                break;
        }
        return null;
    }

    /**
     * Iris has 3 classifications, the sets will be sampled from accordingly
     *
     * @param d unstratified dataset of iris data
     * @return stratified dataset of iris data
     */
    private ArrayList<Node> stratIris(ArrayList<Node> d) {
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

        // clear and reform d from the two halves
        d.clear();
        first.addAll(second);
        d.addAll(first);
        return d;
    }

    /**
     *
     * @param d
     * @return
     */
    private ArrayList<Node> stratHouse(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        int rep = 0;
        int dem = 0;
        // iterate though the list of data and add to two lists
        for (int i = 0; i < d.size(); i++) {
            if (d.get(i).toString().contains("republican")) {
                if (rep % 2 == 0) {
                    first.add(d.get(i));
                } else {
                    second.add(d.get(i));
                }
                rep++;
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
        return d;
    }

    private ArrayList<Node> stratGlass(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        for (int i = 0; i < d.size(); i++) {

        }
        d.clear();
        first.addAll(second);
        d.addAll(first);
        return d;
    }

    private ArrayList<Node> stratSoy(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        for (int i = 0; i < d.size(); i++) {

        }
        d.clear();
        first.addAll(second);
        d.addAll(first);
        return d;
    }

    private ArrayList<Node> stratBCW(ArrayList<Node> d) {
        ArrayList<Node> first = new ArrayList<>();
        ArrayList<Node> second = new ArrayList<>();
        for (int i = 0; i < d.size(); i++) {

        }
        d.clear();
        first.addAll(second);
        d.addAll(first);
        return d;
    }

}
