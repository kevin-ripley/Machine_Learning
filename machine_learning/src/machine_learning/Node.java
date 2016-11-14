/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author kevinripley
 */
public class Node {
    //doubly linkedlist
        public ArrayList<String> iData; // data item (key)
	public String dData; // data item
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child
        
        
	public void displayNode() // display ourself
	{
		//System.out.print('{');
		System.out.println(iData);
//		System.out.print(", ");
//		System.out.print(dData);
//		System.out.print("} ");
	}
        public void parse(String dData){
           List<String> list = new ArrayList<String>(Arrays.asList(dData.split(",")));
          iData = (ArrayList<String>) list;       
    }
        
}
