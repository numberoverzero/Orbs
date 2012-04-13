package Math.Shapes;

import com.badlogic.gdx.math.Vector2;

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

    public Circle(Circle other) {
        CenterX = other.CenterX;
        CenterY = other.CenterY;
        Radius = other.Radius;
    }

    public Circle(float radius, Vector2 center) {
        this(radius, center.x, center.y);
    }

    public Circle(float radius, float centerX, float centerY) {
        CenterX = centerX;
        CenterY = centerY;
        Radius = radius;
    }

// -------------------------- OTHER METHODS --------------------------

    public void CenterAt(Vector2 center) {
        CenterX = center.x;
        CenterY = center.y;
    }

    public Vector2 GetCenter() {
        return new Vector2(CenterX, CenterY);
    }

    public void Translate(Vector2 offset) {
        CenterX += offset.x;
        CenterY += offset.y;
    }
}
