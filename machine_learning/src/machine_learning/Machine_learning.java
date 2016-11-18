/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Machine_learning {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String[] files = new String[5];
        files[0] = "house-votes-84.data.txt";
        files[1] = "iris.data.txt";
        files[2] = "glass.data.txt";
        files[3] = "soybean-small.data.txt";
        files[4] = "breast-cancer-wisconsin.data.txt";

//        for (int i = 0; i < files.length; i++) {
//            PreProcess preProcess = new PreProcess();
//            ArrayList<Node> data = new ArrayList<>();
//            try (FileReader process = new FileReader(files[i])) {
//                Scanner scan = new Scanner(process);
//                scan.useDelimiter("\n");
//
//                // Populate an array list of Nodes to be preprocess and given to an algorithm
//                while (scan.hasNext() != false) {
//                    Node node = new Node();
//                    node.setValue(scan.next());
//                    // node.displayNode();
//                    data.add(node);
//                }
//                process.close();
//                // Process our data/test set for file 0 and 4
//                //preProcess.missingValues(data, files[0]);
//                preProcess.missingValues(data, files[i]);
//                // Do this for all files
//                preProcess.discretize(files[i], data);
//                preProcess.shuffle(data);
//                preProcess.stratify(files[i], data);
//                // preProcess.print(data);
//                //System.out.println(data.size());
//            }
//
//            //Start the NB Algorithm
//            NaiveBayes NB = new NaiveBayes(data, files[i]);
//            NB.setNBData();
//
//        }

        PreProcess preProcess = new PreProcess();
        ArrayList<Node> data = new ArrayList<>();
        try (FileReader process = new FileReader(files[2])) {
            Scanner scan = new Scanner(process);
            scan.useDelimiter("\n");

            // Populate an array list of Nodes to be preprocess and given to an algorithm
            while (scan.hasNext() != false) {
                Node node = new Node();
                node.setValue(scan.next());
                data.add(node);
            }
            process.close();

            preProcess.missingValues(data, files[2]);
            // Do this for all files
            preProcess.discretize(files[2], data);
            preProcess.shuffle(data);
            preProcess.stratify(files[2], data);

        }
//        NearestNeighbor nn = new NearestNeighbor(data, files[1]);
//        nn.setNNData();
//        
        NB_Tan nbt = new NB_Tan(data, files[2]);
        nbt.prepareGraph();
        
        //ArrayList<Node> test;
//        preProcess.shuffle(data);
//        preProcess.stratify(files[1], data);
//        for (int i = 0; i < data.size(); i++) {System.out.println("");
//            for (int j = 0; j < data.get(i).getValue().size(); j++) {
//                System.out.print(data.get(i).getValue().get(j));
//                
//            }
//        }

        //ID3 id3 = new ID3(data, files[1], preProcess.getClassList(files[0]));
        //id3.printlist();
    }
}
