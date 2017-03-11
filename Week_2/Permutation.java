
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        
        int sequenceLength = Integer.parseInt(args[0]);
        int counter = 0;
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        
       
        for (String s : rq) {
            if (counter == sequenceLength) 
                break;
            StdOut.println(s);
            counter += 1;
        }
    }
}