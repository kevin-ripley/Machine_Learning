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
    public Node leftChild; // this node's left child
    public Node rightChild; // this node's right child

    public void displayNode() // display ourself
    {
        
       // System.out.println("");
    }

    public void setValue(String value) {
        inputData = new ArrayList<>(Arrays.asList(value.split(",")));
    }
    
    public ArrayList<String> getValue() {
        return inputData;
    }
    
}
