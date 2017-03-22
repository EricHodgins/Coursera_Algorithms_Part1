import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] allLineSegments;
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("points argument is null.");
        
        // check for duplicates
        isDuplicate(points);
        
        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        Point sortedPoints[] = Arrays.copyOf(points, points.length); 
        
        
        for (Point point : points) {
            Point origin = point;
            Arrays.sort(sortedPoints);
            Arrays.sort(sortedPoints, origin.slopeOrder());
                                                                               
            ArrayList<Point> segmentPts = new ArrayList<Point>();
            segmentPts.add(origin);
            double previousSlope = Double.NEGATIVE_INFINITY;
            double currentSlope = 0;
            
            for (int i = 1; i < sortedPoints.length; i++) {   
                currentSlope = origin.slopeTo(sortedPoints[i]);
                if (previousSlope == currentSlope) {                    
                    segmentPts.add(sortedPoints[i - 1]);
                    if (i == sortedPoints.length - 1) {
                        segmentPts.add(sortedPoints[i]);
                        attemptToAddLineSegment(segmentPts, segments);
                    }
                } else {                    
                    segmentPts.add(sortedPoints[i - 1]);
                    attemptToAddLineSegment(segmentPts, segments);
                    
                    segmentPts = new ArrayList<Point>();
                    segmentPts.add(origin);
                }
      
                previousSlope = currentSlope;
            }
                        
        }
        
        //copy the lineSegments
        allLineSegments = new LineSegment[segments.size()];
        segments.toArray(allLineSegments);

    }
    
    private void attemptToAddLineSegment(ArrayList<Point> segmentPts, ArrayList<LineSegment> segments) {
        if (segmentPts.size() >= 4) {                        
            // check natural order
            if (isNaturalOrder(segmentPts)) {                            
                Point beg = segmentPts.get(0);
                Point end = segmentPts.get(segmentPts.size() - 1);
                LineSegment lineSegment = new LineSegment(beg, end);
                segments.add(lineSegment);
            }
        } 
    }
    
    private boolean isNaturalOrder(ArrayList<Point> segmentPts) {
        for (int j = 0; j < segmentPts.size() - 1; j++) {
            Point p1 = segmentPts.get(j);
            Point p2 = segmentPts.get(j + 1);
            if (p1.compareTo(p2) > 0) return false;
        }
        
        return true;
    }
    
    
    public int numberOfSegments() {
        return allLineSegments.length;
    }
    
    public LineSegment[] segments() {
        return allLineSegments;
    }
    
    private void isDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points.");
            }
        }
        
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("Number of line segments: " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}