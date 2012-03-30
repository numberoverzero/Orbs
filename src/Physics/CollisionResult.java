package Physics;

import GameObjects.GameObject;

/**
 * User: Joe Laptop
 * Date: 3/29/12
 * Time: 8:46 AM
 */
public class CollisionResult {
// ------------------------------ FIELDS ------------------------------

    public boolean Collided;
    public GameObject collider1, collider2;

// --------------------------- CONSTRUCTORS ---------------------------

    public CollisionResult() {
        Collided = false;
    }

    public CollisionResult(GameObject collider1, GameObject collider2) {
        Collided = true;
        this.collider1 = collider1;
        this.collider2 = collider2;
    }

// -------------------------- OTHER METHODS --------------------------

    public GameObject GetOther(GameObject gObject) {
        if (!Collided)
            return null;
        if (gObject != collider1 && gObject != collider2)
            return null;
        return gObject == collider2 ? collider1 : collider2;
    }
}
