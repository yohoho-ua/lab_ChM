package lab1.math;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;


class PostFixCal {
    public static Double calc(List<String> postfix, double var) {
        //System.out.println(postfix);
        Deque<Double> stack = new ArrayDeque<Double>();
        for (String x : postfix) {
            if (x.equals("sqrt")) stack.push(Math.sqrt(stack.pop()));
            else if (x.equals("sin")) stack.push(Math.sin(stack.pop()));
            else if (x.equals("cos")) stack.push(Math.cos(stack.pop()));
            else if (x.equals("tan")) stack.push(Math.tan(stack.pop()));
            else if (x.equals("atan")) stack.push(Math.atan(stack.pop()));
            else if (x.equals("asin")) stack.push(Math.asin(stack.pop()));
            else if (x.equals("acos")) stack.push(Math.acos(stack.pop()));
            else if (x.equals("cube")) {
                Double tmp = stack.pop();
                stack.push(tmp * tmp * tmp);
            } else if (x.equals("pow10")) stack.push(Math.pow(10, stack.pop()));
            else if (x.equals("^")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(Math.pow(a, b));
            } else if (x.equals("+")) stack.push(stack.pop() + stack.pop());
            else if (x.equals("-")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            } else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
            else if (x.equals("/")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a / b);
            } else if (x.equals("u-")) stack.push(-stack.pop());
            else if (x.equals("pi")) stack.push(Math.PI);
            else if (x.equals("E")) stack.push(Math.E);
            else {
                try {
                    stack.push(Double.valueOf(x));
                } catch (NumberFormatException ex) {
                    stack.push(var);
                    //System.out.println("Catched " + x);
                }
            }
        }

        return stack.pop();


    /*public static void main (String[] args) {
       // Scanner in = new Scanner(System.in);
        //String s = in.nextLine();
        String s = "(5 + 2^4) *2";
        ExpressionParser n = new ExpressionParser();
        List<String> expression = n.parse(s);
        boolean flag = n.flag;
        if (flag) {
            for (String x : expression) System.out.print(x + " ");
            System.out.println();
            System.out.println(calc(expression));
        }
    }*/
    }
}