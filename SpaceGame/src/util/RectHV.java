package util;

/*************************************************************************
 *  Compilation:  javac RectHV.java
 *  Execution:    java RectHV
 *  Dependencies: Point2D.java
 *
 *  Implementation of 2D axis-aligned rectangle.
 *
 *************************************************************************/

public class RectHV {
    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates

    // construct the axis-aligned rectangle [xmin, xmax] x [ymin, ymax]
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (xmax < xmin || ymax < ymin) {
            throw new IllegalArgumentException("Invalid rectangle");
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    // accessor methods for 4 coordinates
    public double xmin() { return xmin; }
    public double ymin() { return ymin; }
    public double xmax() { return xmax; }
    public double ymax() { return ymax; }

    // width and height of rectangle
    public double width()  { return xmax - xmin; }
    public double height() { return ymax - ymin; }

    // does this axis-aligned rectangle intersect that one?
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
            && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }


    // distance from p to closest point on this axis-aligned rectangle
    public double distanceTo(float[] p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }

    // distance squared from p to closest point on this axis-aligned rectangle
    public double distanceSquaredTo(float[] p) {
        double dx = 0.0f, dy = 0.0f;
        if      (p[0] < xmin) dx = p[0] - xmin;
        else if (p[0] > xmax) dx = p[0] - xmax;
        if      (p[1] < ymin) dy = p[1] - ymin;
        else if (p[1] > ymax) dy = p[1] - ymax;
        return dx*dx + dy*dy;
    }

    // does this axis-aligned rectangle contain p?
    public boolean contains(float[] p) {
        return (p[0] >= xmin) && (p[0] <= xmax)
            && (p[1] >= ymin) && (p[1] <= ymax);
    }

    // are the two axis-aligned rectangles equal?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) y;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }

    // return a string representation of this axis-aligned rectangle
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }
}