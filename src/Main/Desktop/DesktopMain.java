package Main.Desktop;

import Main.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopMain {
    public static void main(String[] args) {
        new LwjglApplication(new Game(), "Desktop App", 800, 600, true);
    }
}
