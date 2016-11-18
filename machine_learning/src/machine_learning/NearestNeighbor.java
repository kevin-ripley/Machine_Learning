package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NearestNeighbor {

    ArrayList<Node> data = new ArrayList<>();
    ArrayList<Node> test = new ArrayList<>();
    ArrayList<Node> train = new ArrayList<>();
    ArrayList<String> classList = new ArrayList<>();
    ArrayList<Double> classProbability = new ArrayList<>();
    ArrayList<String> intrain = new ArrayList<>();
    ArrayList<ArrayList<Double[]>> totalClassProb = new ArrayList<>();
    List<Class> classList2 = new ArrayList<Class>();
    //list to save distance result
    List<Result> resultList = new ArrayList<Result>();
    String file = "";
    double[] classCount;
    int vary= 0;
    

    public NearestNeighbor(ArrayList<Node> d, String f) {
        this.data = d;
        this.file = f;

    }

    public void split() {
        for (int i = 0; i < this.data.size()-1; i++) {
            if (i < data.size() / 2) {
                test.add(data.get(i));
                
            } else {
                train.add(data.get(i));
            }
            
        }
        for(int j = 0; j < this.train.size(); j ++){
            ArrayList<String> ntrain = train.get(j).inputData;
            for(int h = 0; h < ntrain.size()-1; h++){
                this.intrain.add(ntrain.get(h));
            }
            
        }
        
    }
    public void houseSplit(){
        for (int i = 0; i < this.data.size(); i++) {
            if (i < data.size() / 2) {
                test.add(data.get(i));
                
            } else {
                train.add(data.get(i));
            }
            
        }
        for(int j = 0; j < this.train.size(); j ++){
            ArrayList<String> ntrain = train.get(j).inputData;
            for(int h = 1; h < ntrain.size(); h++){
                this.intrain.add(ntrain.get(h));
            }
            
        }
        
    }

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

    //need to obtain distance for similarity between columns
    public void nnMain() {
        String className = null;
        int k = 4;
        if(vary !=0){
        split();
        }else{
            houseSplit();
        }
        
        //Using the Euclidean Algorithm to find Distance
        for (int i = 0; i < this.data.size(); i++) {
            double dist = 0.0;
            ArrayList<String> current = data.get(i).inputData;
            if(vary !=0){
            for (int j = 0; j < current.size() - 1; j++) {
               
                dist += Math.pow(Double.parseDouble(current.get(j)) - Double.parseDouble(intrain.get(j)), 2);
                className = current.get(vary);
            }
            double distance = Math.sqrt(dist);
            resultList.add(new Result(distance, className));
            }else{
                for (int j = 1; j < current.size(); j++) {
               
                dist += Math.pow(Double.parseDouble(current.get(j)) - Double.parseDouble(intrain.get(j)), 2);
                className = current.get(vary);
            }
            double distance = Math.sqrt(dist);
            resultList.add(new Result(distance, className));
            
            }
        }

        Collections.sort(resultList, new DistanceComparator());
        String[] ss = new String[k];
        for (int x = 0; x < k; x++) {
            //get classes of k nearest instances className from the list into an array
            ss[x] = resultList.get(x).className;
        }
        String vote = voteClass(ss);
        System.out.println("Class of new instance is: " + vote);
    }

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

    public void setNNData() {
        switch (this.file) {
            case "iris.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                // There are 9 possible values: 0-8, and 4 different attributes
                vary = 4;
                nnMain();
                break;
            }
            case "soybean-small.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                vary =  35;
                nnMain();
                break;
            }
            case "glass.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                vary = 9;
                nnMain();
                break;
            }
            case "breast-cancer-wisconsin.data.txt": {
                buildClassList(this.data.get(0).inputData.size() - 1);
                vary = 9;
                nnMain();
                break;
            }
            case "house-votes-84.data.txt": {
                buildClassListHouse(this.data.get(0).inputData.size() - 1);
                vary = 0;
                nnMain();
                break;
            }

        }
    }

    private static String voteClass(String[] array) {
        //add the String array to a HashSet to get unique String values
        Set<String> str = new HashSet<String>(Arrays.asList(array));
        //convert the HashSet back to array
        String[] uniqueValues = str.toArray(new String[0]);
        int[] counts = new int[uniqueValues.length];
        // loop thru unique strings and count how many times they appear in original array   
        for (int i = 0; i < uniqueValues.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j].equals(uniqueValues[i])) {
                    counts[i]++;
                }
            }
        }
        int max = counts[0];
        for (int counter = 1; counter < counts.length; counter++) {
            if (counts[counter] > max) {
                max = counts[counter];
            }
        }
        
        int freq = 0;
        for (int counter = 0; counter < counts.length; counter++) {
            if (counts[counter] == max) {
                freq++;
            }
        }

        //index of most freq value if we have only one mode
        int index = -1;
        if (freq == 1) {
            for (int counter = 0; counter < counts.length; counter++) {
                if (counts[counter] == max) {
                    index = counter;
                    break;
                }
            }
            
            return uniqueValues[index];
        } else {//we have multiple modes
            int[] ix = new int[freq];//array of indices of modes
            System.out.println("Multiple majority classes: " + freq + " classes.");
            int ixi = 0;
            for (int counter = 0; counter < counts.length; counter++) {
                if (counts[counter] == max) {
                    ix[ixi] = counter;//save index of each max count value
                    ixi++; // increase index of array
                }
            }

            for (int counter = 0; counter < ix.length; counter++) {
                System.out.println("Class index: " + ix[counter]);
            }

            //Now choose one at random
            Random generator = new Random();
            //Obtain random number 0 <= rIndex < size of ix
            int rIndex = generator.nextInt(ix.length);
            System.out.println("Random index: " + rIndex);
            int nIndex = ix[rIndex];
            // unique value at that index 
            return uniqueValues[nIndex];
        }

    }

    //class to represent results with distance and name
    static class Result {

        double distance;
        String className;

        public Result(double distance, String className) {
            this.className = className;
            this.distance = distance;
        }
    }
    //simple comparator class used to compare results via distances

    static class DistanceComparator implements Comparator<Result> {

        @Override
        public int compare(Result a, Result b) {
            return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
        }
    }
}
