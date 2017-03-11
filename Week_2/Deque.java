import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private int n = 0; // size of the stack.
    private Node first = null;
    private Node last = null;
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    
    public Deque() {}
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size() {
        return n;
    }
    
    public void addFirst(Item item) {
        
        if (item == null) throw new NullPointerException("Cannot Add null Item.");
                
        Node oldFirst = first;
        first = new Node();        
        first.item = item;   
      
        if (last == null) { // initial first item
            first.prev = null;
            first.next = null;
            last = first;            
            last.next = null;            
            last.prev = null;
        } else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        
        n += 1;
    }
    
    public void addLast(Item item) {
        
        if (item == null) throw new NullPointerException("Cannot Add null Item.");
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        
        if (first == null) { // initial first item
            last.prev = null;
            last.next = null;
            first = last;
            first.prev = null;
            first.next = null;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
            last.next = null;
        }
        
        n += 1;
    }
    
    public Item removeFirst() {
        
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
            n -= 1;
            return item;
        }        

        first = first.next;
        n -= 1;
        return item;
    }
    
    public Item removeLast() {
        
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
            n -= 1;
            return item;
        }
        
        last = last.prev;
        last.next = null;
        
        n -= 1;
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("remove is not permitted."); }

        public Item next() {
            if (isEmpty()) throw new NoSuchElementException("Number of elements is none.");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();
        deck.addFirst(1);  
        deck.addFirst(2);  
        deck.addLast(3);
        
        deck.removeLast();
        deck.removeFirst();
        deck.removeFirst();
        
        deck.addLast(5);
        deck.addFirst(10);
        deck.addFirst(20);
        deck.addFirst(40);
        deck.addLast(33);
        deck.removeFirst();
        deck.removeLast();
        
        StdOut.println("++++++");
        for (Integer i : deck) {
            StdOut.println(i);
        }
        StdOut.println("++++++");
       
    }
    
}






















