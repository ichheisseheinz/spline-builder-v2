package app.gui;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.GRAY;
import static com.raylib.Colors.DARKGRAY;

import app.App;

public class AppGUI {

    private boolean showAddNodeInfoLabel = false;
    private boolean fileSaveDialog = false;

    public void background(int spacing) {
        Vector2 cameraPos = App.getCamera().target();
        int quotientX = (int) (cameraPos.x() / spacing) * spacing;
        int quotientY = (int) (cameraPos.y() / spacing) * spacing;

        for (int row = 0; row < App.HEIGHT + spacing; row += spacing) {
            DrawLine((int)cameraPos.x(), row + quotientY,
                    App.WIDTH + (int)cameraPos.x(), row + quotientY, row + quotientY == 0 ? GRAY : DARKGRAY);
        }

        for (int col = 0; col < App.WIDTH + spacing; col += spacing) {
            DrawLine(col + quotientX, (int)cameraPos.y(),
                    col + quotientX, App.HEIGHT + (int)cameraPos.y(), col + quotientX == 0 ? GRAY : DARKGRAY);
        }
    }

    public void UI() {
        // Buttons
        if (GuiButton(new Rectangle().x(25).y(25).width(30).height(30), "#125#") == 1) showAddNodeInfoLabel = true;
        if (GuiButton(new Rectangle().x(25).y(60).width(30).height(30), "#4#") == 1) fileSaveDialog = true;

        // Add node info label
        if (showAddNodeInfoLabel) {
            GuiDummyRec(new Rectangle().x(80).y(70).width(150).height(75), "Click anywhere to add\na new node");
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT) || IsMouseButtonPressed(MOUSE_BUTTON_RIGHT)) showAddNodeInfoLabel = false;
        }

        // File save dialog
        if (fileSaveDialog) {
            fileSaveDialog = false; // no saving yet
        }
    }
}
