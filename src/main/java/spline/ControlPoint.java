package spline;

import app.App;

import static com.raylib.Colors.WHITE;
import static com.raylib.Colors.YELLOW;
import static com.raylib.Raylib.*;

public class ControlPoint {

    private Vector2 position;
    private boolean active = false;
    private boolean isChild = true;

    private ControlPoint[] controlPoints;

    public ControlPoint(Vector2 position) {
        this.position = position;
    }

    public ControlPoint(Vector2 position, ControlPoint first, ControlPoint second) {
        this(position);
        this.controlPoints = new ControlPoint[]{first, second};
        this.isChild = false;
    }

    public void update() {
        Vector2 mousePos = GetMousePosition();
        Rectangle boundingRec = new Rectangle()
                .x(position.x() - 5 - App.getCamera().target().x()).y(position.y() - 5 - App.getCamera().target().y()).width(10).height(10);

        // Ensure only moving active nodes
        if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT) && CheckCollisionPointRec(mousePos, boundingRec) && App.ACTIVE_CONTROL_POINT == null) {
            active = true;
            App.ACTIVE_CONTROL_POINT = this;
        } else if ((active && IsMouseButtonPressed(MOUSE_BUTTON_LEFT) && !CheckCollisionPointRec(mousePos, boundingRec) && App.ACTIVE_CONTROL_POINT != null)
                    || IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) {
            active = false;
            App.ACTIVE_CONTROL_POINT = null;
        }

        // Check if mouse intersects node bounds, if so, move
        if (active && IsMouseButtonDown(MOUSE_BUTTON_LEFT) &&
                (CheckCollisionPointRec(mousePos, boundingRec) || CheckCollisionPointRec(Vector2Subtract(mousePos, GetMouseDelta()), boundingRec))) {
            position = Vector2Add(position, GetMouseDelta());
            if (controlPoints != null) {
                for (ControlPoint c : controlPoints) {
                    if (c != null) c.addPosition(GetMouseDelta());
                }
            }
        }

        if (controlPoints != null) {
            for (ControlPoint c : controlPoints) {
                if (c != null) c.update();
            }
        }
    }

    public void draw() {
        if (isChild) DrawCircleLinesV(position, 5, active ? YELLOW : WHITE);
        else DrawCircleV(position, 5, active ? YELLOW : WHITE);

        if (active) DrawText("(" + (int)position.x() + ", " + (int)position.y() + ")",
                (int)position.x() + 15, (int)position.y() + 15, 20, WHITE);

        if (controlPoints != null) {
            for (ControlPoint c : controlPoints) {
                if (c != null) {
                    c.draw();
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
