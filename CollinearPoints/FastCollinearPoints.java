import java.util.Arrays;

/**
 * Created by CaTheother on 3/12/17.
 */
public class FastCollinearPoints {
    private final LineSegment[] segments;
    private int count = 0;

    public FastCollinearPoints(Point[] points) {
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
        LineSegment[] temps = new LineSegment[length];
        Arrays.sort(points_copy);
        for (int i = 0; i < length - 1; i++) {
            if (points_copy[i].compareTo(points_copy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }//to find repeated point
        double[] addedslope = new double[length];
        Point[] wasMax = new Point[length];
        for (int j = 0; j < length - 3; j++) {
            Point center;
            center = points_copy[j];
            Point[] bySlopes = new Point[length - 1];
            int numberOfSlopes = 0;
            for (int i = 0; i < length; i++) {
                if ((center.compareTo(points_copy[i]) == -1) && (i != j)) {
                    bySlopes[numberOfSlopes] = points_copy[i];
                    numberOfSlopes++;
                }
            }
            Arrays.sort(bySlopes, 0, numberOfSlopes, center.slopeOrder());
            int head = 0;
            int tail = 1;
            int max = 0;
            while (head < numberOfSlopes - 2) {
                while (Double.compare(center.slopeTo(bySlopes[head]), center.slopeTo(bySlopes[tail])) == 0) {
                    if (bySlopes[max].compareTo(bySlopes[tail]) == -1) {
                        max = tail;
                    }
                    tail++;
                    if (tail == numberOfSlopes) {
                        break;
                    }
                }
                //System.out.println("head:"+head+"--->"+tail);
                if (tail - head > 2) {
                    Boolean canAdd = true;
                    for (int i = 0; i < count; i++) {
                        if ((wasMax[i].compareTo(bySlopes[max]) == 0) && (Double.compare(addedslope[i], center.slopeTo(bySlopes[max])) == 0)) {
                            canAdd = false;
                        }
                    }
                    if (canAdd) {
                        LineSegment segment = new LineSegment(center, bySlopes[max]);
                        temps[count] = segment;
                        addedslope[count] = center.slopeTo(bySlopes[max]);
                        wasMax[count] = bySlopes[max];
                        count++;
                        if (count == temps.length) {
                            LineSegment[] newTemps = new LineSegment[temps.length * 2];
                            double[] newaddedslope = new double[addedslope.length * 2];
                            Point[] newwasMax = new Point[wasMax.length * 2];
                            for (int i = 0; i < count; i++) {
                                newTemps[i] = temps[i];
                                newaddedslope[i] = addedslope[i];
                                newwasMax[i] = wasMax[i];
                            }
                            temps = newTemps;
                            addedslope = newaddedslope;
                            wasMax = newwasMax;
                        }
                    }
                }
                head = tail;
                max = head;
                tail++;
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
