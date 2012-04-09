package UI;

import com.badlogic.gdx.graphics.Color;

public class Text extends UIElement {
// ------------------------------ FIELDS ------------------------------

    public String text;
    public Color textColor;

// --------------------------- CONSTRUCTORS ---------------------------

    public Text(String id, boolean appendSuffix) {
        super(id, appendSuffix);
    }

    public Text(String id, String text, boolean appendSuffix) {
        this(id, appendSuffix);
        this.text = text;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public String GetControlSuffix() {
        return "_Text";
    }
}
