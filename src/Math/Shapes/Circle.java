package Math.Shapes;

import Math.Vec2;

/**
 * User: Joe Laptop
 * Date: 4/5/12
 * Time: 8:42 AM
 */
public class Circle {
// ------------------------------ FIELDS ------------------------------

    public float Radius;
    public float CenterX, CenterY;

// --------------------------- CONSTRUCTORS ---------------------------

    public Circle() {
        this(0);
    }

    public Circle(float radius) {
        this(radius, 0, 0);
    }

    public Circle(float radius, Vec2 center) {
        this(radius, center.X, center.Y);
    }

    public Circle(float radius, float centerX, float centerY) {
        CenterX = centerX;
        CenterY = centerY;
        Radius = radius;
    }
    
    public Circle(Circle other)
    {
        CenterX = other.CenterX;
        CenterY = other.CenterY;
        Radius = other.Radius;
    }

// -------------------------- OTHER METHODS --------------------------

    public void CenterAt(Vec2 center) {
        CenterX = center.X;
        CenterY = center.Y;
    }

    public Vec2 GetCenter() {
        return new Vec2(CenterX, CenterY);
    }

    public void Translate(Vec2 offset) {
        CenterX += offset.X;
        CenterY += offset.Y;
    }
}
