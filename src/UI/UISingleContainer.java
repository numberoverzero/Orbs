package UI;

/**
 * User: Joe Laptop
 * Date: 4/10/12
 * Time: 9:25 AM
 */
public class UISingleContainer extends UIContainer {
// --------------------------- CONSTRUCTORS ---------------------------

    public UISingleContainer(String id, boolean appendSuffix) {
        super(id, appendSuffix);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public String GetControlSuffix() {
        return "_UISingleContainer";
    }
}
