package ScreenManagement;

import Math.Shapes.Rect;
import Rendering.RenderPass;
import Rendering.Shapes.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * User: Joe Laptop
 * Date: 4/6/12
 * Time: 8:20 PM
 */
public class ScreenManager {
// ------------------------------ FIELDS ------------------------------

    private ArrayList<Screen> screens;
    private ArrayList<Screen> screensToUpdate;
    private ArrayList<Screen> screensToDraw;

    private SpriteBatch batch;

// -------------------------- OTHER METHODS --------------------------

    public void AddScreen(Screen screen) {
        screen.ScreenManager = this;
        screen.LoadContent();
        screens.add(screen);
    }

    public void Draw(SpriteBatch batch, RenderPass pass) {
        screensToDraw.clear();
        for (Screen screen : screens)
            screensToDraw.add(screen);

        for (Screen screen : screensToDraw) {
            if (screen.GetScreenState() == ScreenState.Hidden)
                continue;
            screen.Draw(batch, pass);
        }
    }

    public void FadeBackBufferToBlack(int alpha) {
        Rect screenArea = new Rect();
        DrawRectangle(screenArea, new Color(0, 0, 0, alpha / 255f));
    }

    public void DrawRectangle(Rect rect, Color color) {
        batch.begin();
        Rectangle.Draw(batch, rect, color);
        batch.end();
    }

    public ArrayList<Screen> GetScreens() {
        return new ArrayList<Screen>(screens);
    }

    public SpriteBatch GetSpriteBatch() {
        return batch;
    }

    public void LoadContent() {
        batch = new SpriteBatch();
        for (Screen screen : screens)
            screen.LoadContent();
    }

    public void RemoveScreen(Screen screen) {
        screen.ScreenManager = null;
        screen.UnloadContent();
        screens.remove(screen);
        screensToUpdate.remove(screen);
    }

    public void UnloadContent() {
        batch = null;
        for (Screen screen : screens)
            screen.UnloadContent();
    }

    public void Update(float dt) {
        screensToUpdate.clear();
        for (Screen screen : screens)
            screensToUpdate.add(screen);

        boolean otherScreenHasFocus = false;
        boolean coveredByOtherScreen = false;

        Screen screen;
        int screenIndex;
        while (screensToUpdate.size() > 0) {
            screenIndex = screensToUpdate.size() - 1;
            screen = screensToUpdate.get(screenIndex);
            screensToUpdate.remove(screenIndex);

            screen.Update(dt, otherScreenHasFocus, coveredByOtherScreen);

            if (screen.GetScreenState() == ScreenState.TransitionOn ||
                    screen.GetScreenState() == ScreenState.Active) {
                // If this is the first active screen we came across,
                // give it a chance to handle input and update presence.
                if (!otherScreenHasFocus) {
                    screen.HandleInput();

                    otherScreenHasFocus = true;
                }

                // If this is an active non-popup, inform any subsequent
                // screens that they are covered by it.
                if (!screen.IsPopup)
                    coveredByOtherScreen = true;
            }
        }
    }
}
