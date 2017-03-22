import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment lineSegments[];
                
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("points argument is null.");
        
        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        int length = points.length;
        
        Point pointsCopy[] = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy); // makes sure the combinations are in natural order.
        
        // check for duplicates
        isDuplicate(pointsCopy);
        
        // Make all combinations of points (should be in natural order because of the sort now)
        for (int i = 0; i < length - 4 + 1; i++) {
            for (int j = i + 1; j < length - 2 + 1; j++) {
                for (int k = j + 1; k < length - 2 + 1; k++) {
                    for (int l = k + 1; l < length - 1 + 1; l++) {
                        double slope = pointsCopy[i].slopeTo(pointsCopy[j]);     // Check 1st with 2nd
                        if (slope == pointsCopy[i].slopeTo(pointsCopy[k])) {     // Check 1st with 3rd
                            if (slope == pointsCopy[i].slopeTo(pointsCopy[l])) { // Check 1st with 4th
                                Point p1 = pointsCopy[i]; // because of the sort p1 and p4 should be the end points now.
                                Point p4 = pointsCopy[l];
                                LineSegment line = new LineSegment(p1, p4);
                                segments.add(line);
                            }
                        }
                    }
                }
            }
        }  
        
        lineSegments = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            lineSegments[i] = segments.get(i);
        }
       
    }
    
    private void isDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points.");
            }
        }
        
    }
    
    public int numberOfSegments() {
        return lineSegments.length;
    }
    
    public LineSegment[] segments() {
        return lineSegments;
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
                
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments.        
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("Number of line segments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}