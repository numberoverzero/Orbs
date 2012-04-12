package UI;

import Math.Shapes.Rect;
import Math.Vec2;
import Rendering.RenderPass;
import ScreenManagement.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UIElement {
// ------------------------------ FIELDS ------------------------------

    protected boolean visible;
    protected Alignment halign, valign;
    protected boolean hexpand, vexpand;
    public UIContainer Parent;
    protected Screen screen;
    public Screen GetScreen()
    {
        if(screen != null)
            return screen;
        if(Parent != null)
            return Parent.GetScreen();
        // Null-safe return
        return Screen.NoneScreen();
    }

    // X, Y here refer to offset from parent's corner
    public Rect BoundingRect;

    protected String id;

// --------------------------- CONSTRUCTORS ---------------------------

    public UIElement(String id, boolean appendSuffix) {
        BoundingRect = new Rect();
        halign = valign = Alignment.Center;
        hexpand = vexpand = false;
        visible = true;

        if (appendSuffix)
            this.id = id.concat(GetControlSuffix());
        UIManager.Register(this);
    }

    public abstract String GetControlSuffix();

// -------------------------- OTHER METHODS --------------------------

    public abstract void Render(SpriteBatch batch, RenderPass pass);

    public Vec2 GetAbsolutePos(){
        Vec2 pos = GetRelativePos();
        if (Parent != null)
            pos.Add(Parent.GetAbsolutePos());
        else
        {
            // If parent is null, grab the screen and make sure we offset by that.
            // This will allow screens to handle transition position entirely by updating the bottom left corner
            pos.Add(GetScreen().GetBottomLeftPosition());
        }
        return pos;
    }

    public Vec2 GetRelativePos() {
        return new Vec2(BoundingRect.X, BoundingRect.Y);
    }
    
    public void SetRelativePos(Vec2 position)
    {
        BoundingRect.X = position.X;
        BoundingRect.Y = position.Y;
    }
    
    public void SetAbsolutePos(Vec2 position)
    {
        // Calculate global offset less relative offset
        Vec2 globalOffset = GetAbsolutePos();
        globalOffset.Sub(GetRelativePos());

        Vec2 newRel = new Vec2(position);
        newRel.Sub(globalOffset);
        SetRelativePos(newRel);
    }

    public String GetID() {
        return id;
    }

    public void Destroy() {
        if(Parent != null)
            Parent.RemoveChild(this);
        UIManager.UnRegister(this);
    }

    public void SetHorizontalAlign(Alignment halign)
    {
        this.halign = halign;
    }
    public void SetVerticalAlign(Alignment valign)
    {
        this.valign = valign;
    }
    public void SetAlignment(Alignment halign, Alignment valign)
    {
        SetHorizontalAlign(halign);
        SetVerticalAlign(valign);
    }

    public void DetermineSize() { }

    public void ResetSize(Vec2 dimensions)
    {
        BoundingRect.SetDimensions(dimensions);
    }
}
