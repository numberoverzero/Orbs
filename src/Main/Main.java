package Main;

import Main.Desktop.DesktopMain;

public class Main {
    static RunMode mode = RunMode.Desktop;
    
    public static void main(String[] args)
    {
        switch(mode)
        {
            case Desktop:
                DesktopMain.main(args);
                break;
            default:
                break;
        }
    }
}
