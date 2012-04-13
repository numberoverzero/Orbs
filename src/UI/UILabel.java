package UI;

import Rendering.RenderPass;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UILabel extends UIElement {
// ------------------------------ FIELDS ------------------------------

    public UIText text;
    public Color backgroundColor;

// --------------------------- CONSTRUCTORS ---------------------------

    public UILabel(String id, boolean appendSuffix) {
        super(id, appendSuffix);
    }

    public UILabel(String id, String text, boolean appendSuffix) {
        this(id, appendSuffix);
        this.text = new UIText(this.id, text, true);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void Render(SpriteBatch batch, RenderPass pass) { }

    @Override
    public String GetControlSuffix() {
        return "_UILabel";
    }
}
