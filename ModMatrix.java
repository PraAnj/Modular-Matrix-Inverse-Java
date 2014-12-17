package matrixoperations;
import java.math.*;

public class ModMatrix {
    private int nrows;
    private int ncols;
    private BigInteger[][] data;
    private final BigInteger mod= new BigInteger("26");

    public ModMatrix(BigInteger[][] dat) {
        this.data = dat;
        this.nrows = dat.length;
        this.ncols = dat[0].length;
    }

    public ModMatrix(int nrow, int ncol) {
        this.nrows = nrow;
        this.ncols = ncol;
        data = new BigInteger[nrow][ncol];
    }

    public int getNrows() {
        return nrows;
    }

    public void setNrows(int nrows) {
        this.nrows = nrows;
    }

    public int getNcols() {
        return ncols;
    }

    public void setNcols(int ncols) {
        this.ncols = ncols;
    }

    public BigInteger[][] getData() {
        return data;
    }

    public void setData(BigInteger[][] data) {
        this.data = data;
    }

    public BigInteger getValueAt(int i, int j) {
        return data[i][j];
    }

    public void setValueAt(int i, int j, BigInteger value) {
        data[i][j] = value;
    }

    public int size() {
        return ncols;
    }

    // Take the transpose of the Matrix..
    public static ModMatrix transpose(ModMatrix matrix) {
        ModMatrix transposedMatrix = new ModMatrix(matrix.getNcols(), matrix.getNrows());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }
        return transposedMatrix;
    }

    // All operations are using Big Integers... Not Modular of anything
    public static BigInteger determinant(ModMatrix matrix) {

        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }
        if (matrix.size() == 2) {
            //return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
            return (matrix.getValueAt(0, 0).multiply(matrix.getValueAt(1, 1))).subtract((matrix.getValueAt(0, 1).multiply(matrix.getValueAt(1, 0))));
        }
        BigInteger sum = new BigInteger("0"); 
        for (int i = 0; i < matrix.getNcols(); i++) {
            sum = sum.add(changeSign(i).multiply(matrix.getValueAt(0, i).multiply(determinant(createSubMatrix(matrix, 0, i)))));
        }
        return sum;
    }

    private static BigInteger changeSign(int i) {
        if (i % 2 == 0) {
            return new BigInteger("1"); 
        } else {
            return new BigInteger("-1");
        }
    }

    public static ModMatrix createSubMatrix(ModMatrix matrix, int excluding_row, int excluding_col) {
        ModMatrix mat = new ModMatrix(matrix.getNrows() - 1, matrix.getNcols() - 1);
        int r = -1;
        for (int i = 0; i < matrix.getNrows(); i++) {
            if (i == excluding_row) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < matrix.getNcols(); j++) {
                if (j == excluding_col) {
                    continue;
                }
                mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
            }
        }
        return mat;
    }

    public ModMatrix cofactor(ModMatrix matrix) {
        ModMatrix mat = new ModMatrix(matrix.getNrows(), matrix.getNcols());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                mat.setValueAt(i, j, (changeSign(i).multiply(changeSign(j)).multiply(determinant(createSubMatrix(matrix, i, j)))).mod(mod));
            }
        }

        return mat;
    }

    public ModMatrix inverse(ModMatrix matrix) {
        return (transpose(cofactor(matrix)).dc(determinant(matrix)));
    }

    private ModMatrix dc(BigInteger d) {
        BigInteger inv = d.modInverse(mod);
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                data[i][j] = (data[i][j].multiply(inv)).mod(mod); 
            }
        }
        return this;
    }
}
