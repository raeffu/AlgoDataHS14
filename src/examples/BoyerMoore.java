/**
 * 
 */
package examples;

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

    while (i < n)
    {
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

  /**
   * @param args
   */
  public static void main(String[] args) {
    String t = "a pattern rithm matching algorithm a pattern matching algorithm";
    String p = "rithm";
    System.out.println("index of " + p + ": " + t.indexOf(p));
    BoyerMoore bm = new BoyerMoore(t.toCharArray(), p.toCharArray());
    System.out.println("found match at " + bm.match());
  }

}
