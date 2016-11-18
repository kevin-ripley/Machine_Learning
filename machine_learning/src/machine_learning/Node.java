/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    //doubly linkedlist
    public  ArrayList<String> inputData; // data item (key)
    public ArrayList<Node> neighbors = new ArrayList<>();
    public Node leftChild; // this node's left child
    public Node rightChild; // this node's right child
    boolean visited = false;
    
    double weight = 0;

    public void displayNode() // display ourself
    {
        
    }

    public void setVisited(){
        this.visited = true;
    }
    
    public void setWeight(double d){
        this.weight = d;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public void addEdge(Node n){
        this.neighbors.add(n);
    }
    
    public void removeEdge(int i) {
        this.neighbors.remove(i);
    }
    
    public void setValue(String value) {
        inputData = new ArrayList<>(Arrays.asList(value.split(",")));
    }
    
    public ArrayList<String> getValue() {
        return inputData;
    }
    
}
