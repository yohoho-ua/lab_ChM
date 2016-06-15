package lab1.math;

import java.util.GregorianCalendar;

/**
 * Created by john on 21.05.2016.
 */
public class Test {

    public static void main(String[] args) {
    GregorianCalendar calendar = new GregorianCalendar(1940,0,1,0,1,0);
        System.out.println(calendar.getTime());
        long time = calendar.getTimeInMillis();
        int [] count = new int [100];
        for (int j = 0; j < 30000; j++) {
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(GregorianCalendar.YEAR));
        sb.append(calendar.get(GregorianCalendar.MONTH)+1);
        sb.append(calendar.get(GregorianCalendar.DAY_OF_MONTH));
       // System.out.println(sb);

            int sum = 0;
            for (int i = 0; i < sb.length(); i++) {
                sum = sum + Integer.parseInt(sb.charAt(i) + "");
            }
            if (sum > 9) {
                int a = sum/10;
                int b = sum%10;
                sum = a+b;
            }
            if (sum > 9) {
                int a = sum/10;
                int b = sum%10;
                sum = a+b;
            }
            if (sum > 9) {
                int a = sum/10;
                int b = sum%10;
                sum = a+b;
            }
            count[sum]++;
            //System.out.println(sum);
            calendar.setTimeInMillis(time+1000*60*60*24);
            time = calendar.getTimeInMillis();
        }

        for (int i = 0; i < count.length; i++) {
            System.out.printf("\ni = %d, value = %d", i, count[i]);

        }
    }
}
