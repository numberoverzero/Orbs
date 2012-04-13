package Main.Desktop;

import Main.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopMain {
// --------------------------- main() method ---------------------------

    public static void main(String[] args) {
        new LwjglApplication(new Game(), "Desktop App", 480, 320, true);
    }
}
