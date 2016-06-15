package lab2;

/**
 * Created by john on 12.06.2016.
 */
public class Matrix {
    private boolean isDeadMatrix = false;
    private int sign = 1;
    private int rang;

    private String det = null;
    private double[][] matrix = null;
    private double[][] antiMatrix = null;
    private double[] free = null;
    private String log = null;
    private String[] roots = null;

    public Matrix(double[][] matrix, double[] free) {
        this.matrix = matrix;
        this.free = free;
        rang = matrix.length;
    }

    public void solve() {

        if (rang == 1) {
            det = matrix[0][0] + "";
        } else if (rang == 2) {
            calDet2();
        } else if (rang == 3) {
            //calDet3();
        }
        antiMatrix = new double[rang][];
        fillAnti();
        System.out.println(printMatrix(matrix, free, antiMatrix));

        gauss(0);



    }

    private void gauss(int t) {
        for (int i = 0; i < rang-1; i++) {
            for (int j = i+1; j <rang; j++) {
                double main = matrix [i][i];
                double current = matrix [j][i];
               // System.out.print(j+", " + i + "  ");
                if(main > 0) {
                perform(current/main*(-1),j);
                } else {
                    perform(current/main, j);
                }

                /*if(b/a >0) {
                    perform(b/a, i);
                } else if(b/a < 0) {
                    perform(b/a*-1, i);
                } else {
                    perform(0,i);
                }*/
            }
         //   System.out.println();
        }
    }

    private void perform(double d, int row) {
        //add d to every member of definite row matrix, free and anti
        for (int i = 0; i < rang; i++) {
           matrix[row][i] += matrix[i][i]*d;
            antiMatrix[row][i] += antiMatrix[i][i]*d;
        }
        free[row] +=free[row]*d;
            log = log + printMatrix(matrix, free, antiMatrix);
    }

    private void fillAnti() {
        for (int i = 0; i < antiMatrix.length; i++) {
            antiMatrix[i] = new double[antiMatrix.length];
            for (int j = 0; j < antiMatrix.length; j++) {
                if(i==j)
                antiMatrix[i][j] = 1;
            }
        }
    }


    public void calcDet() {
        //simple matrix
        //printMatrix(matrix);

        if (matrix.length == 1) {
            det = fmt(matrix[0][0]);
        } else if (matrix.length == 2) {
            calDet2();
        }

        //n>2 matrix
        checkFirstElement(matrix);


    }


    //calculate det 2x2
    private void calDet2() {
        det = fmt(matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        log = printMatrix(matrix, null, null) + String.format("\nрішення: (%s * %s) - (%s * %s) = %s",
                fmt(matrix[0][0]), fmt(matrix[1][1]), fmt(matrix[0][1]), fmt(matrix[1][0]), det) +
                "\n____________________________________________________________________________________";
    }

    //calculate det 2x2
    private void calDet3() {
        det = fmt(matrix[0][0] * matrix[1][1] * matrix[2][2] +
                matrix[0][1] * matrix[1][2] * matrix[2][0] +
                matrix[1][0] * matrix[2][1] * matrix[0][2] -
                matrix[0][2] * matrix[1][1] * matrix[2][0] -
                matrix[0][1] * matrix[1][0] * matrix[2][2] -
                matrix[1][2] * matrix[2][1] * matrix[0][0]);
        log = printMatrix(matrix, null, null) + String.format("\nрішення: (%s * %s * %s) + (%s * %s * %s) +" +
                        " (%s * %s * %s) - (%s * %s * %s) - (%s * %s * %s) - (%s * %s * %s) = %s",
                fmt(matrix[0][0]), fmt(matrix[1][1]), fmt(matrix[2][2]),
                fmt(matrix[0][1]), fmt(matrix[1][2]), fmt(matrix[2][0]),
                fmt(matrix[1][0]), fmt(matrix[2][1]), fmt(matrix[0][2]),
                fmt(matrix[0][2]), fmt(matrix[1][1]), fmt(matrix[2][0]),
                fmt(matrix[0][1]), fmt(matrix[1][0]), fmt(matrix[2][2]),
                fmt(matrix[1][2]), fmt(matrix[2][1]), fmt(matrix[0][0]), det) +
                "\n____________________________________________________________________________________";
    }

    //swaps rows untill first element != 0
    private void checkFirstElement(double[][] matrix) {
        if (matrix[0][0] == 0) {
            isDeadMatrix = true;
            double firstRow[] = matrix[0];
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][0] != 0) {
                    // System.out.println("changing " + matrix[i][0] + " is now matrix[0]");
                    matrix[0] = matrix[i];
                    matrix[i] = firstRow;
                    isDeadMatrix = false;
                    sign *= -1;
                    break;
                }
            }
        }
    }

    private void downgradeMatrix(double[][] matrix) {
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                continue;
            }

        }
    }


    //print Matrix
    public static String printMatrix(double matrix[][], double[] free, double[][] anti) {
        StringBuilder sb = new StringBuilder("\n");
       /* if (free !=null) {
            sb.append(String.format("%10s %6s %15s\n", "A", "b", "A-1"));
        }*/
        //int count = 0;
        for (int j = 0; j < matrix.length; j++) {
            sb.append("|");
            for (int i = 0; i < matrix.length; i++) {
                sb.append(String.format("%6s", fmt(matrix[j][i])));
            }
            if (free != null) {
                sb.append(String.format("  |%6s", fmt(free[j])));
                sb.append(String.format("%15s", "|"));
                for (int i = 0; i < matrix.length; i++) {
                    sb.append(String.format("%6s", fmt(anti[j][i])));
                }
            }
            sb.append(String.format("%2s\n", "|"));
        }
        return sb.toString();
    }

    public static String fmt(double d) {

        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    public String getDet() {
        return det;
    }

    public String getLog() {
        return log;
    }

    public String[] getRoots() {
        return roots;
    }
}
