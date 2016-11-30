import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] results; // open result
    private int trial; // number of trials
    private int n; // n*n grid

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trial) { 
        if (n <= 0 || trial <= 0) 
            throw new java.lang.IllegalArgumentException();
        this.n = n;
        this.trial = trial;    
        results = new double[trial];
        Percolation perc;
        for (int k = 0; k < trial; ++k) {
            perc = new Percolation(n);
            int i = 0;
            int j = 0;
            int openNum = 0;
            while (!perc.percolates()) {
                i = StdRandom.uniform(1, n+1);
                j = StdRandom.uniform(1, n+1);
                if (perc.isOpen(i, j)) 
                    continue;
                perc.open(i, j);
                openNum++;
            }
            results[k] = (double) openNum / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() { 

        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() { 
      return StdStats.stddev(results);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() { 
        return mean() - 1.96 * stddev() / Math.sqrt(trial);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() { 
        return mean() + 1.96 * stddev() / Math.sqrt(trial);
    }
 
    public static void main(String[] args) { // test
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.printf("mean                    = %f%n", ps.mean());
        StdOut.printf("stddev                  = %f%n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f%n", ps.confidenceLo(), ps.confidenceHi());
    }
}
