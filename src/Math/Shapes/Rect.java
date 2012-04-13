package Math.Shapes;

import Math.Util;
import com.badlogic.gdx.math.Vector2;

public class Rect {
// ------------------------------ FIELDS ------------------------------

    public float X, Y, Width, Height;

// -------------------------- STATIC METHODS --------------------------

    public static Rect MinBoundsOf(Rect rect, double rot) {
        Vector2 pivot = rect.GetCenter();
        Vector2[] corners = rect.Corners();
        float minX = pivot.x, maxX = pivot.x, minY = pivot.y, maxY = pivot.y;
        for (Vector2 corner : corners) {
            Util.RotateInPlace(corner, pivot, rot);
            minX = minX < corner.x ? minX : corner.x;
            maxX = maxX > corner.x ? maxX : corner.x;
            minY = minY < corner.y ? minY : corner.y;
            maxY = maxY > corner.y ? maxY : corner.y;
        }
        return new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    public Vector2 GetCenter() {
        return new Vector2(Left() + Width / 2, Bottom() + Height / 2);
    }

    public float Left() {
        return X;
    }

    public float Bottom() {
        return Y;
    }

    public Vector2[] Corners() {
        Vector2[] corners = new Vector2[4];
        corners[0] = new Vector2(Left(), Bottom());
        corners[1] = new Vector2(Right(), Bottom());
        corners[2] = new Vector2(Right(), Top());
        corners[3] = new Vector2(Left(), Top());
        return corners;
    }

    public float Right() {
        return X + Width;
    }

    public float Top() {
        return Y + Height;
    }

    public static Rect MinBoundsOf(Vector2 center, float width, float height, float rot) {
        Rect rect = Rect.CenteredAt(center, width, height);
        return MinBoundsOf(rect, rot);
    }

    public static Rect CenteredAt(Vector2 center, float width, float height) {
        float hw = width / 2;
        float hh = height / 2;
        return new Rect(center.x - hw, center.y - hh, width, height);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Rect() {
        this(0, 0, 0, 0);
    }

    public Rect(Rect other) {
        X = other.X;
        Y = other.Y;
        Width = other.Width;
        Height = other.Height;
    }

    public Rect(float x, float y, float width, float height) {
        X = x;
        Y = y;
        Width = width;
        Height = height;
    }

// -------------------------- OTHER METHODS --------------------------

    public void CenterAt(Vector2 center) {
        X = center.x - Width / 2;
        Y = center.y - Height / 2;
    }

    public Vector2 Dimensions() {
        return new Vector2(Width, Height);
    }

    public Rect MinBoundsOf(double rot) {
        return MinBoundsOf(this, rot);
    }

    public void Translate(Vector2 offset) {
        X += offset.x;
        Y += offset.y;
    }
    
    public void SetDimensions(Vector2 dimensions)
    {
        Width = dimensions.x;
        Height = dimensions.y;
    }
}
