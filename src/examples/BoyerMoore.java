/**
 * 
 */
package examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author lar02
 *
 */
public class BoyerMoore {

  char[] t; // text
  char[] p; // pattern
  int[] last = new int[256]; // last occurence table of a char

  int n; // length of text
  int m; // length of pattern
  
  int matchCounter;

  public BoyerMoore(char[] t, char[] p) {
    this.t = t;
    this.p = p;
    n = t.length;
    m = p.length;
    Arrays.fill(last, -1);
    for (int k = 0; k < m; k++)
    {
      last[p[k]] = k;
    }
  }

  public int match() {
    int i = m - 1; // pos in text
    int j = m - 1; // pos in pattern

    matchCounter = 0;
    
    while (i < n)
    {
      matchCounter++;
      if(t[i] == p[j])
      {
        if(j == 0)
          return i;
        i--;
        j--;
      }
      else
      {
        i = i + m - Math.min(j, last[t[i]] + 1);
        j = m - 1;
      }
    }

    return -1;
  }

  public void setText(File file) throws IOException {
    FileInputStream in = null;
    int c = -1;

    try
    {
      in = new FileInputStream(file);
      int len = in.available();
      t = new char[len + 1];
      int i = 0;
      while ((c = in.read()) != -1 && i < len)
      {
        char cb = (char) c;
        // if (cb<=0 || cb>255) System.out.println("i: "+i+", cb: "+cb);
        t[i++] = cb;
      }
      t[i++] = 0;// stopchar
      n = t.length - 1;
    }
    finally
    {
      if(in != null)
      {
        in.close();
      }
    }
  }
  
  public int getMatchCount(){
    return matchCounter;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    String t = "a pattern rithm matching algorithm a pattern matching algorithm";
    String p = "Dorfschulmeister";
    
    BoyerMoore bm = new BoyerMoore(t.toCharArray(), p.toCharArray());

    try
    {
      bm.setText(new File("resources/Goethe.txt"));
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    long s = System.currentTimeMillis();

    for (int i = 0; i < 1000; i++)
      bm.match();

    long e = System.currentTimeMillis();
    System.out.println(e - s + " micro sec");
    System.out.println(" found at " + bm.match());
    System.out.println("Vergleiche: " +bm.getMatchCount());
    System.out.println(t.indexOf(p));
  }

}
