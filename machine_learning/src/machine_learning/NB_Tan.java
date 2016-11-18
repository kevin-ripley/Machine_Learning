package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NB_Tan {

    ArrayList<Node> tanGraph = new ArrayList<>();
    ArrayList<Node> maxSpan = new ArrayList<>();
    List<String> classList = new ArrayList<>();
    List<Double> classProbability = new ArrayList<>();
    List<ArrayList<Double[]>> attributeProb = new ArrayList<>();
    String file;
    double[] classCount;

    public NB_Tan(ArrayList<Node> g, String f) {
        this.tanGraph = g;
        file = f;
    }

    // Prepare graph will connect all nodes to eachother for a complete graph, assign weights to edges, and call maximum spanning tree. 
    public void prepareGraph() {

        // follow our trend of nested loops...
        // For every node, add all other nodes to it
//        for (int i = 0; i < this.tanGraph.size(); i++) {
//            for (int j = 0; j < this.tanGraph.size(); j++) {
//                if (!this.tanGraph.get(i).equals(this.tanGraph.get(j))) {
//                    this.tanGraph.get(i).addEdge(this.tanGraph.get(j));
//                }
//            }
//        }
        addWeights();

    }

    // Add weights to edges for preparation of maximum spanning tree, use CMI
    public void addWeights() {
        // we need class and attribute probabilites
        NaiveBayes NB = new NaiveBayes(this.tanGraph, this.file);
        NB.setNBData();

        double temp = 0;
        double max = 0;
        int classNum = 0;

        this.attributeProb = NB.getAttributeProb();
        this.classList = NB.getClassList();
        this.classProbability = NB.getClassProb();
        this.classCount = NB.getClassCount();

        int count = 0;
        if (this.file.equals("house-votes-84.data.txt")) {
            count = 1;
        }
        for (int i = 0; i < this.tanGraph.size(); i++) {
            temp = 0;
            for (int j = 0; j < this.tanGraph.size(); j++) {
                if (!this.tanGraph.get(i).equals(this.tanGraph.get(j))) {

                    classNum = 0;

                    for (int k = 0; j < classList.size(); j++) {
                        temp = 0;
                        // find max sum for all classes following cmi
                        for (int l = count; l < this.tanGraph.get(i).inputData.size() - 1; l++) {

                            temp += this.attributeProb.get(k).get(l)[Integer.parseInt(this.tanGraph.get(i).inputData.get(l))];
                        }
                        //smooth out the data
                        // System.out.println(temp);
                        temp = Math.abs(((temp + .01) * this.classProbability.get(k)));
                        //System.out.println("here");
                        //  System.out.println(temp);
                        if (temp > max) {
                            max = temp;
                            classNum = k;
                        }

                        // set weight for edge
                        this.tanGraph.get(i).weight = max;
                        //System.out.println(max);
                        //this.tanGraph.get(i).neighbors.get(j).weight = max;
                        // this.tanGraph.get(j).weight = max;
                    }
                    max = 0;
                }
            }
        }

        createSpanningtree();

    }

    public void createSpanningtree() {

        double max = 0;
        double temp = 0;
        int count = 0;
        boolean unvis = true;

        this.tanGraph.get(0).setVisited();
        this.maxSpan.add(this.tanGraph.remove(0));
        
        // find the max weight between two edges.
        // Add nodes to spanTree and mark them as visited, continue until all are visited
        while (unvis) {
            unvis = false;
            for (int i = 0; i < this.tanGraph.size(); i++) {
                if (this.tanGraph.get(i).visited == false) {
                    unvis = true;

                    temp = this.tanGraph.get(i).weight;
                    if (temp > max) {
                        max = temp;
                        count = i;
                    }
                }
            }
            this.tanGraph.get(count).setVisited();
            this.maxSpan.get(0).addEdge(this.tanGraph.get(count));
            //this.maxSpan.add(this.tanGraph.get(count));
            max = 0;
            temp = 0;
        }

        
        System.out.println(this.maxSpan.get(0).neighbors.size());
    }
}
