package machine_learning;

import java.util.ArrayList;

public class NaiveBayes {

    // TODO: need to handle zero probabilities, then use bayes formulat to get max probability.
    
    ArrayList<Node> data = new ArrayList<>();
    ArrayList<String> classList = new ArrayList<>();
    ArrayList<Integer[]> frequencyTable = new ArrayList<>();
    String file = "";

    public NaiveBayes(ArrayList<Node> d, String f) {
        this.data = d;
        this.file = f;
    }

    // Build the class list from the data.
    public void buildClassList(int index) {
        for (int i = 0; i < data.size(); i++) {
            if(index < data.get(i).inputData.size() && !classList.contains(data.get(i).inputData.get(index))){
                classList.add(data.get(i).inputData.get(index));
            }
        }
    }

    public void printClassList(){
        for (int i = 0; i < this.classList.size(); i++){
            System.out.println(classList.get(i));
        }
    }
    
    public void buildFrequencies(Integer possibleValues) {
        // We need the total number of attributes to count, minus the class list
        int attributeSize = this.data.get(0).inputData.size()-1;
        
        // set the frequency table with the total attribut categories, then populate each category with corresponding values
        for(int i = 0; i < attributeSize; i++) {
            Integer[] attribute = new Integer[possibleValues];
            for (int j = 0; j < possibleValues; j++){
                attribute[j] = 0;
            }
            this.frequencyTable.add(attribute);
        }
        
        // Populate the attribute frequencies
        for(int i = 0; i < this.data.size()-1; i++){
            for(int j = 0; j < attributeSize; j++) {
                if ( j < this.data.get(i).inputData.size()) {
                frequencyTable.get(j)[Integer.parseInt(this.data.get(i).inputData.get(j))] += 1;
                }
            }
        }
        // for debugging, checking frequency table output
        for(int i = 0; i < frequencyTable.size(); i++ ) {
            System.out.println("Attribute " + i);
            for(int j = 0; j < possibleValues; j++) {
                System.out.println(frequencyTable.get(i)[j]);
            }
        }
    }
    
    // We need to get the classlist for testing. Based on the text file the class is either the first or last value
    public void setNBData() {
        switch (this.file) {
            case "iris.data.txt": {
                buildClassList(this.data.get(0).inputData.size()-1);
                // There are 9 possible values: 0-8
                buildFrequencies(9);
                break;
            }

        }
    }

}
