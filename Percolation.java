import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid; // 0 if blocked, 1 if open
    private WeightedQuickUnionUF ufPercolated; // union for percolation
    private WeightedQuickUnionUF ufTop; // union for isFull()
    private int size; // number of sites
    private int n; // n*n grid
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {               
    	if (n <= 0)
    		throw new java.lang.IllegalArgumentException();
    	grid = new boolean[n][n];
    	for (int i = 0; i < n; ++i) {
    		for (int j = 0; j < n; ++j) {
    			grid[i][j] = false;
    		}
    	}
    	this.n = n;
    	size = n * n + 2; // add two virtual nodes on top and bottom
    	ufPercolated = new WeightedQuickUnionUF(size);
    	ufTop = new WeightedQuickUnionUF(size - 1); // only one virtual node on top;
    	for (int i = 1; i <= n; ++i) {
    		ufPercolated.union(0, i);
    		ufPercolated.union(size - 1, size - 1 - i);
    		ufTop.union(0, i);
    	}
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {      
    	validArgs(row, col);
        grid[row - 1][col - 1] = true;
        int idx = posToIndex(row, col);
        if (row > 1 && isOpen(row - 1, col)) {
        	ufPercolated.union(posToIndex(row - 1, col), idx);
        	ufTop.union(posToIndex(row - 1, col), idx);
        }
        if (row < n && isOpen(row + 1, col)) {
        	ufPercolated.union(posToIndex(row + 1, col), idx);
        	ufTop.union(posToIndex(row + 1, col), idx);
        }
        if (col > 1 && isOpen(row, col - 1)) {
        	ufPercolated.union(posToIndex(row, col - 1), idx);
        	ufTop.union(posToIndex(row, col - 1), idx);
        }
        if (col < n && isOpen(row, col + 1)) {
        	ufPercolated.union(posToIndex(row, col + 1), idx);
        	ufTop.union(posToIndex(row, col + 1), idx);
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) { 
    	validArgs(row, col);
        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) { 
    	validArgs(row, col);
    	return isOpen(row, col) && ufTop.connected(posToIndex(row, col), 0);
    }
    
    // does the system percolate?
    public boolean percolates()  {            
    	if (n == 1) 
            return isOpen(1, 1); // corner case
        return ufPercolated.connected(0, size - 1);
    }

    private void validArgs(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new java.lang.IndexOutOfBoundsException();
    }

    private int posToIndex(int row, int col) {
        return (row - 1) * n + col;
    }

}
