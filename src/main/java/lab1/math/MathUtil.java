package lab1.math;

import java.util.ArrayList;

import static lab1.math.PostFixCal.calc;

/**
 * Created by zim on 16.04.2016.
 */
public class MathUtil {


    public static ArrayList<Double> getPoints(String formulaString,
                                              double minX, double maxX, double precision) {
        ArrayList<Double> points = new ArrayList<>();
        ExpressionParser n = new ExpressionParser();
        java.util.List<String> expression = n.parse(formulaString);
        boolean flag = n.flag;
        if (flag) {

            for (double i = minX; i <= maxX; i+=precision) {
                Double curY = calc(expression, i);
                    points.add(i);
                    points.add(curY);
            }
                    }
        return points;
    }
}