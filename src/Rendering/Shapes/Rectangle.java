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
        batch.setColor(color);
        batch.draw(texture, pos.X - dimensions.X / 2, pos.Y - dimensions.Y / 2, dimensions.X / 2, dimensions.Y / 2,
                dimensions.X, dimensions.Y, 1, 1, Util.ToDegrees(rot), 0, 0, 1, 1, false, false);
    }

    public static void DrawOutline(SpriteBatch batch, Vec2 pos, Vec2 dimensions,
                                   double rot, Color color, float lineWidth) {
        batch.setColor(color);

        double dim2x = dimensions.X / 2;
        double dim2y = dimensions.Y / 2;
        // Here dim2x/y are the half dimensions of the rectangle
        double[] corners = new double[]{
                -dim2x + lineWidth, -dim2y + lineWidth,
                -dim2x + lineWidth, dim2y - lineWidth,
                dim2x - lineWidth, dim2y - lineWidth,
                dim2x - lineWidth, -dim2y + lineWidth,
                -dim2x + lineWidth, -dim2y + lineWidth};

        // Here dim2x/y are the rotated x/y positions of the corner being rotated
        final double st = Math.sin(rot);
        final double ct = Math.cos(rot);
        for (int i = 0; i < 10; i += 2) {
            dim2x = (ct * corners[i]) - (st * corners[i + 1]);
            dim2y = (st * corners[i]) + (ct * corners[i + 1]);
            corners[i] = dim2x + pos.X;
            corners[i + 1] = dim2y + pos.Y;
        }

        // Here dim2x/y are the delta x/y values between this corner and the next
        float scaleX;
        double segRot;
        for (int i = 0; i < 8; i += 2) {
            dim2y = corners[i + 3] - corners[i + 1];
            dim2x = corners[i + 2] - corners[i];
            scaleX = Util.Distance(dim2x, dim2y) + lineWidth;
            segRot = Util.Angle(dim2y, dim2x);
            batch.draw(texture, (float) corners[i], (float) corners[i + 1], 0, 0, scaleX, lineWidth,
                    1, 1, Util.ToDegrees(segRot), 0, 0, 1, 1, false, false);
        }
    }

    /*// Old draw method using Vec2s, lots of allocation.
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

        Vec2 segment, scale = new Vec2(0, lineWidth);
        float lineRot;
        for (int i = 0; i < 4; i++) {
            segment = (corners[i + 1].Minus(corners[i]));
            scale.X = segment.Mag() + lineWidth;
            lineRot = (float) segment.Angle();
            batch.draw(texture, corners[i].X, corners[i].Y, 0, 0, scale.X, scale.Y,
                    1, 1, Util.ToDegrees(lineRot), 0, 0, 1, 1, false, false);
        }
    }
    */
}
