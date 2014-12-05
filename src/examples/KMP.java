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
public class KMP {

  char[] t; // text
  char[] p; // pattern
  int[] prefix;
  
  int matchCounter;
  
  int n, m;

  public KMP(char[] t, char[] p) {
    this.t = t;
    this.p = p;
    n = t.length;
    m = p.length;

    prefix = new int[m];
    setFailure();
  }

  private void setFailure() {
    prefix[0] = 0;
    int i = 1;
    int j = 0;

    while (i < m)
    {
      if(p[i] == p[j])
      {
        prefix[i] = j + 1;
        i++;
        j++;
      }
      else if(j > 0)
      {
        j = prefix[j - 1];
      }
      else
      {
        prefix[i] = 0;
        i++;
      }
    }
  }

  public int match() {
    int i = 0;
    int j = 0;
    
    matchCounter = 0;

    while (i < n)
    {
      matchCounter++;
      if(t[i] == p[j])
      {
        if(j == m - 1)
        {
          return i - j;
        }
        else
        {
          i++;
          j++;
        }
      }
      else
      {
        if(j > 0)
          j = prefix[j - 1];
        else
          i++;
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
    String t = "a pattern matching algorithm";
    String p = "Dorfschulmeister";
    KMP bm = new KMP(t.toCharArray(), p.toCharArray());
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
    System.out.println("Vergleiche: " + bm.getMatchCount());
    System.out.println(" found at " + bm.match());
    System.out.println(t.indexOf(p));
  }

}
