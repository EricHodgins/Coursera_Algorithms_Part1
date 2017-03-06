import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first = null;
    private Node last = null;
    private class Node {
        Item item;
        Node next;
    }
    
    public Deque() {}
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size() {
        if (isEmpty()) return 0;
        
        if (first == last) return 1;
        
        Node current = last;
        int counter = 1;
        while (current.next != null) {
            counter += 1;
            current = current.next;
        }
        
        return counter;
    }
    
    public void addFirst(Item item) {
        
        if (item == null) throw new NullPointerException("Cannot Add null Item.");
         
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = null;
        
        if (last == null) {
            last = first;
        } else {
            oldFirst.next = first;
        }
    }
    
    public void addLast(Item item) {
        
        if (item == null) throw new NullPointerException("Cannot Add null Item.");
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        
        if (isEmpty()) first = last;
        else last.next = oldLast;
    }
    
    public Item removeFirst() {
        
        if (isEmpty()) throw new NoSuchElementException("Cannot remove items from an empty Deque.");
        
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
            return item;
        }        
                
        Node current = last;
        
        while (current.next != null) {
            if (current.next.next == null) first = current;  
            current = current.next;  
        }       
        
        first.next = null;
        return item;
    }
    
    public Item removeLast() {
        
        if (isEmpty()) throw new NoSuchElementException("Cannot remove items from an empty Deque.");
        
        Item item = last.item;
        last = last.next;
        
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = last;
        
        public boolean hasNext() { return current != null; }
        public void remove() { /* not supported */ }
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> deck = new Deque<Integer>();
        deck.addFirst(1);
        deck.addFirst(2);
        
        StdOut.println("First item: " + deck.first.item);
        StdOut.println("Last item : " + deck.last.item);
        StdOut.println("Size: " + deck.size());
        
        StdOut.println(deck.removeLast());
        StdOut.println("Size: " + deck.size());
        deck.addLast(9);
        
        StdOut.println("++++++");
        for (Integer i : deck) {
            StdOut.println(i);
        }
    }
    
}






















