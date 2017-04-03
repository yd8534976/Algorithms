import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

/**
 * Created by CaTheother on 4/2/17.
 */
public class KdTree {
    private class Node {
        public Node(double x, double y) {
            this.x = x;
            this.y = y;
            point = new Point2D(x, y);
        }

        public Point2D point;
        public double x;
        public double y;
        public Node leftchild = null;
        public Node rightchild = null;
        public int mode;//0: x-coordinates; 1: y-coordinates;
    }

    private Node root;
    private int size = 0;
    private Point2D nearest;
    private double min;

    private void draw(Node node) {
        if (node == null) {
            return;
        }
        StdDraw.point(node.x, node.y);
        draw(node.leftchild);
        draw(node.rightchild);
    }

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        double x = p.x();
        double y = p.y();
        if (root == null) {
            root = new Node(x, y);
            root.mode = 0;
            size++;
            return;
        }
        Node current = root;
        while (true) {
            if ((Double.compare(current.x, x) == 0) && (Double.compare(current.y, y) == 0)) {
                return;
            }
            if (current.mode == 0) {
                if (Double.compare(x, current.x) <= 0) {
                    if (current.leftchild == null) {
                        Node node = new Node(x, y);
                        node.mode = 1;
                        current.leftchild = node;
                        size++;
                        return;
                    }
                    current = current.leftchild;
                } else {
                    if (current.rightchild == null) {
                        Node node = new Node(x, y);
                        node.mode = 1;
                        current.rightchild = node;
                        size++;
                        return;
                    }
                    current = current.rightchild;
                }
            } else {
                if (Double.compare(y, current.y) <= 0) {
                    if (current.leftchild == null) {
                        Node node = new Node(x, y);
                        node.mode = 0;
                        current.leftchild = node;
                        size++;
                        return;
                    }
                    current = current.leftchild;
                } else {
                    if (current.rightchild == null) {
                        Node node = new Node(x, y);
                        node.mode = 0;
                        current.rightchild = node;
                        size++;
                        return;
                    }
                    current = current.rightchild;
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            return false;
        }
        double x = p.x();
        double y = p.y();
        Node current = root;
        while (current != null) {
            if ((Double.compare(current.x, x) == 0) && (Double.compare(current.y, y) == 0)) {
                return true;
            }
            if (current.mode == 0) {
                if (Double.compare(x, current.x) <= 0) {
                    current = current.leftchild;
                } else {
                    current = current.rightchild;
                }
            } else {
                if (Double.compare(y, current.y) <= 0) {
                    current = current.leftchild;
                } else {
                    current = current.rightchild;
                }
            }
        }
        return false;
    }

    public void draw() {
        draw(root);
    }

    private void searchRange(Node node, RectHV rectHV, RectHV parentRect, ArrayList<Point2D> range) {
        if (node == null) {
            return;
        }
        if (rectHV.contains(node.point)) {
            range.add(node.point);
        }
        RectHV leftRect;
        RectHV rightRect;
        if (node.mode == 0) {
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), node.x, parentRect.ymax());
            rightRect = new RectHV(node.x, parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
        } else {
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), node.y);
            rightRect = new RectHV(parentRect.xmin(), node.y, parentRect.xmax(), parentRect.ymax());
        }
        if (rectHV.intersects(leftRect)) {
            searchRange(node.leftchild, rectHV, leftRect, range);
        }
        if (rectHV.intersects(rightRect)) {
            searchRange(node.rightchild, rectHV, rightRect, range);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> range = new ArrayList<>();
        RectHV parentRect = new RectHV(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        searchRange(root, rect, parentRect, range);
        return range;
    }

    private void searchNearest(Node node, RectHV parentRect, Point2D p) {
        if (node == null) {
            return;
        }
        double distanceSquared = node.point.distanceSquaredTo(p);
        if (Double.compare(distanceSquared, min) < 0) {
            nearest = node.point;
            min = distanceSquared;
        }
        RectHV leftRect;
        RectHV rightRect;
        boolean leftFirst = false;
        if (node.mode == 0) {
            if (p.x() < node.x) {
                leftFirst = true;
            }
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), node.x, parentRect.ymax());
            rightRect = new RectHV(node.x, parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
        } else {
            if (p.y() < node.y) {
                leftFirst = true;
            }
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), node.y);
            rightRect = new RectHV(parentRect.xmin(), node.y, parentRect.xmax(), parentRect.ymax());
        }
        if (leftFirst) {
            if (leftRect.distanceSquaredTo(p) < min) {
                searchNearest(node.leftchild, leftRect, p);
            }
            if (rightRect.distanceSquaredTo(p) < min) {
                searchNearest(node.rightchild, rightRect, p);
            }
        } else {
            if (rightRect.distanceSquaredTo(p) < min) {
                searchNearest(node.rightchild, rightRect, p);
            }
            if (leftRect.distanceSquaredTo(p) < min) {
                searchNearest(node.leftchild, leftRect, p);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        min = Double.MAX_VALUE;
        RectHV parentRect = new RectHV(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        searchNearest(root, parentRect, p);
        return nearest;
    }

    public static void main(String[] args) {

    }
}
