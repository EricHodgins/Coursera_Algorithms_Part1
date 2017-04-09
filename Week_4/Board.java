
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
import java.lang.Math;

public class Board {
    private int n; // dimension of the grid
    private int[][] tiles;
    
    public Board(int[][] blocks) {
        this.n = blocks[0].length;
        this.tiles = new int[this.n][this.n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.tiles[row][col] = blocks[row][col];
            }
        } 
    }
       
    public int dimension() {
        return n;
    }
    
    public int manhattan() {
        int goal = 0;
        int mSum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goal = (j + 1) + (i * n);
                if (tiles[i][j] != goal && tiles[i][j] != 0) {
                    // Find goal position
                    int gRow = (tiles[i][j] - 1) / this.n;
                    int gCol = tiles[i][j] - (this.n * gRow) - 1;
                    mSum += Math.abs(i - gRow) + Math.abs(j - gCol);
                }
            }
        }
        
        return mSum;
    }
    
    public int hamming() {
        int goal;
        int wrong = 0;
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goal = (j + 1) + (i * n);
                if (i == n-1 && j == n-1) {
                    goal = 0;
                    break;
                }
                
                if (goal != tiles[i][j]) {
                    wrong += 1;
                }
            }
        }
        
        return wrong;
    }
    
    public boolean isGoal() {
        if (tiles[n-1][n-1] != 0) return false;
        
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 & j == n-1) {
                    blocks[i][j] = 0;
                } else {
                    blocks[i][j] = (j + 1) + (i * n);                    
                }
                
                if (tiles[i][j] != blocks[i][j]) return false;
            }
        }
        
        return true;
    }
        
    public Board twin() {                     
        // copy existing board
        int[][] boardCopy = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                boardCopy[row][col] = tiles[row][col];
            }
        } 
        
        // swap 2 blocks that are not 0
        int row1 = 0;
        int col1 = 0;
        while (boardCopy[row1][col1] == 0) {
            col1 += 1;
        }
        
        int row2 = 0;
        int col2 = 0;
        while (boardCopy[row2][col2] == 0 || boardCopy[row1][col1] == boardCopy[row2][col2]) {
            if (col2 == n-1) {
                col2 = 0;
                row2 += 1;
            } else {
                col2 += 1;
            }                                           
        }

        int temp = boardCopy[row1][col1];
        boardCopy[row1][col1] = boardCopy[row2][col2];
        boardCopy[row2][col2] = temp;
        
        return new Board(boardCopy);
    }        
    
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        
        // find the 0 position; to swap around it
        int idxI = -1;
        int idxJ = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    idxI = i;
                    idxJ = j;
                }
            }
        }
        
        // top
        if (idxI != 0) {
            int iNew = idxI - 1;
            Board top = makeNeighbor(iNew, idxJ);
            boards.add(top);
        }
        
        // bottom
        if (idxI < n - 1) {
            int iNew = idxI + 1;
            Board bottom = makeNeighbor(iNew, idxJ);
            boards.add(bottom);
        }
        
        // left
        if (idxJ > 0) {
            int jNew = idxJ - 1;
            Board left = makeNeighbor(idxI, jNew);
            boards.add(left);
        }
        
        // right
        if (idxJ < n - 1) {
            int jNew = idxJ + 1;
            Board right = makeNeighbor(idxI, jNew);
            boards.add(right);
        }
        
        return boards;
    }
    
    private Board makeNeighbor(int rI, int rJ) {
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blocks[i][j] = tiles[rI][rJ];
                    blocks[rI][rJ] = 0;
                } else {
                    if (i != rI || j != rJ) blocks[i][j] = tiles[i][j];
                }
            }
        }
        
        Board board = new Board(blocks);
        return board;
    }
    
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");            
        }     
        return s.toString();
    }
    
    public static void main(String[] args) {}
}