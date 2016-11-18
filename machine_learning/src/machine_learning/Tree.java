package machine_learning;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Austin
 */
public class Tree {

    private Tree left;
    private Tree right;
    private double weight;
    ArrayList<String> classNode;
    int win = 0;
    int loss = 0;

    public Tree(Node n) {
        this.weight = n.weight;
        this.classNode = n.inputData;
        this.left = null;
        this.right = null;
    }

    public void addNode(Node n) {
        if (n.weight < this.weight) {
            if (this.left != null) {
                this.left.addNode(n);
            } else {
                this.left = new Tree(n);
            }
        } else if (this.right != null) {
            this.right.addNode(n);
        } else {
            this.right = new Tree(n);
        }

    }

    public void inOrder() {
        if (this.left != null) {
            this.left.inOrder();
        }
        System.out.println(this.weight);
        if (this.right != null) {
            this.right.inOrder();
        }

    }

    public void findClass(double max, ArrayList<String> actual, Tree parent, List<Node> test) {

        double l = 0;
        double r = 0;
        if (this.left != null) {
            l = max - this.left.weight;
        } else if (this.right != null) {
            r = max - this.right.weight;
        } else if (actual.get(actual.size() - 1).equals(this.classNode.get(this.classNode.size() - 1))) {
            parent.win++;
        } else {
            parent.loss =  test.size() - win;
        }//System.out.println("The Guessed class is " + this.classNode.get(this.classNode.size()-1));
        //System.out.println("The Actual class is " + actual.get(actual.size() -1 ));
        /// System.out.println("The 0/1 (win/loss) ratio for TAN is: " + win + "/" + loss);

        if (r > l && this.right != null) {
            this.right.findClass(max, actual, parent, test);
        }

        if (l < r && this.left != null) {
            this.left.findClass(max, actual, parent, test);
        }

    }

    public void getWinLoss() {
        System.out.println("The 0/1 (win/loss) ratio for TAN is: " + this.win/2 + "/" + this.loss/2);
    }
}
