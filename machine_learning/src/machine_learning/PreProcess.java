/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machine_learning;

import java.util.ArrayList;

/**
 *
 * @author Kevin Ripley
 */
public class PreProcess {

    public void processIt(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {

        }
    }

    //this method is for housevotes missing values
    //1=0, 2=0. 3=12. 4=48, 5=11, 6=11, 7=15, 8=11, 9=14, 10=15, 11=22, 12=7, 13=21, 14=31, 15=25, 16=17, 17=28
    public void hVote(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.contains("?")) {
                list.get(i);
                list.set(i, "0");
            }
        }
    }
}
