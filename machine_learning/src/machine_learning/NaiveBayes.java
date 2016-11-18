package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NaiveBayes {

    // TODO: need to handle zero probabilities, then use bayes formulat to get max probability.
    List<Node> data = new ArrayList<>();
    List<String> classList = new ArrayList<>();
    List<Double> classProbability = new ArrayList<>();
    List<ArrayList<Double[]>> totalClassProb = new ArrayList<>();
    int[] maxClass;

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
        this.getClassProbabilities(false);
    }

    // Build the class list from the data.
    public void buildClassListHouse(int index) {
        // classes are repub and demo in the beggining of array
        for (int i = 0; i < data.size(); i++) {
            if (index < data.get(i).inputData.size() && !classList.contains(data.get(i).inputData.get(0))) {
                classList.add(data.get(i).inputData.get(0));
            }
        }
        this.getClassProbabilities(true);
    }

    // Find the probability of each class occuring
    public void getClassProbabilities(boolean t) {
        int size = 0;
        if (!t) {
            size = this.data.get(0).inputData.size() - 1;
        }
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

    public void buildFrequencies(Integer possibleValues, Integer numAttributes, boolean house, List<Node> d) {
        int classIndex = 0;
        int housei = 1;
        if (house == false) {
            classIndex = this.data.get(0).inputData.size() - 1;
            housei = 0;
        }

        //System.out.println(this.classList.size());
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
            for (int i = housei; i < this.data.get(0).inputData.size() - 1; i++) {

                for (int j = 0; j < d.size(); j++) {
                    // Another case of checking for the eof character
                    if (d.get(j).inputData.size() > 1 && d.get(j).inputData.get(classIndex).equals(this.classList.get(t))) {
                        totalClassProb.get(t).get(i)[Integer.parseInt(d.get(j).inputData.get(i))] += 1;
                    }
                }
            }
        }

        // For debugging, printing the probability of each class occuring given they type for each attribute. This was a pain.
//        for (int c = 0; c < totalClassProb.size(); c++) {
//            for (int i = 0; i < totalClassProb.get(c).size(); i++) {
//                for (int j = 0; j < totalClassProb.get(c).get(i).length; j++) {
//                    totalClassProb.get(c).get(i)[j] = totalClassProb.get(c).get(i)[j] / this.classCount[c];
//                    System.out.print(" " + totalClassProb.get(c).get(i)[j] + " ");
//                }
//                System.out.println("");
//            }
//            System.out.println("");
//        }
    }

    public double findMax(Integer d, int a) {
        double max = 0;
        int c = 0;

        for (int i = 0; i < this.classList.size(); i++) {
            double temp = this.totalClassProb.get(i).get(a)[d] * this.classProbability.get(i);
            //handle smoothing for 0 probabilities
            if (temp == 0) {
                temp = .001 * this.classProbability.get(i);
            }
            if (temp > max) {
                max = temp;
                c = i;
            }
        }

        maxClass[c] += 1;

        return max;
    }

    public void runNB(List<Node> test, boolean house) {
        int max = 0;
        int temp = 0;
        int c = 0;
        int housej = 0;
        if (house == true) {
            housej = 1;
        }
        // for statistics, false pos, etc time allowing
        int fp, p, fn, n = 0;

        //for 0/1 loss
        int win = 0, loss = 0;

        for (int k = 0; k < test.size(); k++) {
            c = 0;
            temp = 0;
            max = 0;
            for (int j = housej; j < test.get(k).inputData.size() - 1; j++) {
                findMax(Integer.parseInt(test.get(k).inputData.get(j)), j);
            }

            // Get the most likely class
            for (int i = 0; i < maxClass.length; i++) {
                temp = maxClass[i];
                if (temp > max) {
                    max = temp;
                    c = i;
                }
            }
            if (house == true) {
                // System.out.println(this.classList.size()+1);
                //System.out.println("The actual class is: " + test.get(k).inputData.get(0));
               // System.out.println("The guessed value is: " + this.classList.get(c));
//            System.out.println("");

                if (test.get(k).inputData.get(0).equals(this.classList.get(c))) {
                    win++;
                } else {
                    loss++;
                }

                for (int l = 0; l < maxClass.length; l++) {
                    maxClass[l] = 0;
                }
            } else {
                // System.out.println(this.classList.size()+1);
                //System.out.println("The actual class is: " + test.get(k).inputData.get(test.get(k).inputData.size() - 1));
                //System.out.println("The guessed value is: " + this.classList.get(c));
//            System.out.println("");

                if (test.get(k).inputData.get(test.get(k).inputData.size() - 1).equals(this.classList.get(c))) {
                    win++;
                } else {
                    loss++;
                }

                for (int l = 0; l < maxClass.length; l++) {
                    maxClass[l] = 0;
                }
            }
        }

        System.out.println("The 0/1 (win/loss) ratio is: " + win + "/" + loss);

    }

    // We need to get the classlist for testing. Based on the text file the class is either the first or last value
    public void setNBData() {
        int length = this.data.size();
        List<Node> test = this.data.subList(0, length / 2);
        List<Node> train = this.data.subList(length / 2, length);

        switch (this.file) {
            case "iris.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                //split the data into training and testing;

                // There are 9 possible values: 0-8, and 4 different attributes
                buildFrequencies(9, 4, false, train);
                maxClass = new int[this.classList.size()];
                Arrays.fill(maxClass, 0);

                // run naiveBayes!
                runNB(test, false);
                break;
            }
            case "soybean-small.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                buildFrequencies(7, 35, false, train);
                maxClass = new int[this.classList.size()];
                Arrays.fill(maxClass, 0);

                // run naiveBayes!
                runNB(test, false);
                break;
            }
            case "glass.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                buildFrequencies(2, 9, false, train);

                maxClass = new int[this.classList.size()];
                Arrays.fill(maxClass, 0);

                // run naiveBayes!
                runNB(test, false);
                break;
            }
            case "breast-cancer-wisconsin.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                buildFrequencies(11, 9, false, train);
                maxClass = new int[this.classList.size()];
                Arrays.fill(maxClass, 0);

                // run naiveBayes!
                runNB(test, false);
                break;
            }
            case "house-votes-84.data.txt": {
                /// fix triple loop i = 0 counter
                buildClassListHouse(this.data.get(0).inputData.size() - 1);
                buildFrequencies(3, 16, true, train);
                maxClass = new int[this.classList.size()];
                Arrays.fill(maxClass, 0);

                // run naiveBayes!
                runNB(test, true);
                break;
            }

        }
    }

}
