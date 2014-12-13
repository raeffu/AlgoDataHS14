/**
 *
 */
package examples;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ps
 */
public class LCS {

  char[] x, y;
  int n, m;
  int[][] lcs;

  public LCS(String sX, String sY){
    x = sX.toCharArray();
    y = sY.toCharArray();
    n = x.length;
    m = y.length;
    lcs = new int[n+1][m+1];
    for(int i = 1; i <= n; i++)
      for(int k = 1; k <= m; k++)
        if(x[i-1] == y[k-1])
          lcs[i][k] = lcs[i-1][k-1]+1;
        else
          lcs[i][k] = Math.max(lcs[i][k-1], lcs[i-1][k]);
  }

  private String getLCSubSequence(){
    return getLCSubSequence(n, m);
  }

  private String getLCSubSequence(int i, int k){
    if(i == 0 || k == 0)
      return "";

    if(lcs[i-1][k] == lcs[i][k])
      return getLCSubSequence(i-1, k);
    else if(lcs[i][k-1] == lcs[i][k])
      return getLCSubSequence(i, k-1);
    else
      // Bei diagonalem Schritt Buchstabe (aus X) merken => �bereinstimmung
      return getLCSubSequence(i-1, k-1)+x[i-1];
  }

  public String getEditString(){
    return getEditString(n, m, lcs[n][m]);
  }

  private String getEditString(int i, int k, int len){
    // to do ......
    return "";
  }

  public Set<String> getAllSubseqs(){
    Set<String> al = new HashSet<>();
    getLCSubSequences(al, "", n, m);
    return al;
  }

  public void getLCSubSequences(Set<String> list, String seq, int i, int k){
    if(i < 1 || k < 1)
    {
      list.add(seq);
      return;
    }

    if(lcs[i-1][k] == lcs[i][k])
      getLCSubSequences(list, seq, i-1, k);
    if(lcs[i][k-1] == lcs[i][k])
      getLCSubSequences(list, seq, i, k-1);
    if(x[i-1]==y[k-1]){
      seq = x[i-1]+seq;
      getLCSubSequences(list, seq, i-1, k-1);
    }
  }

  static public void main(String[] argv){
    LCS lc = new LCS("SENDEN", "DRESEN");
    System.out.println(lc.getLCSubSequence());
    System.out.println(lc.getAllSubseqs());
    System.out.println(lc.getEditString());
  }
}