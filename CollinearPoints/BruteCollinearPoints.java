import java.util.Arrays;

/**
 * Created by CaTheother on 3/12/17.
 */
public class BruteCollinearPoints {
    private final LineSegment[] segments;//array of collinear segments
    private int count;//count of collinear segments

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        int length = points.length;
        Point[] points_copy = new Point[length];//make arguments immutable when sorting
        for (int i = 0; i < length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            points_copy[i] = points[i];
        }
        Arrays.sort(points_copy);
        for (int i = 0; i < length - 1; i++) {
            if (points_copy[i].compareTo(points_copy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }//to find repeated point
        LineSegment[] temps = new LineSegment[length];
        for (int i1 = 0; i1 < length - 3; i1++) {
            for (int i2 = i1 + 1; i2 < length - 2; i2++) {
                for (int i3 = i2 + 1; i3 < length - 1; i3++) {
                    for (int i4 = i3 + 1; i4 < length; i4++) {
                        if (Double.compare(points_copy[i1].slopeTo(points_copy[i2]), points_copy[i1].slopeTo(points_copy[i3])) == 0) {
                            if (Double.compare(points_copy[i1].slopeTo(points_copy[i2]), points_copy[i1].slopeTo(points_copy[i4])) == 0) {
                                LineSegment seg = new LineSegment(points_copy[i1], points_copy[i4]);
                                temps[count] = seg;
                                count++;
                            }
                        }
                    }
                }
            }
        }
        segments = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segments[i] = temps[i];
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] segments_copy = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segments_copy[i] = segments[i];
        }
        return segments_copy;//make data type immutable
    }
}
