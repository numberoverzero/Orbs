package Rendering.Shapes;

import Math.Util;
import Math.Vec2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class Rectangle {
// ------------------------------ FIELDS ------------------------------

    static Texture texture = MakePixel();

// -------------------------- STATIC METHODS --------------------------

    static Texture MakePixel() {
        Texture _temp;
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(new Color(1, 1, 1, 1));
        p.fillRectangle(0, 0, 1, 1);
        _temp = new Texture(p, true);
        p.dispose();
        return _temp;
    }

    public static void Draw(SpriteBatch batch, Vec2 pos, Vec2 dimensions, Color color) {
        Draw(batch, pos, dimensions, 0, color);
    }

    public static void Draw(SpriteBatch batch, Vec2 pos, Vec2 dimensions, double rot, Color color) {
        Vec2 origin = dimensions.DividedBy(2);
        batch.setColor(color);
        batch.draw(texture, pos.X - origin.X, pos.Y - origin.Y, origin.X, origin.Y, dimensions.X, dimensions.Y,
                1, 1, Util.ToDegrees(rot), 0, 0, 1, 1, false, false);
    }

    public static void DrawOutline(SpriteBatch batch, Vec2 pos, Vec2 dimensions, double rot, Color color, float lineWidth) {
        batch.setColor(color);
        Vec2 dim2 = dimensions.DividedBy(2);
        Vec2[] corners = new Vec2[]{
                new Vec2(-dim2.X + lineWidth, -dim2.Y + lineWidth),
                new Vec2(-dim2.X + lineWidth, dim2.Y - lineWidth),
                new Vec2(dim2.X - lineWidth, dim2.Y - lineWidth),
                new Vec2(dim2.X - lineWidth, -dim2.Y + lineWidth),
                new Vec2(-dim2.X + lineWidth, -dim2.Y + lineWidth)};

        for (int i = 0; i < 5; i++) {
            corners[i] = corners[i].Rotate(rot);
            corners[i].Add(pos);
        }

        Vec2 scale = new Vec2(0, lineWidth);
        Vec2 segment;
        float lineRot;
        for (int i = 0; i < 4; i++) {
            segment = (corners[i + 1].Minus(corners[i]));
            scale.X = segment.Mag() + lineWidth;
            lineRot = (float) segment.Angle();
            batch.draw(texture, corners[i].X, corners[i].Y, 0, 0, scale.X, scale.Y,
                    1, 1, Util.ToDegrees(lineRot), 0, 0, 1, 1, false, false);
            //Draw(batch, corners[i], Vec2.Zero(), scale, segment.Angle(), color);
        }
    }
}
