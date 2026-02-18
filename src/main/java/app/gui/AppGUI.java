package app.gui;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.GRAY;

import app.App;

public class AppGUI {

    private boolean showMessageBox;

    public AppGUI() {
        this.showMessageBox = false;
    }

    public void background(int spacing) {
        Vector2 cameraPos = App.getCamera().target();
        int quotientX = (int) (cameraPos.x() / spacing) * spacing;
        int quotientY = (int) (cameraPos.y() / spacing) * spacing;

        for (int row = 0; row < App.HEIGHT + spacing; row += spacing) {
            DrawLine((int)cameraPos.x(), row + quotientY,
                    App.WIDTH + (int)cameraPos.x(), row + quotientY, GRAY);
        }

        for (int col = 0; col < App.WIDTH + spacing; col += spacing) {
            DrawLine(col + quotientX, (int)cameraPos.y(),
                    col + quotientX, App.HEIGHT + (int)cameraPos.y(), GRAY);
        }
    }

    public void UI() {
        if (GuiButton(new Rectangle().x(24).y(24).width(120).height(30), "#191#Info") == 1) showMessageBox = true;

        if (showMessageBox)
        {
            int result = GuiMessageBox(new Rectangle().x(85).y(70).width(250).height(100),
            "#191#Message Box", "This doesn't do anything yet\nJust trying out raygui", "Gimme some time;Lemme be");

            if (result >= 0) showMessageBox = false;
        }
    }
}
