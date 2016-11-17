package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;

public class NaiveBayes {

    // TODO: need to handle zero probabilities, then use bayes formulat to get max probability.
    ArrayList<Node> data = new ArrayList<>();
    ArrayList<String> classList = new ArrayList<>();
    ArrayList<Double> classProbability = new ArrayList<>();
    ArrayList<ArrayList<Double[]>> totalClassProb = new ArrayList<>();

    String file = "";
    double[] classCount;

    public NaiveBayes(ArrayList<Node> d, String f) {
        this.data = d;
        this.file = f;
    }

    // Build the class list from the data.
    public void buildClassList(int index) {
        for (int i = 0; i < data.size(); i++) {
            if (index < data.get(i).inputData.size() && !classList.contains(data.get(i).inputData.get(index))) {
                classList.add(data.get(i).inputData.get(index));
            }
        }
        this.getClassProbabilities();
    }

    // Find the probability of each class occuring
    public void getClassProbabilities() {
        int size = this.data.get(0).inputData.size() - 1;
        this.classCount = new double[this.classList.size()];
        double counter;
        // currently working with iris data, probably going to have to change for house
        for (int i = 0; i < this.classList.size(); i++) {
            counter = 0;
            for (int j = 0; j < this.data.size(); j++) {
                if (this.data.get(j).inputData.size() > 1 && this.data.get(j).inputData.get(size).equals(this.classList.get(i))) {
                    counter++;
                }
            }
            // We need to subtract one for that missing character at the end of file..... :(
            this.classProbability.add(counter / (this.data.size() - 1));
            this.classCount[i] = counter;
        }
    }

    public void printClassList() {
        for (int i = 0; i < this.classList.size(); i++) {
            System.out.println(classList.get(i));
        }
    }

    public void buildFrequencies(Integer possibleValues, Integer numAttributes) {
        int classIndex = this.data.get(0).inputData.size() - 1;

        // add an array for each class to contain probabilities
        for (int c = 0; c < this.classList.size(); c++) {
            ArrayList<Double[]> attributeProbability = new ArrayList<>();
            for (int i = 0; i < numAttributes; i++) {
                attributeProbability.add(new Double[possibleValues]);
                Arrays.fill(attributeProbability.get(i), (double) 0);
            }
            totalClassProb.add(attributeProbability);
        }

        // find probabilites of attribute occuring given class, this is also EXTREMELY GROSS. Triple nested? kick me in the face!
        for (int t = 0; t < this.classList.size(); t++) {
            for (int i = 0; i < classIndex; i++) {
                for (int j = 0; j < this.data.size(); j++) {
                    // Another case of checking for the eof character
                    if (this.data.get(j).inputData.size() > 1 && this.data.get(j).inputData.get(classIndex).equals(this.classList.get(t))) {
                        totalClassProb.get(t).get(i)[Integer.parseInt(this.data.get(j).inputData.get(i))] += 1;
                        //attributeProbability.get(i)[Integer.parseInt(this.data.get(j).inputData.get(i))] += 1;
                    }
                }
            }
        }

        // For debugging, printing the probability of each class occuring given they type for each attribute. This was a pain.
        for (int c = 0; c < totalClassProb.size(); c++) {
            for (int i = 0; i < totalClassProb.get(c).size(); i++) {
                for (int j = 0; j < totalClassProb.get(c).get(i).length; j++) {
                    System.out.print(" " + totalClassProb.get(c).get(i)[j] / this.classCount[c] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    // We need to get the classlist for testing. Based on the text file the class is either the first or last value
    public void setNBData() {
        switch (this.file) {
            case "iris.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                // There are 9 possible values: 0-8, and 4 different attributes
                buildFrequencies(9, 4);
                break;
            }

        }
    }

}
