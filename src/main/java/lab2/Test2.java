package lab2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by john on 13.06.2016.
 */
public class Test2 {
    public static void main(String[] args) {

        double a =(0.91 + (6.7  *  -0.13582089552238807));
        double b = Math.round(1.00085);
        System.out.println(a);
        System.out.println(b);
        System.out.println(a-b);
        BigDecimal a1 = new BigDecimal(6.7).setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal b1 = new BigDecimal(0.91).setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal c1 = b1.divide(a1, 5, RoundingMode.HALF_UP);
        BigDecimal cd1 = a1.multiply(c1).setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal cd2 = cd1.subtract(b1).setScale(4, BigDecimal.ROUND_HALF_UP);
        System.out.println(a1);
        System.out.println(b1);
        System.out.println(c1);
        System.out.println(cd1);
        System.out.println(cd2);


    }


}
