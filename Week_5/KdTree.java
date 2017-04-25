
import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    
    private Node root;
    private int size;
    
    private static class Node {
       private Point2D p;
       private RectHV rect;
       private Node lb;
       private Node rt;
       private boolean isVertical;
       
       public Node(Point2D p, boolean isVertical) {
           this.p = p;
           this.isVertical = isVertical;
       }
    }
    
    public KdTree() {
        RectHV rect1 = new RectHV(0.0, 0.0, 1.0, 1.0);
    }
    
    public boolean isEmpty() {
        if (root == null) return true;
        return false;
    }
    
    public int size() {
        if (isEmpty()) return 0;
        //return KdTrace(root);
        return this.size;
    }
                     
    public void insert(Point2D p) {                        
        if (root == null) {  
            this.size += 1;
            Node node = new Node(p, true);
            node.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            this.root = node;
        } else {           
            insert(p, root, null, true);
        }
    }
    
    private void insert(Point2D p, Node current, Node previous, boolean isVertical) {                    
        // finally hit a null point, so add new Node.
        if (current == null) {
            this.size += 1;
            Node node = new Node(p, isVertical);
            if (!isVertical) {
                if (p.x() < previous.p.x()) {
                    node.rect = new RectHV(previous.rect.xmin(), previous.rect.ymin(), previous.p.x(), previous.rect.ymax()); 
                    previous.lb = node;
                } else {
                    node.rect = new RectHV(previous.p.x(), previous.rect.ymin(), previous.rect.xmax(), previous.rect.ymax());
                    previous.rt = node;
                }
            } else {
                if (p.y() < previous.p.y()) {
                    node.rect = new RectHV(previous.rect.xmin(), previous.rect.ymin(), previous.rect.xmax(), previous.p.y());
                    previous.lb = node;
                } else {
                    node.rect = new RectHV(previous.rect.xmin(), previous.p.y(), previous.rect.xmax(), previous.rect.ymax());
                    previous.rt = node;                
                }
            }
            return;
        }
        
        //  if node already exists
        if (current.p.x() == p.x() && current.p.y() == p.y()) {
            return;
        }
        
           
        // Keep recursively searching kdTree for a null node.
        if (isVertical) {
            if (p.x() < current.p.x()) {
                insert(p, current.lb, current, !isVertical);
            }else {
                insert(p, current.rt, current, !isVertical);
            }
        } else {
            if (p.y() < current.p.y()) {
                insert(p, current.lb, current, !isVertical);
            } else {
                insert(p, current.rt, current, !isVertical);
            }
        }
    }
        
    public boolean contains(Point2D p) {
       return contains(root, p, true); 
    }
    
    private boolean contains(Node current, Point2D p, boolean isVertical) {
        if (current == null) return false;
        else if (current.p.x() == p.x() && current.p.y() == p.y()) return true;
                
        if (isVertical) {
            if (p.x() < current.p.x()) return contains(current.lb, p, !isVertical);
            else return contains(current.rt, p, !isVertical);
        } else {
            if (p.y() < current.p.y()) return contains(current.lb, p, !isVertical);
            else return contains(current.rt, p, !isVertical);
        }
    }
    
    public void draw() {
        StdDraw.setPenRadius(0.01);
        draw(root);
    }
    
    private void draw(Node node) {
        if (node == null) return;
        node.p.draw();
        
        //need to setup the rect for each point now.
        if (node.isVertical) {
            StdDraw.setPenRadius(0.002);
            StdDraw.setPenColor(StdDraw.RED);                               
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenRadius(0.002);
            StdDraw.setPenColor(StdDraw.BLUE);                               
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());            
        }
        
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);                               
     
        draw(node.lb);
        draw(node.rt);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        findRange(rect, root, points);
        return points;
    }
    
    private void findRange(RectHV rect, Node node, ArrayList<Point2D> points) {
        // if 0 points are in range or searched all Points
        if (node == null) return;
        
        // Point is in the rect        
        if (rect.contains(node.p)) {
            points.add(node.p);            
        }
                        
        // Find if Vertical or Horizontal line intersects
        if (node.lb != null && node.lb.rect.intersects(rect)) {
            findRange(rect, node.lb, points);
        }
        
        if (node.rt != null && node.rt.rect.intersects(rect)) {
            findRange(rect, node.rt, points);
        }                                    
    }
    
    public Point2D nearest(Point2D p) {
        return nearest(p, root, root.p);        
    }
    
    private Point2D nearest(Point2D query, Node node, Point2D champion) {
        if (node == null) {
            return champion;
        }
                
        if (node.p.distanceTo(query) < champion.distanceTo(query)) {
            champion = node.p;            
        }            
        
        if (node.isVertical) {
            if (node.p.x() >= query.x()) {
                champion = nearest(query, node.lb, champion);
                if (node.rt != null && node.p.distanceSquaredTo(champion) >= node.rt.rect.distanceSquaredTo(champion)) {
                    champion = nearest(query, node.rt, champion);                
                }
                return champion;
            } else {
                champion = nearest(query, node.rt, champion);       
                if (node.lb != null && node.p.distanceSquaredTo(champion) >= node.lb.rect.distanceSquaredTo(champion)) {
                    champion = nearest(query, node.lb, champion);            
                }
                return champion;
            }
        } else {
            if (node.p.y() >= query.y()) {
                champion = nearest(query, node.lb, champion);
                if (node.rt != null && node.p.distanceSquaredTo(champion) >= node.rt.rect.distanceSquaredTo(champion)) {
                    champion = nearest(query, node.rt, champion);                
                }
                return champion;
            } else {
                champion = nearest(query, node.rt, champion);
                if (node.lb != null && node.p.distanceSquaredTo(champion) >= node.lb.rect.distanceSquaredTo(champion)) {
                    champion = nearest(query, node.lb, champion);                
                }
                return champion;
            }
        }
    }
    
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        Point2D p6 = new Point2D(0.5, 0.5);
 
        tree.insert(p1);        
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        
        //StdOut.println(tree.size());
//        StdOut.println(tree.contains(p1));        
//        StdOut.println(tree.contains(p2));
//        StdOut.println(tree.contains(p3));
//        StdOut.println(tree.contains(p4));
//        StdOut.println(tree.contains(p5));
//        StdOut.println(tree.contains(p6));
        
        //StdOut.println(tree.nearest(p6));
        
        RectHV rect = new RectHV(0.001, 0.001, 0.61, 0.8);
//        
        for (Point2D p : tree.range(rect)) {
            StdOut.println(p + ": in rect.");
        }
        
        //tree.testTree();
    }
}












