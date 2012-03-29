package Physics;

import GameObjects.GameObject;
import Math.Rect;
/**
 * User: Joe Laptop
 * Date: 3/28/12
 * Time: 5:01 PM
 */
public class PhysicsSystem {
    final double eps = 1E-7;
    public boolean Intersect(GameObject object1, GameObject object2)
    {
        if(Math.abs(object1.Physics.Rotation) < eps && Math.abs(object2.Physics.Rotation) < eps)
            return IntersectAAObjects(object1, object2);
        else
            return IntersectRotatedObjects(object1, object2);
    }
    
    boolean IntersectAAObjects(GameObject object1, GameObject object2)
    {
        return Rect.AreIntersecting(object1.Physics.GetAABB(), object2.Physics.GetAABB());
    }

    boolean IntersectRotatedObjects(GameObject object1, GameObject object2)
    {
        return true;
    }

    boolean IntersectInRefFrame(GameObject refObject, GameObject relObject)
    {
        return true;
    }

}
