package UI;

import Rendering.RenderPass;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIText extends UIElement {
// ------------------------------ FIELDS ------------------------------

    public String text;
    public Color textColor;

// --------------------------- CONSTRUCTORS ---------------------------

    public UIText(String id, boolean appendSuffix) {
        super(id, appendSuffix);
    }

    public UIText(String id, String text, boolean appendSuffix) {
        this(id, appendSuffix);
        this.text = text;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void Render(SpriteBatch batch, RenderPass pass) { }

    @Override
    public String GetControlSuffix() {
        return "_UIText";
    }
}
