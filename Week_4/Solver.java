

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private ArrayList<Board> stack = new ArrayList<Board>();
    
    private int moves = 0;
    private MinPQ<Node> pq;
    private MinPQ<Node> swapPQ;
    private boolean solvable = false;
    private boolean unsolvable = false;
    private Node endNode;
    
    private class Node {
        private Board board;
        private Node parent;
        
        public Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
        }
    }
    
    public Solver(Board initial) {
        pq = new MinPQ<Node>(boardComparator);
        swapPQ = new MinPQ<Node>(boardComparator);
        Node currentNode = new Node(initial, null);
        Node currentTwinNode = new Node(initial.twin(), null);        
        pq.insert(currentNode);
        swapPQ.insert(currentTwinNode);

        while (!solvable && !unsolvable) {
            currentNode = pq.delMin();            
            addNeighborsToPQ(currentNode, pq);
            
            currentTwinNode = swapPQ.delMin();
            addNeighborsToPQ(currentTwinNode, swapPQ);

            solvable = currentNode.board.isGoal();
            unsolvable = currentTwinNode.board.isGoal();
        }
        
        endNode = currentNode;
    }
    
    private void addNeighborsToPQ(Node node, MinPQ<Node> q) {
        for (Board b : node.board.neighbors()) {                        
            if (node.parent == null || node.parent.parent == null) { // 1st and 2nd node insertions
                Node n = new Node(b, node);
                q.insert(n);
            } else if (!b.equals(node.parent.board) && !b.equals(node.parent.parent.board)) {
                Node n = new Node(b, node);
                q.insert(n);
            }
        }
    }    
    
    public boolean isSolvable() {
        return solvable;
    }
    
    static private int nodeMoves(Node node) {
        int moves = 0;
        Node current = node;   
        while (current.parent != null) {
            current = current.parent;
            moves += 1;
        }
        return moves;
    }
    
    public int moves() {
        if (!solvable) return -1;
        if (moves > 0) return moves;
        Node current = endNode;
        while(current.parent != null) {
            current = current.parent;
            moves += 1;
        }
        
        return moves;
    }
    
    private static Comparator<Node> boardComparator = new Comparator<Node>() {
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.board.manhattan() + nodeMoves(n1)) - (n2.board.manhattan() + nodeMoves(n2));
        }
    };
    
    public Iterable<Board> solution() {
        if (!solvable) return null;
        if (stack.size() != 0) return stack;
        
        Node current = endNode;
        while(current != null) {
            stack.add(0, current.board);
            current = current.parent;
        }

        return stack;
    }
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        
        Board initial = new Board(blocks);  
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }
    
}