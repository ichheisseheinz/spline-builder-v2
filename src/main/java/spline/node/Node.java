package spline.node;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class Node {

    private Vector2 position;
    private boolean active;

    private ControlPoint[] controlPoints;

    public Node(Vector2 position, ControlPoint first, ControlPoint second) {
        this.position = position;
        this.active = false;
        controlPoints = new ControlPoint[]{first, second};
    }

    public void update() {
        Vector2 pos = GetMousePosition();
        Rectangle boundingRec = new Rectangle().x(position.x() - 5).y(position.y() - 5).width(10).height(10);

        // Ensure only moving active nodes
        if (!active && IsMouseButtonPressed(MOUSE_BUTTON_LEFT) && CheckCollisionPointRec(pos, boundingRec)) active = true;
        else if (active && IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) active = false;

        // Check if mouse intersects node bounds, if so, move
        if (active && IsMouseButtonDown(MOUSE_BUTTON_LEFT) &&
                (CheckCollisionPointRec(pos, boundingRec) || CheckCollisionPointRec(Vector2Subtract(pos, GetMouseDelta()), boundingRec))) {
            position = Vector2Add(position, GetMouseDelta());

            // Update control points to move with node
            for (ControlPoint cp : controlPoints) {
                if (cp != null) cp.addPosition(GetMouseDelta());
            }
        }

        for (ControlPoint cp : controlPoints) {
            if (cp != null) cp.update(pos);
        }
    }

    public void draw() {
        DrawCircleV(position, 5, WHITE);

        for (ControlPoint cp : controlPoints) {
            if (cp != null) {
                cp.draw();
                DrawLineV(position, cp.getPosition(), WHITE);
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public ControlPoint getControlPoint(int index) {
        return controlPoints[index];
    }
}
