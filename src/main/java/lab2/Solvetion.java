package lab2;

/**
 * Created by john on 14.06.2016.
 */
public class Solvetion {
    String [] roots;
    String log;

    public Solvetion(String[] roots, String log) {
        this.roots = roots;
        this.log = log;
    }
    public Solvetion(double root, String log) {
        String [] temp = {root+""};
        roots= temp;
        this.log = log;
    }

    public String[] getRoots() {
        return roots;
    }

    public void setRoots(String[] roots) {
        this.roots = roots;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
