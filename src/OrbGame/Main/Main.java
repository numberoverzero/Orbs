package OrbGame.Main;

import OrbGame.Main.Desktop.DesktopMain;

public class Main {
// ------------------------------ FIELDS ------------------------------

    static RunMode mode = RunMode.Desktop;

// --------------------------- main() method ---------------------------

    public static void main(String[] args) {
        switch (mode) {
            case Desktop:
                DesktopMain.main(args);
                break;
            default:
                break;
        }
    }
}
