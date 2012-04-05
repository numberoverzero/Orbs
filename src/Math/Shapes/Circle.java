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

    public Circle()
    {
        this(0);
    }

    public Circle(float radius)
    {
        this(radius, 0, 0);
    }

    public Circle(float radius, Vec2 center)
    {
        this(radius, center.X, center.Y);
    }

    public Circle(float radius, float centerX, float centerY)
    {
        CenterX = centerX;
        CenterY = centerY;
        Radius = radius;
    }

// -------------------------- OTHER METHODS --------------------------

    public Vec2 GetCenter()
    {
        return new Vec2(CenterX, CenterY);
    }
}
