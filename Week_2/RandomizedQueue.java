
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


// in: to -> be -> or -> not
// First = to
// last = not

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int queueSize;
    private Node first;
    private class Node {
        Item item;
        Node next;
    }
    
    public RandomizedQueue() {
        this.queueSize = 0;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size() {
        return this.queueSize;
    }
    
    public void enqueue(Item item) {                
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        queueSize += 1;
    }
    
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        // get random index between 0 and size-1
        int randIdx = StdRandom.uniform(size());
        
        if (size() == 1) {
            Item item = first.item;
            first = null;
            this.queueSize = 0;
            return item;
        }
        
        if (randIdx == 0) {
            Item item = first.item;
            first = first.next;    
            this.queueSize -= 1;
            return item;
        }
        
        if (randIdx == size()-1) {
            Item item;
            Node current = first;
            for (int i = 0; i < randIdx-1; i++) {
                current = current.next;
            }
            item = current.next.item;
            current.next = null;
            this.queueSize -= 1;
            return item;
        }
        
        Node current = first;        
        for (int i = 0; i < randIdx-1; i++) {
            current = current.next;
        }
        Item item = current.next.item;
        current.next = current.next.next;
        this.queueSize -= 1;
        return item;
    }
    
    public Item sample() {
        int randIdx = StdRandom.uniform(size());
        
        Node current = first;
        for (int i = 0; i < randIdx; i++) 
            current = current.next;
        return current.item;
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    
    private class ListIterator implements Iterator<Item> {
        private Item[] randomArray = generateItemArray();
        private int index = -1;
        
        public boolean hasNext() { return index < size()-1; }
        public void remove() { throw new UnsupportedOperationException("remove is not permitted."); }

        public Item next() {
            if (isEmpty()) throw new NoSuchElementException("Number of elements is none.");
            index += 1;
            return randomArray[index];
        }
    }
    
    private Item[] generateItemArray() {
        Item[] items = (Item[]) new Object[this.queueSize];
        
        Node current = first;
        for (int i = 0; i < this.queueSize; i++) {
            items[i] = current.item;
            current = current.next;
        }
        
        StdRandom.shuffle(items);
        
        return items;
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> qu = new RandomizedQueue<Integer>();
        
        qu.enqueue(1);
        qu.enqueue(2);
        qu.enqueue(3);

        for (Integer i : qu) {
            StdOut.println(i);
        }
        
        StdOut.println(qu.sample());
    }
    
}














