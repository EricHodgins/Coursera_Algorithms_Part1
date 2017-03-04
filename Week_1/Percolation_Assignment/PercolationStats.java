
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double thresholdArray[];
    private int T;
    
    public PercolationStats(int n, int trials) {
        this.T = trials;
        int row = 0;
        int col = 0;
        
        thresholdArray = new double[trials];
        
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                // randomly open sites
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                
                perc.open(row, col);
            }
            
            thresholdArray[i] = 1.0 * perc.numberOfOpenSites() / (n * n);
        }
    }
    
    public double mean() {
        return StdStats.mean(thresholdArray);
    }
    
    public double stddev() {
        return StdStats.stddev(thresholdArray);
    }
    
    public double confidenceLo() {
        return mean() - ((1.96*stddev()) / Math.sqrt(T));
    }
    
    public double confidenceHi() {
        return mean() + ((1.96*stddev()) / Math.sqrt(T));
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats percStats = new PercolationStats(n, trials);
        
        //mean
        StdOut.println("mean = " + percStats.mean());
        
        //Standard deviation
        StdOut.println("stddev = " + percStats.stddev());
        
        //95% confidence Interval
        double lo = percStats.confidenceLo();
        double hi = percStats.confidenceHi();
        StdOut.println("95% confidence interval = [" + lo + ", " + hi + "]");
    }
}