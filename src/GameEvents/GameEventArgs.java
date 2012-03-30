package GameEvents;

/**
 * User: Joe Laptop
 * Date: 3/27/12
 * Time: 5:35 PM
 */
public class GameEventArgs {
// ------------------------------ FIELDS ------------------------------

    String msg;

// --------------------------- CONSTRUCTORS ---------------------------

    public GameEventArgs() {
        msg = "";
    }

    public GameEventArgs(String msg) {
        this.msg = msg;
    }
}
