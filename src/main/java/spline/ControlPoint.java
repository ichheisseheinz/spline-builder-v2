package spline;

import app.App;

import static com.raylib.Colors.WHITE;
import static com.raylib.Colors.YELLOW;
import static com.raylib.Raylib.*;

public class ControlPoint {

    private Vector2 position;
    private boolean active = false;

    private ControlPoint[] controlPoints = null;

    public ControlPoint(Vector2 position) {
        this.position = position;
    }

    public ControlPoint(Vector2 position, ControlPoint first, ControlPoint second) {
        this(position);
        controlPoints = new ControlPoint[]{first, second};
    }

    private void moveActive(Vector2 mousePos, Rectangle boundingRec) {
        // Ensure only moving active nodes
        if (!active && IsMouseButtonPressed(MOUSE_BUTTON_LEFT) && CheckCollisionPointRec(mousePos, boundingRec) && !App.IS_NODE_SELECTED) {
            active = true;
            App.IS_NODE_SELECTED = true;
        } else if (active && IsMouseButtonReleased(MOUSE_BUTTON_LEFT) && App.IS_NODE_SELECTED) {
            active = false;
            App.IS_NODE_SELECTED = false;
        }
    }

    public void moveChildren() {
        for (ControlPoint c : controlPoints) {
            if (c != null) c.addPosition(GetMouseDelta());
        }
    }

    public void updateChildren() {
        for (ControlPoint c : controlPoints) {
            if (c != null) c.update();
        }
    }

    public void update() {
        Vector2 mousePos = GetMousePosition();
        Rectangle boundingRec = new Rectangle()
                .x(position.x() - 5 - App.getCamera().target().x()).y(position.y() - 5 - App.getCamera().target().y()).width(10).height(10);
        moveActive(mousePos, boundingRec);

        // Check if mouse intersects node bounds, if so, move
        if (active && IsMouseButtonDown(MOUSE_BUTTON_LEFT) &&
                (CheckCollisionPointRec(mousePos, boundingRec) || CheckCollisionPointRec(Vector2Subtract(mousePos, GetMouseDelta()), boundingRec))) {
            position = Vector2Add(position, GetMouseDelta());
            if (controlPoints != null) moveChildren();
        }

        if (controlPoints != null) updateChildren();
    }

    public void draw(boolean isChild) {
        if (isChild) DrawCircleLinesV(position, 5, active ? YELLOW : WHITE);
        else DrawCircleV(position, 5, active ? YELLOW : WHITE);

        if (controlPoints != null) {
            for (ControlPoint c : controlPoints) {
                if (c != null) {
                    c.draw(true);
                    DrawLineV(position, c.getPosition(), WHITE);
                }
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void addPosition(Vector2 toAdd) {
        position = Vector2Add(position, toAdd);
    }

    public ControlPoint getControlPoint(int index) {
        return controlPoints[index];
    }
}
