package spline;

import spline.bezier.Bezier;
import spline.node.Node;

import java.util.LinkedList;

// WE DID IT BOIS THE ENTIRE POINT OF THE PROJECT IS RIGHT HERE
public class Spline {

    private LinkedList<Node> nodes;
    private LinkedList<Bezier> segments;

    public Spline() {
        nodes = new LinkedList<>();
        segments = new LinkedList<>();
    }

    public void addNode(Node toAdd) {
        nodes.add(toAdd);
        if (nodes.size() > 1) segments.add(new Bezier(nodes.get(nodes.size() - 2), nodes.getLast()));
    }

    public void update() {
        // Update nodes instead of curves to prevent updating the same node twice (that's bad if you couldn't tell why I'm putting this comment)
        for (Node n : nodes) n.update();
    }

    public void draw() {
        for (Node n : nodes) n.draw(); // Only draw nodes once
        for (Bezier b : segments) b.draw(); // Draw curves separately
    }
}
