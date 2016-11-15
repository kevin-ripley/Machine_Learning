
package machine_learning;

import java.util.ArrayList;

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
            // A ? in this case does not mean unidentified informaiton, just that there was not a Y or N. We will put these values into class o for other
            case "house-votes-84.data.txt": {
                current.set(i, "o");
                break;
            }
            case "iris.data.txt": {
                //do nothing, there is no missing data according to iris.names.txt
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
                current.set(j, rounded+"");
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

}
