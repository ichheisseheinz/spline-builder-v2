package spline.bezier;

import spline.ControlPoint;

import java.util.HashMap;

import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

public class Bezier {

    private ControlPoint p0;
    private ControlPoint c0;
    private ControlPoint p1;
    private ControlPoint c1;

    private static final int RESOLUTION = 50;
    private static final HashMap<Float, Float[]> precompiledTVals = precompileTVals();

    public Bezier(ControlPoint p0, ControlPoint p1) {
        this.p0 = p0;
        this.p1 = p1;
        this.c0 = p0.getControlPoint(1);
        this.c1 = p1.getControlPoint(0);
    }

    private static HashMap<Float, Float[]> precompileTVals() {
        HashMap<Float, Float[]> compiled = new HashMap<>();
        float res = 1f/RESOLUTION;

        for (float t = res; t <= 1; t += res) {
            Float[] vals = new Float[4];
            float t2 = (float) Math.pow(t, 2);
            float t3 = (float) Math.pow(t, 3);

            vals[0] = -t3 + 3*t2 - 3*t + 1;
            vals[1] = 3*t3 - 6*t2 + 3*t;
            vals[2] = -3*t3 + 3*t2;
            vals[3] = t3;

            compiled.put(t, vals);
        }

        return compiled;
    }

    public void draw() {
        /*
        Matrix form
        [1 t t^2 t^3] [ 1  0  0  0] [p0]
                      [-3  3  0  0] [p1]
                      [ 3 -6  3  0] [p2]
                      [-1  3 -3  1] [p3]

        Bernstein form
        P = p0(-t^3 + 3t^2 - 3t + 1) +
            p1(3t^3 - 6t^2 + 3t) +
            p2(-3t^3 + 3t^2) +
            p3(t^3)

        Polynomial coefficients
        P = p0 +
            t(-3p0 + 3p1) +
            t^2(3p0 - 6p1 + 3p2) +
            t^3(-p0 + 3p1 - 3p2 + p3)
         */

        Vector2 last = p0.getPosition();
        Vector2 current;
        float resolution = 1f / RESOLUTION;
        for (float t = resolution; t <= 1; t += resolution) {
            Float[] ts = precompiledTVals.get(t); // this variable name is ironic, because it could be t's (as in those t values) or ts (as in this shit, because that's what it is)
            if (ts == null) {
                System.out.println("precompiledTVals is null at key " + t);
                return;
            }

            current = Vector2Add(Vector2Add(Vector2Scale(p0.getPosition(), ts[0]), Vector2Scale(c0.getPosition(), ts[1])),
                      Vector2Add(Vector2Scale(c1.getPosition(), ts[2]), Vector2Scale(p1.getPosition(), ts[3])));

            // Genuinely the worst line of code I have ever fucking written, just keeping it here because I have to remember to strive for greatness not the burning pits of hell
            // current = Vector2Add(Vector2Add(p0.getPosition(),
            //           Vector2Scale(Vector2Add(Vector2Scale(p0.getPosition(), -3), Vector2Scale(p1.getPosition(), 3)), t)),
            //           Vector2Add(Vector2Scale(Vector2Add(Vector2Add(Vector2Scale(p0.getPosition(), 3), Vector2Scale(p1.getPosition(), -6)), Vector2Scale(p2.getPosition(), 3)), t2),
            //           Vector2Scale(Vector2Add(Vector2Add(Vector2Scale(p0.getPosition(), -1), Vector2Scale(p1.getPosition(), 3)), Vector2Add(Vector2Scale(p2.getPosition(), -3), p3.getPosition())), t3)));

            DrawLineV(last, current, RAYWHITE);
            last = current;
        }
    }
}
