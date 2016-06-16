package lab2;

import java.math.BigDecimal;

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
    private String log = "";
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
        log +=(printMatrix(matrix, free, antiMatrix));

        gaussForward();
        calcDetN();
        gaussBackward();
        reduce();

        roots = new String[rang];
        for (int i = 0; i < rang; i++) {
            roots[i] = fmt(free[i],10);
        }



    }

    private void gaussForward() {
        for (int i = 0; i < rang-1; i++) {
            for (int j = i+1; j <rang; j++) {
                double main = matrix [i][i];
                double current = matrix [j][i];
                /*if (current==0) {
                    continue;
                }*/
                //System.out.print(j+", " + i + "  ");
                //if(main > 0) {
                perform((current/main)*(-1),j, i);
                checkRows();
                //} else {
               //     perform(current/main, j, i);
               // }
            }
         //   System.out.println();
        }
    }

    private void gaussBackward() {
        double main = 0;
        double current = 0;
        for (int i = rang-1; i > 0; i--) {
            for (int j = i-1; j >=0; j--) {
                try {
                     main = matrix[i][i];
                     current = matrix[j][i];
                } catch (Exception e) {
                    System.out.println("i = " + i + ", matrix size = " + matrix.length + ", matrix [] size = " + matrix[0].length);
                }
               /* if (current==0) {
                    continue;
                }*/
               // System.out.print(j+", " + i + "  ");
                perform((current/main)*(-1),j, i);
                checkRows();
            }
        }
    }

    private void reduce() {
        double divisor =1;
        for (int i = 0; i < rang; i++) {
                 divisor = matrix[i][i];
            for (int j = 0; j < rang; j++) {
                matrix[i][j] /= divisor;
                antiMatrix[i][j] /= divisor;
            }
            free[i] /= divisor;
        }
        log = log + printMatrix(matrix, free, antiMatrix);
    }

    private void perform(double d, int curRow, int mainRow) {
        //add d to every member of definite row matrix, free and anti
        for (int i = 0; i < rang; i++) {
           // System.out.println(curRow + "   " + matrix[curRow][i] + " + " + matrix[mainRow][i] + "  *  " + d + " = " + (matrix[curRow][i] + matrix[mainRow][i]*d));
            BigDecimal result = new BigDecimal(matrix[curRow][i] + matrix[mainRow][i]*d);
           matrix[curRow][i] = result.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            BigDecimal resultAnti = new BigDecimal(antiMatrix[curRow][i] + antiMatrix[mainRow][i]*d);
            antiMatrix[curRow][i] = resultAnti.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        BigDecimal resultFree = new BigDecimal(free[curRow] +free[mainRow]*d);
        free[curRow] =resultFree.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            log = log + String.format("\nДо рядка %d додаємо рядок %d помножений на %s :\n",
                    curRow+1, mainRow+1, fmt(d, 6)) +
                    printMatrix(matrix, free, antiMatrix);

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

        if (rang == 1) {
            det = fmt(matrix[0][0]);
        } else if (rang == 2) {
            calDet2();
        } else if (rang==3) {
            calDet3();
        } else {
            calcDetN();
        }

        //n>2 matrix
        checkFirstElement(matrix);


    }

    private void calcDetN() {
        BigDecimal det = new BigDecimal(1);
        for (int i = 0; i < rang; i++) {
                det = det.multiply(new BigDecimal(matrix[i][i]));
          //  }
        }
        this.det = fmt(det.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue());
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




    //print Matrix
    public static String printMatrix(double matrix[][], double[] free, double[][] anti) {
        StringBuilder sb = new StringBuilder("\n");
        for (int j = 0; j < matrix.length; j++) {
            sb.append("|\t");
            for (int i = 0; i < matrix[0].length; i++) {
                sb.append(String.format("%-15s", fmt(matrix[j][i], 6)));
            }
            if (free != null) {
                sb.append(String.format("  |  %-30s|", fmt(free[j], 8)));
               // sb.append(String.format("%s", "|"));
                for (int i = 0; i < matrix[0].length; i++) {
                    sb.append(String.format("%s\t\t", fmt(anti[j][i], 6)));
                }
            }
            sb.append(String.format("%2s\n", "|"));
        }
        return sb.toString();
    }
    public static String fmt(double d) {
        return fmt(d, 4);
    }

    public static String fmt(double d, int precision) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else {
            String temp = String.format("%s", d);
            return temp.length()>precision?temp.substring(0,precision):temp;
        }
    }

    private void checkRows() {
        for (int i = 0; i < rang; i++) {
            double sum = 0;
            for (int j = 0; j < rang; j++) {
                sum += matrix[i][j];
                }
            if (sum == 0) {
                removeRow(i);
                rang -=1;
                log = log + "\nРядок № " + (i+1) + " нульовий, видалення : " + printMatrix(matrix, free, antiMatrix);
            }
            }
        }

    private void removeRow(int row) {
        int oldRang = rang;
        double [] tempFree = new double[rang-1];
        double [][] tempAnti = new double[rang-1][];
        double [][] tempMatrix = new double[rang-1][];
        for (int i = 0,j = 0; i < oldRang; i++, j++) {
            if (i == row) {
                j--;
                continue;
            }
            tempMatrix[j] = matrix[i];
            tempFree[j] = free[i];
            tempAnti[j] = antiMatrix[i];

        }
        matrix = tempMatrix;
        free = tempFree;
        antiMatrix = tempAnti;
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
