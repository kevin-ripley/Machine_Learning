/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author kevinripley
 */
public class Machine_learning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("glass.data.txt");
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(line);
        }// TODO code application logic here
    }

}
