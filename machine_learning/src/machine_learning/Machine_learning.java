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
import java.util.Scanner;

/**
 *
 * @author kevinripley
 */
public class Machine_learning {

    /**
     * @param args the command line arguments
     */
    static int count = 0;
    public static void main(String[] args) throws FileNotFoundException, IOException {
    ArrayList<String> data = new ArrayList<>();
    FileReader process = new FileReader("glass.data.txt");
    Scanner scan = new Scanner(process);
    // Set delimiters to space and comma.
    // ", *" tells Scanner to match a comma and zero or more spaces as
    // delimiters.

    scan.useDelimiter (", *");
    //scan.useDelimiter ("\n");
    
    // Read and add to ArrayList
    while (scan.hasNext()!= false) {
                  data.add(scan.next());
                   //System.out.println( data );
                    
    }
    

    process.close ();
    }

}
