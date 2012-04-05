package Math.Shapes;

import Math.Util;
import Math.Vec2;

/**
 * User: Joe Laptop
 * Date: 4/5/12
 * Time: 9:45 AM
 */
public final class Intersection {
// -------------------------- STATIC METHODS --------------------------

    public static boolean Contains(Rect rect, Vec2 point)
    {
        return Contains(rect, point.X, point.Y, 0);
    }

    public static boolean Contains(Circle circle, Vec2 point)
    {
        return Contains(circle, point.X, point.Y, 0);
    }

    public static boolean Contains(Circle circle, float x, float y, float pad)
    {
        return Util.DistanceSquared(x - circle.CenterX, y - circle.CenterY)
                <= (circle.Radius + pad) * (circle.Radius + pad);
    }

    public static boolean Check(Rect rect, Circle circle)
    {
        // True does not guarantee collision.
        // False contains guarantees NOT collision.
        if(!Contains(rect, circle.CenterX, circle.CenterY, circle.Radius))
            return false;

        final Vec2 rectCenter = rect.Center();
        final float centerDist = Util.Distance(circle.CenterX - rectCenter.X, circle.CenterY - rectCenter.Y);
        final float cornerDist = Util.Distance(rect.Width/2, rect.Height/2);
        return centerDist <= cornerDist + circle.Radius;
    }

    public static boolean Contains(Rect rect, float x, float y, float pad)
    {
        final float pad2 = pad/2;
        return x >= rect.Left()-pad2 &&
                x <= rect.Right()+pad2 &&
                y >= rect.Bottom()-pad2 &&
                y <= rect.Top()+pad2;
    }

    public static boolean Check(Circle circle, Rect rect)
    {
        return Check(rect, circle);
    }

    public static boolean Check(Circle circle1, Circle circle2)
    {
        return Util.DistanceSquared(circle2.CenterX - circle1.CenterX, circle2.CenterY - circle1.CenterY)
                <= (circle1.Radius+circle2.Radius) * (circle1.Radius+circle2.Radius);
    }

    public static boolean Check(Rect rect1, Rect rect2)
    {
        return ((rect1.Right() >= rect2.Left()) &&
                (rect1.Left() <= rect2.Right()) &&
                (rect1.Top() >= rect2.Bottom()) &&
                (rect1.Bottom() <= rect2.Top()));
    }
}
