package lab2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by john on 12.06.2016.
 */
public class TestMatrix {
    public static void main(String[] args) throws Exception {
        File file2 = new File("matrix2.txt");
        File file3 = new File("matrix3.txt");
        File file33 = new File("matrix33.txt");
        File file5 = new File("matrix5.txt");
        File fileMain = new File("matrix5Main.txt");

        ArrayList <String []> strings = new ArrayList<>();

        Scanner input = new Scanner(fileMain);
        while(input.hasNext()) {
            //System.out.println(input.nextLine());
            strings.add(input.nextLine().split(","));
        }
        int matrixRang = strings.size();

        double [][] matrix = new double[strings.size()][];
        double [] free = new double[strings.size()];

        //fill matrix
            int startRow = 0;
        for (String[] string : strings) {
            double [] temp = new double[matrixRang];
            int cureentLength = string.length;
            if (matrixRang == cureentLength-1) {
            for (int i = 0; i <matrixRang ; i++) {
                temp[i] = Double.parseDouble(string[i]);
            }
                free[startRow] = Double.parseDouble(string[cureentLength-1]);
                matrix[startRow++] = temp;
            } else {
                throw new Exception("wrong input");
            }
        }


        //show matrix
        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                System.out.print(aDouble+" ");
            }
            System.out.println();
        }

        Matrix mat = new Matrix(matrix, free);
        mat.solve();

        System.out.println(mat.getLog());
        System.out.println("\n Det = " + mat.getDet());
        String [] roots = mat.getRoots();
        for (String root : roots) {
        System.out.print(root + "  ");

        }
    }

}
