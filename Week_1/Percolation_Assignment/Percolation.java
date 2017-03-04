
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int virtualTop;
    private int virtualBottom;
    private int n; // length of a row
    private boolean[] sites;
    
    private WeightedQuickUnionUF weightedQuickUnion;
    private WeightedQuickUnionUF artificialUnion;
    
    public Percolation(int n) {
        this.n = n;
        sites = new boolean[n*n];
        weightedQuickUnion = new WeightedQuickUnionUF(n * n + 2);
        artificialUnion = new WeightedQuickUnionUF(n * n + 2);
        
        virtualTop = (n * n);
        virtualBottom = (n * n) + 1;
    }
    
    public void open(int row, int col) {
 
        checkOutOfBounds(row, col);      
        int site = xyTo1D(row, col);
        // open the site
        if (!isOpen(row, col)) {
            sites[xyTo1D(row, col)] = true;
        } else {
            return;
        }
        //connect left neighbour
        if (col > 1 && isOpen(row, col - 1)) {
            int leftNeighbour = xyTo1D(row, col - 1);
            weightedQuickUnion.union(site, leftNeighbour);
            artificialUnion.union(site, leftNeighbour);
        }
        //connect right neighbour
        if (col != n && isOpen(row, col + 1)) {
            int rightNeighbour = xyTo1D(row, col + 1);
            weightedQuickUnion.union(site, rightNeighbour);
            artificialUnion.union(site, rightNeighbour);
        }
        //connect top
        
        // if top row, connect to virtual Top node.
        if (row == 1) {
            weightedQuickUnion.union(site, virtualTop);
            artificialUnion.union(site, virtualTop);
        } else if (row - 1 >= 1 && isOpen(row - 1, col)) {
            int topNeighbour = xyTo1D(row - 1, col);
            weightedQuickUnion.union(site, topNeighbour);
            artificialUnion.union(site, topNeighbour);
        }
        //connect bottom
        
        // if bottom row, connect only artificial union to virtual Bottom
        if (row == n) {
            artificialUnion.union(site, virtualBottom);
        } else if (row + 1 <= n && isOpen(row + 1, col)) {
            int bottomNeighbour = xyTo1D(row + 1, col);
            weightedQuickUnion.union(site, bottomNeighbour);
            artificialUnion.union(site, bottomNeighbour);
        }
    }
    
    public boolean isOpen(int row, int col) {
        checkOutOfBounds(row, col);
        return sites[xyTo1D(row, col)];
    }
    
    public boolean isFull(int row, int col) {
        checkOutOfBounds(row, col);
        int site = xyTo1D(row, col);
        return weightedQuickUnion.connected(site, virtualTop);
    }
    
    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 0; i < n*n; i++) {
            if (sites[i] == true)
                counter += 1;
        }
        
        return counter;
    }
    
    public boolean percolates() {
        return artificialUnion.connected(virtualTop, virtualBottom);
    }
    
    // convert 2d to a 1d for a 0 indexed array
    private int xyTo1D(int i, int j) {
        return (n * i) - 1 - (n - j);
    }
        
    private void checkOutOfBounds(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n) {
            StdOut.println("++++++++++++++++++++++");
            throw new IndexOutOfBoundsException("n = " + n + " - Index: " + i + "," + j + " is out of bounds.");
        }
    }
    
    public static void main(String[] args) {
       Percolation perc = new Percolation(3);
       perc.open(1, 1);
       perc.open(2, 1);
       perc.open(3, 1);
       perc.open(3, 3);
       StdOut.println("Should percolate: " + perc.percolates());
       StdOut.println("Number open sites: " + perc.numberOfOpenSites());
    }
    
    
}