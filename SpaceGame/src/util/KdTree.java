package util;

import java.util.Stack;

public class KdTree<Value>
{
    // positive and negative infinity values
    private final double pInf = Double.POSITIVE_INFINITY;
    private final double nInf = Double.NEGATIVE_INFINITY;
    
    // the top of the KD tree
    protected Node top;
    // the size of the KD tree
    private int size;
    
    // the private Node class used for the nodes of the tree
    // SPECIAL THANKS to the Princeton website for this class
    protected class Node 
    {
        protected float[] p;      // the point
        protected Value value;    // the symbol table maps the point to this value
        
        // the axis-aligned rectangle corresponding to this node
        protected RectHV rect;
        protected Node left;        // the left/bottom subtree
        protected Node right;        // the right/top subtree
        
        // initialize with point, value, and rectangle
        Node(float[] p, Value val, RectHV rect)
        {
            this.p = p;
            value = val;
            this.rect = rect;
            
        }
    }
    

    /*
     * Goes through the kdTree and finds the k closest points
     */
    public Iterable<float[]> nearest(float[] p, int k)
    {
        // the stack to be returned
        Stack<float[]> stack = new Stack<float[]>();
        
        // set the closest point  the furthest away possible
        // (infinity is not an option for points, can't use the top point
        //   because it can't be added more than once)
        float[] furthest = {-9e20f, -9e20f};
        
        // add the closest points and returns
        for (int i = 0; i < k; i++)
        {
            float[] nearest = enhNearest(top, furthest, p, stack, true);
            if (!equals(nearest, furthest))
                stack.push(enhNearest(top, furthest, p, stack, true));
        }
        return stack;
    }
    
     /*
     * The enhanced nearest, only adds the closest if it is not already in the 
     * stack
     */
    private float[] enhNearest(Node node, float[] closest,
                                         float[] p, Stack stack, boolean xCmp)
    {
        // base case
        if (node == null)
            return closest;
        
        // distance from rectangle to point and from closest found to point
        double distToP = node.rect.distanceSquaredTo(p);
        double closestDist = distanceSquaredTo(p, closest);
        
        // return if distance to rectangle is less than distance to closest
        if (distToP > closestDist)
            return closest;
        
        // creating a new closest so closest does not change
        float[] newClosest = closest;
        
        // if this point is closer than replace closest
        if (distanceSquaredTo(p, node.p) < closestDist)
        {
            if (!stackContains(stack, node.p))
                newClosest = node.p;
        }
        
        
        // go down the subtrees in the correct order depending on what
        // side the queried point is on
        if ((p[0] < node.p[0] && xCmp) || (p[1] < node.p[1] && !xCmp))
        {
            newClosest = enhNearest(node.left, newClosest, p, stack, !xCmp);
            newClosest = enhNearest(node.right, newClosest, p, stack, !xCmp);
        }
        else
        {
            newClosest = enhNearest(node.right, newClosest, p, stack, !xCmp); 
            newClosest = enhNearest(node.left, newClosest, p, stack, !xCmp);
        }
        
        return newClosest;
    }
    
    private double distanceSquaredTo(float[] a, float[] b)
    {
    	return (a[0] - b[0])*(a[0] - b[0]) + (a[1] - b[1])*(a[1] - b[1]);
    }
    private boolean equals(float[] a, float b[])
    {
    	float x = a[0] - b[0];
    	float y = a[1] - b[1];
    	return x == 0 && y == 0;
    }
    /*
     * helper method for adding, it checks if a point is contained in the 
     * stack
     */
    private boolean stackContains(Stack<float[]> stack, float[] p)
    {
        for (float[] a: stack)
        {
            if (equals(a, p))
                return true;
        }
        return false;
    }
    
    // construct an empty table of points
    public KdTree()
    {
        size = 0;
    }
    
    // is the symbol table empty
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    // the size of the symbol table
    public int size()
    {
        return size;
    }
    
    
    /*
     *  Insert key-value pair into KD tree
     *  (Loosely based on the Princeton BST insert method)
     */
    public void insert(float[] point, Value val) {
        // create the first rectangle and go into the recursive helper
        //RectHV rect = new RectHV(nInf, nInf, pInf, pInf);
        RectHV rect = new RectHV(-100, -100, 100, 100);
        top = put(top, point, val, true, rect);
    }
    
    /*
     * The helper method for insert, it goes down recursively to find the 
     * correct empty location, changing the rectangle of the insertion point 
     * as it goes down
     */
    private Node put(Node node, float[] point, Value val, boolean cmpX, RectHV rect) 
    {
        // base case, increment size return the new node
        if (node == null) 
        {
            size++;
            return new Node(point, val, rect);
        }
        
        // compare the point to be inserted against the current node
        // (depends on if comparing x's or y's)
        double cmp;
        if (cmpX)
            cmp = point[0] - node.p[0];
        else
            cmp = point[1] - node.p[1];
        
        // if the point compares lower go left
        if (cmp < 0)
        {
            // create the new rectangle
            RectHV newRect = getRect(node, true, cmpX);
            // put the point left
            node.left  = put(node.left, point, val, !cmpX, newRect);
        }
        // if higher than go right
        else if (cmp > 0)
        {
            RectHV newRect = getRect(node, false, cmpX);
            node.right = put(node.right, point, val, !cmpX, newRect);
        }
        
        // check if equal, if not then go right
        else
        {
            //compares the other point (either x or y)
            if (!cmpX)
                cmp = point[0] - node.p[0];
            else
                cmp = point[1] - node.p[1];
            
            if (cmp == 0)
                node.value = val;
            else
            {
                RectHV newRect = getRect(node, false, cmpX);
                node.right = put(node.right, point, val, !cmpX, newRect);
            }
        }
        
        return node;
    }
    
