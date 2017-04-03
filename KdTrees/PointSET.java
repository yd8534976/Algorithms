import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

/**
 * Created by CaTheother on 4/2/17.
 */
public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p==null){
            throw new NullPointerException();
        }
        if (!set.contains(p)) {
            set.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p==null){
            throw new NullPointerException();
        }
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect==null){
            throw new NullPointerException();
        }
        TreeSet<Point2D> range = new TreeSet<>();
        for (Point2D p : set) {
            if ((p.x()>=rect.xmin())&&(p.x()<=rect.xmax())){
                if ((p.y()>=rect.ymin())&&(p.y()<=rect.ymax())){
                    range.add(p);
                }
            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p==null){
            throw new NullPointerException();
        }
        double min=Double.MAX_VALUE;
        Point2D nearestPoint=null;
        for (Point2D current:set){
            if (current.distanceSquaredTo(p)<min){
                nearestPoint=current;
                min=current.distanceSquaredTo(p);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}
