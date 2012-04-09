package Rendering.Shapes;

import Math.Shapes.Rect;
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

    public static void Draw(SpriteBatch batch, Rect rect, Color color) {
        Draw(batch, new Vec2(rect.X, rect.Y), rect.Dimensions(), color);
    }

    public static void Draw(SpriteBatch batch, Vec2 pos, Vec2 dimensions, Color color) {
        Draw(batch, pos, dimensions, 0, color);
    }

    public static void Draw(SpriteBatch batch, Vec2 pos, Vec2 dimensions, double rot, Color color) {
        batch.setColor(color);
        batch.draw(texture, pos.X - dimensions.X / 2, pos.Y - dimensions.Y / 2, dimensions.X / 2, dimensions.Y / 2,
                dimensions.X, dimensions.Y, 1, 1, Util.ToDegrees(rot), 0, 0, 1, 1, false, false);
    }

    public static void DrawOutline(SpriteBatch batch, Vec2 pos, Vec2 dimensions,
                                   double theta, Color color, float borderWidth) {
        batch.setColor(color);
        double[] corners = Util.GetRectCorners(dimensions, borderWidth);
        Util.Rotate(corners, theta);
        Util.Offset(corners, pos);

        float scaleX;
        double segRot, dx, dy;
        for (int i = 0; i < 8; i += 2) {
            dy = corners[i + 3] - corners[i + 1];
            dx = corners[i + 2] - corners[i];
            scaleX = Util.Distance(dx, dy) + borderWidth;
            segRot = Util.Angle(dy, dx);
            batch.draw(texture, (float) corners[i], (float) corners[i + 1], 0, 0, scaleX, borderWidth,
                    1, 1, Util.ToDegrees(segRot), 0, 0, 1, 1, false, false);
        }
    }
}
