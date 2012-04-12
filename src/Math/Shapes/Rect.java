package Math.Shapes;

import Math.Vec2;

public class Rect {
// ------------------------------ FIELDS ------------------------------

    public float X, Y, Width, Height;

// -------------------------- STATIC METHODS --------------------------

    public static Rect MinBoundsOf(Rect rect, double rot) {
        Vec2 pivot = rect.GetCenter();
        Vec2[] corners = rect.Corners();
        float minX = pivot.X, maxX = pivot.X, minY = pivot.Y, maxY = pivot.Y;
        for (Vec2 corner : corners) {
            Vec2.Rotate(corner, pivot, rot);
            minX = minX < corner.X ? minX : corner.X;
            maxX = maxX > corner.X ? maxX : corner.X;
            minY = minY < corner.Y ? minY : corner.Y;
            maxY = maxY > corner.Y ? maxY : corner.Y;
        }
        return new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    public Vec2 GetCenter() {
        return new Vec2(Left() + Width / 2, Bottom() + Height / 2);
    }

    public float Left() {
        return X;
    }

    public float Bottom() {
        return Y;
    }

    public Vec2[] Corners() {
        Vec2[] corners = new Vec2[4];
        corners[0] = new Vec2(Left(), Bottom());
        corners[1] = new Vec2(Right(), Bottom());
        corners[2] = new Vec2(Right(), Top());
        corners[3] = new Vec2(Left(), Top());
        return corners;
    }

    public float Right() {
        return X + Width;
    }

    public float Top() {
        return Y + Height;
    }

    public static Rect MinBoundsOf(Vec2 center, float width, float height, float rot) {
        Rect rect = Rect.CenteredAt(center, width, height);
        return MinBoundsOf(rect, rot);
    }

    public static Rect CenteredAt(Vec2 center, float width, float height) {
        float hw = width / 2;
        float hh = height / 2;
        return new Rect(center.X - hw, center.Y - hh, width, height);
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

    public void CenterAt(Vec2 center) {
        X = center.X - Width / 2;
        Y = center.Y - Height / 2;
    }

    public Vec2 Dimensions() {
        return new Vec2(Width, Height);
    }

    public Rect MinBoundsOf(double rot) {
        return MinBoundsOf(this, rot);
    }

    public void Translate(Vec2 offset) {
        X += offset.X;
        Y += offset.Y;
    }
    
    public void SetDimensions(Vec2 dimensions)
    {
        Width = dimensions.X;
        Height = dimensions.Y;
    }
}
