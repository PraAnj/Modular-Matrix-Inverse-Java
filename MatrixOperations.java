package matrixoperations;

import java.math.BigInteger;

public class MatrixOperations {

    public static void main(String[] args) {

        BigInteger[][] key2 = new BigInteger[3][3];
        key2[0][0] = new BigInteger("12");
        key2[0][1] = new BigInteger("24");
        key2[0][2] = new BigInteger("15");
        key2[1][0] = new BigInteger("13");
        key2[1][1] = new BigInteger("6");
        key2[1][2] = new BigInteger("10");
        key2[2][0] = new BigInteger("20");
        key2[2][1] = new BigInteger("17");
        key2[2][2] = new BigInteger("15");
        
        ModMatrix obj2= new ModMatrix(key2);
        ModMatrix inverse2 = obj2.inverse(obj2);

        for (int i = 0; i < inverse2.getNrows(); i++) {
            for (int j = 0; j < inverse2.getNcols(); j++) {
                    System.out.print(inverse2.getData()[i][j]+" ");
            }
            System.out.println("");
        }
    }

}
