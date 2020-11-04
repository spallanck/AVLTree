//Author: Sophie Pallanck
//Date: 5/9/20
/*Purpose: This program reads from a file or user input and calculates
the total words and the unique words in the input specified */
package avl;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Vocab {
  
  public static void main(String[] args) {
    if (args.length == 0) { //no text files specified
      Scanner sc = new Scanner(System.in);
      Count c = wordCount(sc);
      System.out.println();
      System.out.println(c);
    } else {
      for (int i = 0; i < args.length; i++) { //read each file passed in
        try {
          File f = new File(args[i]);
          Scanner sc = new Scanner(f);
          Count c = wordCount(sc);
          System.out.println(c);
        } catch (FileNotFoundException exc) {
          System.out.println("Could not find file " + args[i]);
        }
      }
    }
  }
  
  /* count the total and unique words in a document being read
  * by the given Scanner. return the two values in a Count object.*/
  private static Count wordCount(Scanner sc) {
    AVL tree = new AVL();
    Count c = new Count();
    while (sc.hasNext()) {
      // read and parse each word
      String word = sc.next();
      c.total++;
      // remove non-letter characters, convert to lower case:
      word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase();
      tree.avlInsert(word);
    }
    c.unique = tree.getSize(); 
    return c;
  }
}