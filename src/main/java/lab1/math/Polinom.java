package lab1.math;

import java.util.ArrayList;


public class Polinom {
    private double precision;
    private int[] coefs;
    private double paramA;
    private double paramK;

    public Polinom(double precision, int[] coefs, double paramA, double paramK) {
        this.precision = precision;
        this.coefs = coefs;
        this.paramA = paramA;
        this.paramK = paramK;
    }

    public double calc(double x) {
        double result =
                coefs[0] * (1 + paramA) * Math.pow(x, 5) +
                        coefs[1] * Math.pow(x, 4) +
                        coefs[2] * Math.pow(x, 3) +
                        coefs[3] * x * x +
                        coefs[4] * x +
                        coefs[5] * paramK;

        return result;
    }

    public double derivative (double x) {
        double result =
                coefs[0] * (1 + paramA) * 5 * Math.pow(x, 4) +
                        coefs[1] * 4 * Math.pow(x, 3) +
                        coefs[2] * 3 * x * x +
                        coefs[3] * 2 * x +
                        coefs[4] * 1;

        return result;
    }

    public double derivative2 (double x) {
        double result =
                coefs[0] * (1 + paramA) * 5 * 4 *Math.pow(x, 3) +
                        coefs[1] * 4 * 3 * x * x +
                        coefs[2] * 3 * 2 * x +
                        coefs[3] * 2;

        return result;
    }

    public double[] getSolutionBy(int method, double a, double b) {
        ArrayList<Double> temp = new ArrayList<>();
        switch (method) {
            case 1:
                bisect(a, b, temp);
                break;
            case 2:
                hord(a, b, temp);
                break;
            case 3:
                newton(a, b, temp);
                break;
        }
        double[] list = new double[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            list[i] = temp.get(i);
        }
        return list;
    }


    private void bisect(double a, double b, ArrayList<Double> list) {
        if (list.size() > 0 && Math.abs(list.get(list.size() - 1)) < precision) {
            return;
        }
        double c = (a + b) / 2;
        double fa = calc(a);
        double fc = calc(c);
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(fa);
        list.add(fc);
        list.add(a - c);
        if (fa * fc < 0) {
            bisect(a, c, list);
        } else bisect(c, b, list);
    }

    private void hord(double a, double b, ArrayList<Double> list) {
        double x = getHorda(a, b);

        if (list.size() == 0) {
            if (calc(x) * calc(b) < 0) {
                a = b;
            }
        } else {
            if (Math.abs(b - x) < precision) {
                list.add(x);
                list.add(calc(x));
                list.add(Math.abs(b - x));
                return;
            }
        }

        list.add(x);
        list.add(calc(x));
        list.add(Math.abs(b - x));
        hord(a, x, list);
    }

    private void newton(double a, double b, ArrayList<Double> list) {
        if (list.size() == 0) {
            if (calc(a) * derivative2(a) < 0) {
                a = b;
            }
        }
            else {
                if (list.get(list.size()-1) < precision) {
                    return;
                }
            }
        double temp = a;
        list.add(a);
        list.add(calc(a));
        a = a-calc(a)/derivative(a);
        list.add(Math.abs(temp-a));
        newton (a, b, list);
    }

    private double getHorda(double a, double b) {
        return a - (b - a) / (calc(b) - calc(a)) * calc(a);
    }

    //for testing
    public static void main(String[] args) {
        int[] cfs = {1, 0, 0, 3, -2, -1};
        Polinom polinom = new Polinom(0.00001, cfs, 8, 1);
        /*System.out.printf("\n%5s %10s %10s %10s %10s %10s %12s","iteration", "a","b","c","f(a)","f(c)", "precision");
        System.out.println("\n____________________________________________________________________________________");
        double [] results = polinom.getSolutionBy(1,-2,-1);
        for (int i = 0, j=1; i < results.length; i++, j++) {
            System.out.printf("\n%10d %10.5f %10.5f %10.5f %10.5f %10.5f %12.8f", j,
                    results[i++], results[i++], results[i++], results[i++], results[i++], results[i]);
        }
        System.out.println("\n____________________________________________________________________________________");*/

        double[] results = polinom.getSolutionBy(3, -1,-0.5);
        for (int i = 0; i < results.length; i++) {
            System.out.printf("\n%15.5f  %15.5f %15.5f", results[i++], results[i++], results[i]);
        }

    }
}
