package spline;

import spline.bezier.Bezier;

import java.util.LinkedList;

// WE DID IT BOIS THE ENTIRE POINT OF THE PROJECT IS RIGHT HERE
public class Spline {

    private LinkedList<ControlPoint> controlPoints;
    private LinkedList<Bezier> segments;

    public Spline() {
        controlPoints = new LinkedList<>();
        segments = new LinkedList<>();
    }

    public void addNode(ControlPoint toAdd) {
        controlPoints.add(toAdd);
        if (controlPoints.size() > 1) segments.add(new Bezier(controlPoints.get(controlPoints.size() - 2), controlPoints.getLast()));
    }

    public void update() {
        // Update nodes instead of curves to prevent updating the same node twice (that's bad if you couldn't tell why I'm putting this comment)
        for (ControlPoint c : controlPoints) c.update();
    }

    public void draw() {
        for (ControlPoint c : controlPoints) c.draw(false); // Only draw nodes once
        for (Bezier b : segments) b.draw(); // Draw curves separately
    }
}
