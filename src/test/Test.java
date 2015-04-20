package test;

import java.util.Random;

public class Test {

  public static void main(String[] args) {
      int n = 256;
      Random rand = new Random();
      
      String output = "";
      for(int i=0;i<24;i++){
        if(i!=0) output = ",";
        else output = "";
        output += String.valueOf(rand.nextInt(n));
        System.out.print(output);
      }

  }

}
