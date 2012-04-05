package Physics;

import GameObjects.GameObject;
import Math.Rect;
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
        if (Math.abs(object1.Physics.Rotation) < eps && Math.abs(object2.Physics.Rotation) < eps)
            return IntersectAAObjects(object1, object2);
        else
            return IntersectRotatedObjects(object1, object2);
    }

    boolean IntersectAAObjects(GameObject object1, GameObject object2) {
        return Rect.AreIntersecting(object1.Physics.GetAABB(), object2.Physics.GetAABB());
    }

    // If the objects don't intersect in object1's ref, or they don't intersect in object2's ref,
    // then they can't intersect
    boolean IntersectRotatedObjects(GameObject object1, GameObject object2) {
        return !(DoesNotIntersectInRefFrame(object1, object2) || DoesNotIntersectInRefFrame(object2, object1));
    }

    // Returns TRUE if the objects do no intersect in refObject's 0 rotation origin-centered perspective
    boolean DoesNotIntersectInRefFrame(GameObject refObject, GameObject relObject) {
        double refRotation = refObject.Physics.Rotation;
        double relRotation = relObject.Physics.Rotation;

        Rect refRect = refObject.Physics.GetAABB();
        Rect relRect = relObject.Physics.GetAABB();

        Vec2 refCenter = refRect.Center();
        Vec2 relCenter = relRect.Center();

        // Translate relCenter by refCenter
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
        return !Rect.AreIntersecting(minRelAABB, refRect);
    }
}
