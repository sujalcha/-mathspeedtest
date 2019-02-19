
package mathProblem;

/**
 * @Sujal 12043248
 */

import java.util.Random;

public class Division {
    
    public int denominator;
    public int nominator;
    public int result;
    
    Random rand = new Random();
    
    public void setden(int denominator) {
            this.denominator = denominator; 
        }

    public int getden() {
        denominator = rand.nextInt(50)+1; // random number from 0 to 50
    return denominator;
    }
    
    public void setnom(int nominator) {
    this.nominator = nominator; 
    }

    public int getnom() {
        nominator = rand.nextInt(10)+1; // random number from 0 to 10
    return nominator;
    }
    
    public void setres(int result) {
    this.result = result; 
    }
    public int getres() {
       result= denominator/nominator;
    return result;
    }
}