    /*
     * A helper method for put, it updates the rectangle whenever put goes down
     * a recursive level
     */
    private RectHV getRect(Node node, boolean less, boolean cmpX)
    {
        // the node's rectangle
        RectHV n = node.rect;
        // if comparing x's
        if (cmpX)
        {
            // different rectangle depending on higher or lower
            if (less)
                return new RectHV(n.xmin(), n.ymin(), node.p[0], n.ymax());
            else
                return new RectHV(node.p[0], n.ymin(), n.xmax(), n.ymax());
        }
        // same thing but comparing the y's
        else
        {
            if (less)
                return new RectHV(n.xmin(), n.ymin(), n.xmax(), node.p[1]);
            else
                return new RectHV(n.xmin(), node.p[1], n.xmax(), n.ymax());
        }
    }
    
    /*
     *  returns value associated with the given key, or null if no such key 
     *  exists
     *  (Loosely based on the Princeton BST get method)
     */
    public Value get(float[] point)
    {
        if (point == null) 
            return null;
        
        return get(top, point, true);
    }
    
    /*
     * recursive helper for get method, goes down the tree to find the correct
     * node
     */
    private Value get(Node node, float[] point, boolean cmpX) {
        //base case
        if (node == null)
            return null;
        
        // compare x's or y's accordingly 
        double cmp;
        if (cmpX)
            cmp = point[0] - node.p[0];
        else
            cmp = point[1] - node.p[1];
        
        // if less than or greater go down left or right
        if (cmp < 0) 
            return get(node.left, point, !cmpX);
        else if (cmp > 0)
            return get(node.right, point, !cmpX);
        // if equal then check if the other coordinate is equal as well
        else
        {
            if (!cmpX)
                cmp = point[0] - node.p[0];
            else
                cmp = point[1] - node.p[1];
            
            if (cmp == 0)
                return node.value;
            else
                return get(node.right, point, !cmpX);
        }
    }
    
    // checks if a point is in the ST
    public boolean contains(float[] val)
    {
        return get(val) != null;
    }

    
    // returns an Iterable object of all points in the range of the rectangle
    public Iterable<float[]> range(RectHV rect) 
    {
        // creates the stack, fills it, then returns
        Stack<float[]> stack = new Stack<float[]>();
        range(top, rect, stack);
        return stack;
    }
    
    /*
     * The recursive helper for range, only goes down branches of the tree that
     * intersect the rectangle
     */
    private void range(Node node, RectHV rect, Stack<float[]> stack)
    {
        // base case
        if (node == null)
            return;
        
        // if the point is contained, add it to the stack
        if (rect.contains(node.p))
            stack.push(node.p);
        
        // if the rectangle intersects the node then continue going down the 
        // branches
        if (rect.intersects(node.rect))
        {
            range(node.left, rect, stack);
            range(node.right, rect, stack);
        }
    }
    
    // returns the closest point to a given point
    public float[] nearest(float[] p) 
    {
        // safety check if the ST is empty
        if (isEmpty())
            return null;
        // set the closest point to the top point
        float[] closest = top.p;
        // return the closest
        return nearest(top, closest, p, true);
    }
    
    /*
     * Recursive helper for nearest, goes down the branches only if the 
     * distance to the rectangle is less than the distance to the closest 
     * branch.
     */
    private float[] nearest(Node node, float[] closest, float[] p, boolean xCmp)
    {
        // base case
        if (node == null)
            return closest;
        
        // distance from rectangle to point and from closest found to point
        double distToP = node.rect.distanceSquaredTo(p);
        double closestDist = distanceSquaredTo(p, closest);
        
        // return if distance to rectangle is less than distance to closest
        if (distToP > closestDist)
            return closest;
        
        // creating a new closest so closest does not change
        float[] newClosest = closest;
        
        // if this point is closer than replace closest
        if (distanceSquaredTo(p, node.p) < closestDist)
            newClosest = node.p;
        
        
        // go down the subtrees in the correct order depending on what
        // side the queried point is on
        if ((p[0] < node.p[0] && xCmp) || (p[1] < node.p[1] && !xCmp))
        {
            newClosest = nearest(node.left, newClosest, p, !xCmp);
            newClosest = nearest(node.right, newClosest, p, !xCmp);
        }
        else
        {
            newClosest = nearest(node.right, newClosest, p, !xCmp); 
            newClosest = nearest(node.left, newClosest, p, !xCmp);
        }
        
        return newClosest;
    }
}