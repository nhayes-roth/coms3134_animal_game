/* Name: Nathan Hayes-Roth
 * UNI: nbh2113
 * Course: COMSW3134
 * Assignment 3: Animal Game
 * File Description:  Administers the game.
 */
 
package animal;
import io.*;

public class Play{
    
    static String answer = "";
    private final static BinaryTree newTree = new BinaryTree("Horse");
    
    /* administers yes/no questions, only accepting appropriate responses and
     * returning a string answer */
    public static String yesNo(){
        String answer = "";
        while(true){
            try{
                answer = IO.stdin.readLine().toLowerCase().substring(0,1);
            }
            catch (Exception e){
                IO.stderr.println("Error: " + e);
            }
            if (!(answer.equals("n")||answer.equals("y"))){
                IO.stdout.println("Sorry, that was a yes/no question." +
                                  "('y' to answer 'yes')\n\t" +
                                  "('n' to answer 'no')");
            }
            else return answer;
        }
    }
    
    /* prompts the user to start a new game or continue playing with the
     * previously acquired knowledge*/
    public static boolean newGame(){
        String answer = "";
        IO.stdout.println("Would you like to start a new game or continue " +
                          "from the previous session?");
        while(true){
            try{
                answer = IO.stdin.readLine().toLowerCase().substring(0,1);
            }
            catch (Exception e){
                IO.stderr.println("Error: " + e);
            }
            if (!(answer.equals("n")||answer.equals("c"))){
                IO.stdout.println("Sorry, I didn't catch that.\n\t" +
                                  "('n' to start a new game)\n\t" +
                                  "('c' to continue)");
            }
            else return answer.equals("n")?
                true:
                false;
        }
    }
    
    /* ask the user questions to traverse the tree */
    public static BinaryTree traverseTree(BinaryTree root){
        if (root.isAnimal()) {
            return root;
        }
        else{
            IO.stdout.println(root.data());
            String answer = yesNo();
            if (answer.equals("n")){
                return(root.right());
            }
            else return(root.left());
        }
    }
    
    /* asks the user if he/she is thinking of the current animal */
    public static boolean checkAnimal(BinaryTree root){
        IO.stdout.print("Were you thinking of a");
        if (root.startsWithVowel()){
            IO.stdout.println("n " + root.data() + "?");
        }
        else IO.stdout.println(" " + root.data() + "?");
        String answer = yesNo();
        if (answer.equals("n")){
            return false;
        }
        else return true;
    }
    
    /* prompts the user for a new question to differentiate animals 
     * note: append a question mark if the input does not end with one */
    public static void addQuestion(BinaryTree node){
        IO.stdout.println("Shoot! I give up. What animal were you thinking of?");
        String animal = "";
        String question = "";
        try{
            animal = IO.stdin.readLine();
        }
        catch (Exception e){
            IO.stderr.println("Error: " + e);
        }
        IO.stdout.println("Type a question that distinguishes " + node.data() +
                          " from " + animal + ":");
        try{
            question = IO.stdin.readLine();
        }
        catch (Exception e){
            IO.stderr.println("Error: " + e);
        }
        if (!BinaryTree.isQuestion(question)){
            question = question + "?";
        }
        IO.stdout.println("What is the answer for " + node.data() + "?");
        String answer = yesNo();
        if (answer.equals("n")){
            node.setRight(new BinaryTree(node.data()));
            node.setLeft(new BinaryTree(animal));
            node.setData(question);
        }
        else{
            node.setRight(new BinaryTree((animal)));
            node.setLeft(new BinaryTree(node.data()));
            node.setData(question);
        }
    }
    
    /* checks to see if a knowledge file exists, defaulting to a new game if
     * the user hasn't played before.
     */
    public static BinaryTree checkSave(BinaryTree root){
        try{
            return BinaryTree.toTree(BinaryTree.fileToString());
        }
        catch (Exception e){
            IO.stderr.println("No save file found. Defalting to new game.");
            return root;
        }
    }
    
    public static void main(String[] args){
        while(true){
            BinaryTree root = newGame()?
                newTree:
                checkSave(newTree);
            BinaryTree currentNode = root;
            while(true){
                currentNode = traverseTree(currentNode);
                if (currentNode.isAnimal()){
                    break;
                }
            }
            if(checkAnimal(currentNode)){
                IO.stdout.println("Cool!\n");
            }
            else{
                addQuestion(currentNode);
                IO.stdout.println("Got it, I'll remember that!\n");
            }
            root.toFile();
        }
    }
}    