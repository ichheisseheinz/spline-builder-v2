package spline.node;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class ControlPoint {

    private Vector2 position;
    private boolean active;

    public ControlPoint(Vector2 position) {
        this.position = position;
        this.active = false;
    }

    public void update(Vector2 mousePos) {
        Rectangle boundingRec = new Rectangle().x(position.x() - 5).y(position.y() - 5).width(10).height(10);

        // Ensure only moving active nodes
        if (!active && IsMouseButtonPressed(MOUSE_BUTTON_LEFT) && CheckCollisionPointRec(mousePos, boundingRec)) active = true;
        else if (active && IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) active = false;

        // Check if mouse intersects node bounds, if so, move
        if (active && IsMouseButtonDown(MOUSE_BUTTON_LEFT) &&
                (CheckCollisionPointRec(mousePos, boundingRec) || CheckCollisionPointRec(Vector2Subtract(mousePos, GetMouseDelta()), boundingRec))) {
            position = Vector2Add(position, GetMouseDelta());
        }
    }

    public void draw() {
        DrawCircleLinesV(position, 5, WHITE);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void addPosition(Vector2 toAdd) {
        position = Vector2Add(position, toAdd);
    }
}
