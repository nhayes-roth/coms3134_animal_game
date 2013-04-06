/* Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Course: COMSW3134
 * Assignment 3: Animal Game
 * File Description:  BinaryTree defines the data structure which will hold all
 * the information in the Animal Game.
 */
 
package animal;
import io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;


public class BinaryTree{
    private String data;
    private BinaryTree left;
    private BinaryTree right;
    
    public final static BinaryTree NIL = new BinaryTree(null, null, null);
    
    /*Constructors*/
    public BinaryTree(String d, BinaryTree l, BinaryTree r){
        data = d;
        left = l;
        right = r;
    }
    public BinaryTree(String d){
        data = d;
        left = BinaryTree.NIL;
        right = BinaryTree.NIL;
    }
    
    /* Returns the current string */
    public String data(){
        return this.data;
    }
    
    /* Returns the left tree */
    public BinaryTree left(){
        return this.left;
    }
    
    /* Returns the right tree */
    public BinaryTree right(){
        return this.right;
    }
    
    /* Assigns a data value to the current tree top */
    public void setData(String s){
        this.data = s;
    }
    
    /* Assigns a specific BinaryTree to a node's left side */
    public void setLeft(BinaryTree l){
        this.left = l;
    }
    
    /* Assigns a specific BinaryTree to a node's right side */
    public void setRight(BinaryTree r){
        this.right = r;
    }
    
    /* isEmpty() check */
    public boolean isEmpty(){
        return this == BinaryTree.NIL;
    }
    
    /* checks to see if the current node is an animal */
    public boolean isAnimal(){
        return (this.left().isEmpty() && this.right().isEmpty());
    }
    
    /* checks to see if the current node starts with a vowel */
    public boolean startsWithVowel(){
        String fc = this.data().substring(0,1).toLowerCase();
        if (fc.equals("a") || fc.equals("e") || fc.equals("i") ||
            fc.equals("o") || fc.equals("u")){
            return true;
        }
        else return false;
    }
    
    /* checks a string for question marks (indicating a question) */
    public static boolean isQuestion(String str){
        String lastChar = str.substring(str.length()-1,str.length());
        return (lastChar.equals("?"));
    }
    
    /* prints a tree in preorder format */
    public void printTree(){
        printTree("");
    }
    public void printTree(String str){
        try{
            if (this.isEmpty()){
                return;
            }
            IO.stdout.println(str+this.data());
            this.left().printTree(str+"-");
            this.right().printTree(str+"-");
        }
        catch(Exception e){
            IO.stderr.println("Error: " + e);
        }
    }
    
    /* toString() */
    public String toString(){
        String toReturn = "";
        try{
            if (this.isEmpty()){
                return "";
            }
            if (this.isAnimal()){
                toReturn += this.data() + "\n";
            }
            else{
                toReturn += this.data()+"\n";
                toReturn += this.left().toString();
                toReturn += this.right().toString();
            }
        }
        catch(Exception e){
            IO.stderr.println("Error: " + e);
        }
        return toReturn;
    }
    
    /* write a tree to a file (preorder format) */
    public void toFile(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("Knowledge.txt"));
            out.write(this.toString());
            out.close();
        }
        catch (Exception e){
            IO.stderr.println("Error: " + e);
        }
    }
    
    /* read a file into a String */
    public static String fileToString(){
        String toReturn = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader("Knowledge.txt"));
            StringBuilder builder = new StringBuilder();
            String line = in.readLine();
            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = in.readLine();
            }
            in.close();
            toReturn = builder.toString();
        }
        catch (Exception e){
            IO.stderr.println("Error: " + e);
        }
        return toReturn;
    }
    
    /* converts a String into a BinaryTree (preorder format) */
    static String[] Preorder;
    public static BinaryTree toTree(String preorder){
        Preorder = preorder.split("\n");
        return toTree();
    }
    public static BinaryTree toTree(){
        if (Preorder.length == 0){
            return BinaryTree.NIL;
        }
        String line  = Preorder[0];
        String[] temp = new String[Preorder.length-1];
        for (int i=0; i<temp.length; i++){
            temp[i] = Preorder[i+1];
        }
        Preorder = temp;
        if (!isQuestion(line)){
            return new BinaryTree(line);
        }
        else{
            return new BinaryTree(line, toTree(), toTree());
        }
    }
    
    public static void main(String [] args){
    }
}