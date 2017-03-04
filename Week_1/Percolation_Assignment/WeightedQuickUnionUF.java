
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF {
    
        private int id[];
        private int sz[];
        
        public WeightedQuickUnionUF(int N) {
            id = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
        }
        
        private int root(int i) {
            while (id[i] != i) i = id[i];
            return i;
        }
        
        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }
        
        public void union(int p, int q) {
            int i = root(p);
            int j = root(q);
            if (i == j) return ;
            if (sz[i] < sz[j]) {
                id[i] = j;
                sz[j] += sz[i];
            } else {
                id[j] = i;
                sz[i] += sz[j];
            }
        }
    
        public static void main(String[] args) {
            StdOut.println("Begin...");
            int N = StdIn.readInt();
            WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(N);
            while (!StdIn.isEmpty()) {
                int p = StdIn.readInt();
                int q = StdIn.readInt();
                if (!wqu.connected(p, q)) {
                    wqu.union(p, q);
                    StdOut.println(p + " " + q);
            }
        }
        
    }
}