
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnion {
    
    private int[] id;
    
    public QuickUnion(int N) {
        id = new int[N];    
        for (int i = 0; i < N; i++) id[i] = i;
    }
    
    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }
    
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
    
    public static void main(String[] args) {
        StdOut.println("Begin...");
        int N = StdIn.readInt();
        QuickUnion qu = new QuickUnion(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!qu.connected(p, q)) {
                qu.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
        
    }
}