package UI;

import java.util.HashMap;
import java.util.Map;

public class UIManager {
// ------------------------------ FIELDS ------------------------------

    private static Map<String, UIElement> elementLookupByID = new HashMap<String, UIElement>();

// -------------------------- STATIC METHODS --------------------------

    public static void Register(UIElement element) {
        elementLookupByID.put(element.GetID(), element);
    }

    public static void UnRegister(UIElement element) {
        elementLookupByID.remove(element.GetID());
    }

    public static UIElement GetElement(String id) {
        return elementLookupByID.get(id);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    ;

    private UIManager() {
    }
}
