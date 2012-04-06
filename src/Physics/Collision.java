package Physics;

import GameObjects.GameObject;
import Math.Shapes.Intersection;

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

    boolean IntersectRotatedObjects(PhysicsComponent physics1, PhysicsComponent physics2) {
        ColliderType type1 = physics1.ColliderType;
        ColliderType type2 = physics2.ColliderType;

        if (type1 == ColliderType.Circle && type2 == ColliderType.Rectangle)
            return Intersection.Check(physics2.GetOBB(), physics1.GetBoundingCircle(), physics2.Rotation);

        else if (type1 == ColliderType.Rectangle && type2 == ColliderType.Circle)
            return Intersection.Check(physics1.GetOBB(), physics2.GetBoundingCircle(), physics1.Rotation);

        else if (type1 == ColliderType.Rectangle && type2 == ColliderType.Rectangle)
            return Intersection.Check(physics1.GetOBB(), physics2.GetOBB(), physics1.Rotation, physics2.Rotation);
        else
            return false;
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
