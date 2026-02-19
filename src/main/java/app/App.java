package app;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

import app.gui.AppGUI;
import spline.Spline;
import spline.ControlPoint;

public class App {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    private Spline curve;
    private AppGUI gui;

    private static Camera2D camera;

    public static boolean IS_NODE_SELECTED = false;

    public App() {
        this.curve = new Spline();
        this.curve.addNode(new ControlPoint(new Vector2().x(150).y(350), null, new ControlPoint(new Vector2().x(250).y(50))));
        this.curve.addNode(new ControlPoint(new Vector2().x(550).y(350),
                new ControlPoint(new Vector2().x(450).y(50)), new ControlPoint(new Vector2().x(650).y(650))));
        this.curve.addNode(new ControlPoint(new Vector2().x(950).y(350), new ControlPoint(new Vector2().x(850).y(650)), null));
        this.gui = new AppGUI();

        camera = new Camera2D().rotation(0).zoom(1);
    }

    public void update() {
        // Update camera
        if (IsMouseButtonDown(MOUSE_BUTTON_RIGHT)) {
            camera.target(Vector2Subtract(camera.target(), GetMouseDelta()));
        }

        // Update curve
        curve.update();
    }

    public void draw() {
        BeginDrawing();

        ClearBackground(BLACK);

        BeginMode2D(camera);
        gui.background(50);
        curve.draw();
        EndMode2D();

        gui.UI();
        DrawFPS(5, 5);

        EndDrawing();
    }

    public static Camera2D getCamera() {
        return camera;
    }
}
