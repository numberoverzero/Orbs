package UI;

import Math.Shapes.Rect;
import Rendering.RenderPass;
import ScreenManagement.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class UIElement {
// ------------------------------ FIELDS ------------------------------

    public UIContainer Parent;

    // X, Y here refer to offset from parent's corner
    public Rect BoundingRect;
    protected boolean visible;
    protected Alignment halign, valign;
    protected boolean hExpand, vexpand;
    protected Screen screen;

    protected String id;

// --------------------------- CONSTRUCTORS ---------------------------

    public UIElement(String id, boolean appendSuffix) {
        BoundingRect = new Rect();
        halign = valign = Alignment.Center;
        hExpand = vexpand = false;
        visible = true;

        if (appendSuffix)
            this.id = id.concat(GetControlSuffix());
        UIManager.Register(this);
    }

    public abstract String GetControlSuffix();

// -------------------------- OTHER METHODS --------------------------

    public void Destroy() {
        if (Parent != null)
            Parent.RemoveChild(this);
        UIManager.UnRegister(this);
    }

    public void DetermineSize() {
    }

    public String GetID() {
        return id;
    }

    public Screen GetScreen() {
        if (screen != null)
            return screen;
        if (Parent != null)
            return Parent.GetScreen();
        // Null-safe return
        return Screen.NoneScreen();
    }

    public abstract void Render(SpriteBatch batch, RenderPass pass);

    public void ResetSize(Vector2 dimensions) {
        BoundingRect.SetDimensions(dimensions);
    }

    public void SetAbsolutePos(Vector2 position) {
        // Calculate global offset less relative offset
        Vector2 globalOffset = GetAbsolutePos();
        globalOffset.sub(GetRelativePos());

        Vector2 newRel = new Vector2(position);
        newRel.sub(globalOffset);
        SetRelativePos(newRel);
    }

    public Vector2 GetAbsolutePos() {
        Vector2 pos = GetRelativePos();
        if (Parent != null)
            pos.add(Parent.GetAbsolutePos());
        else {
            // If parent is null, grab the screen and make sure we offset by that.
            // This will allow screens to handle transition position entirely by updating the bottom left corner
            pos.add(GetScreen().GetBottomLeftPosition());
        }
        return pos;
    }

    public Vector2 GetRelativePos() {
        return new Vector2(BoundingRect.X, BoundingRect.Y);
    }

    public void SetRelativePos(Vector2 position) {
        BoundingRect.X = position.x;
        BoundingRect.Y = position.y;
    }

    public void SetAlignment(Alignment halign, Alignment valign) {
        SetHorizontalAlign(halign);
        SetVerticalAlign(valign);
    }

    public void SetHorizontalAlign(Alignment halign) {
        this.halign = halign;
    }

    public void SetVerticalAlign(Alignment valign) {
        this.valign = valign;
    }
}
