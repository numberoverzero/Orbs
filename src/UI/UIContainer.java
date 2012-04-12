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
    private ArrayList<UIElement> children;
    private Orientation orientation;

    public UIContainer(String id, boolean appendSuffix)
    {
        super(id, appendSuffix);
        children = new ArrayList<UIElement>();
        orientation = Orientation.Vertical;
    }
    
    @Override
    public String GetControlSuffix()
    {
        return "_UIContainer";
    }
    
    public void AddChild(UIElement element)
    {
        children.add(element);
    }
    
    public void RemoveChild(UIElement child)
    {
        children.remove(child);
        child.Parent = null;
        
    }

    public void SetOrientation(Orientation orientation)
    {
        this.orientation = orientation;
    }

    @Override
    public void Render(SpriteBatch batch, RenderPass pass)
    {

    }
}
