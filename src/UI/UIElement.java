package UI;

import Math.Shapes.Rect;
import Math.Vec2;
import Rendering.RenderPass;
import ScreenManagement.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UIElement {
// ------------------------------ FIELDS ------------------------------

    public UIElement Parent;
    public Screen Screen;

    // X, Y here refer to offset from parent's corner
    public Rect BoundingRect;

    protected String id;

// --------------------------- CONSTRUCTORS ---------------------------

    public UIElement(String id) {
        this(id, false);
    }

    public UIElement(String id, boolean appendSuffix) {
        BoundingRect = new Rect();
        if (appendSuffix)
            this.id = id.concat(GetControlSuffix());
        UIManager.Register(this);
    }

    public abstract String GetControlSuffix();

// -------------------------- OTHER METHODS --------------------------

    public void Draw(SpriteBatch batch, RenderPass pass) {
    }

    public Vec2 GetAbsolutePos() {
        Vec2 pos = GetRelativePos();
        if (Parent != null)
            pos.Add(Parent.GetAbsolutePos());
        return pos;
    }

    public Vec2 GetRelativePos() {
        return new Vec2(BoundingRect.X, BoundingRect.Y);
    }

    public String GetID() {
        return id;
    }

    public void Remove() {
        UIManager.UnRegister(this);
    }
}
