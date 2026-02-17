package app;

import static com.raylib.Colors.BLACK;
import static com.raylib.Raylib.*;

import app.gui.AppGUI;
import bezier.Bezier;
import node.Node;

public class App {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    private Bezier curve;
    private AppGUI gui;

    public App() {
        this.curve = new Bezier(
                new Node(new Vector2().x(350).y(500)),
                new Node(new Vector2().x(450).y(200)),
                new Node(new Vector2().x(650).y(200)),
                new Node(new Vector2().x(750).y(500))
        );
        this.gui = new AppGUI();
    }

    public void update() {
        curve.update();
    }

    public void draw() {
        BeginDrawing();

        ClearBackground(BLACK);

        gui.background(50);
        curve.draw();
        gui.UI();

        DrawFPS(10, 10);

        EndDrawing();
    }
}
