package OrbGame;

import GameObjects.GameObject;
import Rendering.RenderLayer;
import Rendering.RenderPass;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * User: Joe Laptop
 * Date: 3/30/12
 * Time: 7:52 AM
 */
public class Orb extends GameObject {
// ------------------------------ FIELDS ------------------------------

    static final float BORDER_PCT = 0.04f;
    public int Size;

// --------------------------- CONSTRUCTORS ---------------------------

    public Orb(Orb orb) {
        super(orb);
        Size = orb.Size;
    }

    public Orb(int size, int health, boolean active, boolean fireOnCreateEvent) {
        super(health, active, fireOnCreateEvent);
        Size = size;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void Draw(SpriteBatch batch, RenderPass pass) {
        if (pass == RenderPass.Effects) {
            Color color;
            Vector2 dimensions = new Vector2(Size, Size);

            //Base layer - solid background color
            color = Colors.GetColor(Hostility, RenderLayer.Base);
            Rendering.Shapes.Rectangle.Draw(batch, Physics.Position, dimensions, Physics.Rotation, color);

            //Highlight layer - Thin Border
            color = Colors.GetColor(Hostility, RenderLayer.Highlight);
            float lineWidth = BORDER_PCT * Size;
            Rendering.Shapes.Rectangle.DrawOutline(batch, Physics.Position, dimensions,
                    Physics.Rotation, color, lineWidth);
        }
    }
}
