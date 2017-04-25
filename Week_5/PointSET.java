
import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.TreeSet;

public class PointSET {
    
    private TreeSet<Point2D> points;
    
    public PointSET() {
        this.points = new TreeSet<Point2D>(pointComparator);
    }
    
    public boolean isEmpty() {
        if (size() == 0) return true;
        return false;
    }
    
    public int size() {
        return points.size();
    }
    
    public void insert(Point2D p) {        
        points.add(p);
    }
    
    public boolean contains(Point2D p) {
        return points.contains(p);
    }
    
    public void draw() {
        StdDraw.setPenRadius(0.01);
        for (Point2D p : points) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        double minX = rect.xmin();
        double minY = rect.ymin();
        double maxX = rect.xmax();
        double maxY = rect.ymax();
        Point2D minPt = new Point2D(minX, minY);
        Point2D maxPt = new Point2D(maxX, maxY);
        
        TreeSet<Point2D> treeSubSet = new TreeSet<Point2D>(pointComparator);

        for (Point2D p : points) {
            if (p.x() >= minX && p.x() <= maxX && p.y() >= minY && p.y() <= maxY) {
                treeSubSet.add(p);
            }
        }
        
        return treeSubSet;
    }
    
    public Point2D nearest(Point2D p) {
        double distance = Double.POSITIVE_INFINITY;
        double temp = Double.POSITIVE_INFINITY;
        Point2D pt = null;
        for (Point2D currentPt : points) {
            temp = p.distanceTo(currentPt);
            if (temp < distance) {
                distance = temp;
                pt = currentPt;
            }
        }
        
        return pt;
    }
    
    private static Comparator<Point2D> pointComparator = new Comparator<Point2D>() {
        @Override 
        public int compare(Point2D p1, Point2D p2) {
            double xDiff = p1.x() - p2.x();
            double yDiff = p1.y() - p2.y();
            
            if (xDiff > 0) return 1;
            if (xDiff < 0) return -1;                
            if (yDiff > 0) return 1;
            if (yDiff < 0) return -1;
            
            return 0;
        }
    };
    
    public static void main(String[] args) {
        // Initialize a PointSET
        PointSET ps = new PointSET();
        
        // Read in Point2D values
        In in = new In(args[0]);
        int n = 10;
        for (int i = 0; i < n; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            ps.insert(p);
        }
        ps.draw();
        
        // Create a Rectangle 
        RectHV rect = new RectHV(0.0, 0.3, 1.0, 0.6);
        StdDraw.setPenColor(StdDraw.RED);
        rect.draw();
        for (Point2D p : ps.range(rect)) {  
            StdDraw.setPenColor(StdDraw.BLUE);
            p.draw();
            StdOut.println(p);
        }
        
        // Create a Query Point
        Point2D query = new Point2D(0.9, 0.9);
        StdDraw.setPenColor(StdDraw.RED);
        query.draw();
        Point2D closest = ps.nearest(query);
        StdDraw.setPenColor(StdDraw.GREEN);
        closest.draw();
        
        // Test Contains
        StdOut.println(ps.contains(new Point2D(0.5, 0.5)));
    }
        
}