package UI;

import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * User: Joe Laptop
 * Date: 4/10/12
 * Time: 8:17 AM
 */
public class UIContainer extends UIElement {
// ------------------------------ FIELDS ------------------------------

    private ArrayList<UIElement> children;
    private Orientation orientation;

// --------------------------- CONSTRUCTORS ---------------------------

    public UIContainer(String id, boolean appendSuffix) {
        super(id, appendSuffix);
        children = new ArrayList<UIElement>();
        orientation = Orientation.Vertical;
    }

// -------------------------- OTHER METHODS --------------------------

    public void AddChild(UIElement element) {
        children.add(element);
    }

    @Override
    public String GetControlSuffix() {
        return "_UIContainer";
    }

    public void RemoveChild(UIElement child) {
        children.remove(child);
        child.Parent = null;
    }

    @Override
    public void Render(SpriteBatch batch, RenderPass pass) {

    }

    public void SetOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
