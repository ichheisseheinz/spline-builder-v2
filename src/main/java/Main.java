import app.App;

import static com.raylib.Raylib.*;

public class Main {

    public static void main(String[] args) {
        SetTraceLogLevel(LOG_WARNING | LOG_ERROR | LOG_FATAL);
        InitWindow(App.WIDTH, App.HEIGHT, "Spline Builder by IchHeisseHeinz");
        SetTargetFPS(60);

        App app = new App();

        while (!WindowShouldClose()) {
            app.update();
            app.draw();
        }
        CloseWindow();
    }
}