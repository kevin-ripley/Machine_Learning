/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static java.nio.file.Files.lines;

/**
 *
 * @author Kevin Ripley
 */
public class PreProcess {
    File file = new File("glass.txt");
    String[] lines = new String[10];
    
        FileReader reader = new FileReader(file);
        BufferedReader buffReader = new BufferedReader(reader);
        int x = 0;
        String s;
        while((buffReader.readLine()) != null){
            lines[x] = s;
            x++;
        }
    for(int i = 0; i <= lines[].length(); i ++){
    System.out.println(lines[i]);
}
  }

