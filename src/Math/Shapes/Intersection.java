package Math.Shapes;

import Math.Util;
import Math.Vec2;

public final class Intersection {
// ------------------------------ FIELDS ------------------------------

    final double eps = 1E-7;

// -------------------------- STATIC METHODS --------------------------

    public static boolean Contains(Rect rect, Vec2 point) {
        return Contains(rect, point.X, point.Y, 0);
    }

    public static boolean Contains(Circle circle, Vec2 point) {
        return Contains(circle, point.X, point.Y, 0);
    }

    public static boolean Contains(Circle circle, float x, float y, float pad) {
        return Util.DistanceSquared(x - circle.CenterX, y - circle.CenterY)
                <= (circle.Radius + pad) * (circle.Radius + pad);
    }

    public static boolean Check(Rect rect, Circle circle) {
        // True does not guarantee collision.
        // False guarantees NOT collision.
        if (!Contains(rect, circle.CenterX, circle.CenterY, circle.Radius))
            return false;

        final Vec2 rectCenter = rect.GetCenter();
        final float centerDist = Util.Distance(circle.CenterX - rectCenter.X, circle.CenterY - rectCenter.Y);
        final float cornerDist = Util.Distance(rect.Width / 2, rect.Height / 2);
        return centerDist <= cornerDist + circle.Radius;
    }

    public static boolean Contains(Rect rect, float x, float y, float pad) {
        final float pad2 = pad / 2;
        return x >= rect.Left() - pad2 &&
                x <= rect.Right() + pad2 &&
                y >= rect.Bottom() - pad2 &&
                y <= rect.Top() + pad2;
    }

    public static boolean Check(Circle circle, Rect rect) {
        return Check(rect, circle);
    }

    public static boolean Check(Circle circle1, Circle circle2) {
        return Util.DistanceSquared(circle2.CenterX - circle1.CenterX, circle2.CenterY - circle1.CenterY)
                <= (circle1.Radius + circle2.Radius) * (circle1.Radius + circle2.Radius);
    }

    /*
        THIS MODIFIES BOTH rect AND circle.  PASS COPIES TO THIS FUNCTION
     */
    public static boolean Check(Rect rect, Circle circle, double rectRot) {
        Vec2 offset = rect.GetCenter();
        offset.Negate();

        // Move to the origin, center the rectangle there
        circle.Translate(offset);
        rect.Translate(offset);

        // Move to the unrotated coordinate system wrt the Rect (this is already done for the Rect, as OBB has 0 rot)
        Vec2 circleCenter = circle.GetCenter();
        circleCenter.Rotate(-rectRot);
        circle.CenterAt(circleCenter);

        // Now it's an unrotated rect vs circle check
        return Intersection.Check(rect, circle);
    }

    public static boolean Check(Rect rect1, Rect rect2, double rot1, double rot2) {
        // If the objects don't intersect in object1's ref, or they don't intersect in object2's ref,
        // then they can't intersect
        return !(DoesNotIntersectInRefFrame(new Rect(rect1), new Rect(rect2), rot1, rot2) ||
                DoesNotIntersectInRefFrame(new Rect(rect2), new Rect(rect1), rot2, rot1));
    }

    // Returns TRUE if the objects do not intersect in refRect's 0 rotation origin-centered perspective
    static boolean DoesNotIntersectInRefFrame(Rect refRect, Rect relRect, double refRotation, double relRotation) {
        Vec2 refCenter = refRect.GetCenter();
        Vec2 relCenter = relRect.GetCenter();

        // Translate relCenter by -refCenter, center rect at origin
        relCenter.Translate(refCenter.NegOut());
        refRect.CenterAt(Vec2.Zero());

        // RotateOut our relCenter by negative ref rotation, so that we have effectively
        // put our ref into a coordinate system (as seen by rel) centered at the origin w/o rotation
        relCenter.Rotate(-refRotation);

        // GetCenter relRect at relCenter, then grab it's minAABB
        relRect.CenterAt(relCenter);
        double theta = relRotation - refRotation;
        Rect minRelAABB = relRect.MinBoundsOf(theta);

        // Check our minRelAABB against our refComponent,
        // with our refComponent's AABB centered at the origin
        return !Check(minRelAABB, refRect);
    }

    public static boolean Check(Rect rect1, Rect rect2) {
        return ((rect1.Right() >= rect2.Left()) &&
                (rect1.Left() <= rect2.Right()) &&
                (rect1.Top() >= rect2.Bottom()) &&
                (rect1.Bottom() <= rect2.Top()));
    }
}
