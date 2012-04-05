package Physics;

import GameObjects.GameObject;
import Math.Shapes.Circle;
import Math.Shapes.Intersection;
import Math.Shapes.Rect;
import Math.Vec2;

public final class Collision {
// ------------------------------ FIELDS ------------------------------

    final double eps = 1E-7;

// -------------------------- OTHER METHODS --------------------------

    public CollisionResult Check(Iterable<GameObject> gObjIter1, Iterable<GameObject> gObjIter2) {
        for (GameObject gObj1 : gObjIter1) {
            for (GameObject gObj2 : gObjIter2) {
                if (Check(gObj1, gObj2))
                    return new CollisionResult(gObj1, gObj2);
            }
        }
        return new CollisionResult();
    }

    public CollisionResult Check(Iterable<GameObject> gObjIter, GameObject gameObject) {
        for (GameObject gObj : gObjIter) {
            if (Check(gObj, gameObject))
                return new CollisionResult(gObj, gameObject);
        }
        return new CollisionResult();
    }

    public boolean Check(GameObject object1, GameObject object2) {
        PhysicsComponent physics1 = object1.Physics;
        PhysicsComponent physics2 = object2.Physics;
        boolean mayNeedRotatedCheck = Math.abs(object1.Physics.Rotation) < eps &&
                Math.abs(object2.Physics.Rotation) < eps;
        boolean hasNonTrivialRotation = physics1.ColliderType != ColliderType.Circle ||
                physics2.ColliderType != ColliderType.Circle;

        if (mayNeedRotatedCheck && hasNonTrivialRotation)
            return IntersectRotatedObjects(physics1, physics2);
        else
            return IntersectAlignedObjects(physics1, physics2);
    }

    // If the objects don't intersect in object1's ref, or they don't intersect in object2's ref,
    // then they can't intersect
    boolean IntersectRotatedObjects(PhysicsComponent physics1, PhysicsComponent physics2) {
        ColliderType type1 = physics1.ColliderType;
        ColliderType type2 = physics2.ColliderType;

        if (type1 == ColliderType.Circle && type2 == ColliderType.Rectangle)
            return IntersectCircleRect(physics1, physics2);

        else if (type1 == ColliderType.Rectangle && type2 == ColliderType.Circle)
            return IntersectCircleRect(physics2, physics1);

        else if (type1 == ColliderType.Rectangle && type2 == ColliderType.Rectangle)
            return !(DoesNotIntersectInRefFrame(physics1, physics2) ||
                    DoesNotIntersectInRefFrame(physics2, physics1));
        else
            return false;
    }

    boolean IntersectCircleRect(PhysicsComponent physicsCircle, PhysicsComponent physicsRect) {
        double theta = physicsRect.Rotation;
        Circle circle = physicsCircle.GetBoundingCircle();
        Rect rect = physicsRect.GetOBB();
        Vec2 offset = rect.Center();
        offset.Negate();

        // Move to the origin, center the rectangle there
        circle.Translate(offset);
        rect.Translate(offset);

        // Move to the unrotated coordinate system wrt the Rect (this is already done for the Rect, as OBB has 0 rot)
        Vec2 circleCenter = circle.GetCenter();
        Vec2.Rotate(circleCenter, -theta);
        circle.CenterAt(circleCenter);

        // Now it's an unrotated rect vs circle check
        return Intersection.Check(rect, circle);
    }

    // Returns TRUE if the objects do no intersect in refPhysics's 0 rotation origin-centered perspective
    boolean DoesNotIntersectInRefFrame(PhysicsComponent refPhysics, PhysicsComponent relPhysics) {
        double refRotation = refPhysics.Rotation;
        double relRotation = relPhysics.Rotation;

        Rect refRect = refPhysics.GetOBB();
        Rect relRect = relPhysics.GetOBB();

        Vec2 refCenter = refRect.Center();
        Vec2 relCenter = relRect.Center();

        // Translate relCenter by -refCenter
        relCenter.Translate(refCenter.NegOut());

        // RotateOut our relCenter by negative ref rotation, so that we have effectively
        // put our ref into a coordinate system (as seen by rel) centered at the origin w/o rotation
        Vec2.Rotate(relCenter, -refRotation);

        // Center relRect at relCenter, then grab it's minAABB
        relRect.CenterAt(relCenter);
        double theta = relRotation - refRotation;
        Rect minRelAABB = relRect.MinBoundsOf(theta);

        // Check our minRelAABB against our refComponent,
        // with our refComponent's AABB centered at the origin
        refRect.CenterAt(Vec2.Zero());
        return !Intersection.Check(minRelAABB, refRect);
    }

    boolean IntersectAlignedObjects(PhysicsComponent physics1, PhysicsComponent physics2) {
        ColliderType type1 = physics1.ColliderType;
        ColliderType type2 = physics2.ColliderType;
        if (type1 == ColliderType.Circle && type2 == ColliderType.Circle)
            return Intersection.Check(physics1.GetBoundingCircle(), physics2.GetBoundingCircle());

        else if (type1 == ColliderType.Circle && type2 == ColliderType.Rectangle)
            return Intersection.Check(physics1.GetBoundingCircle(), physics2.GetOBB());

        else if (type1 == ColliderType.Rectangle && type2 == ColliderType.Circle)
            return Intersection.Check(physics1.GetOBB(), physics2.GetBoundingCircle());

        else if (type1 == ColliderType.Rectangle && type2 == ColliderType.Rectangle)
            return Intersection.Check(physics1.GetOBB(), physics2.GetOBB());

        else
            return false;
    }
}
