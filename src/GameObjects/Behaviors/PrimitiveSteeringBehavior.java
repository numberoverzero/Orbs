package GameObjects.Behaviors;

import GameObjects.GameObject;

/**
 * User: Joe Laptop
 * Date: 4/3/12
 * Time: 8:10 AM
 */
public class PrimitiveSteeringBehavior implements IBehavior {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface IBehavior ---------------------

    @Override
    public void Apply(GameObject object) {
        object.Physics.Rotation = object.Physics.Velocity.Angle();
    }

    @Override
    public void Destroy() {
    }

    @Override
    public boolean MeetsCriteria(GameObject object) {
        return true;
    }

    @Override
    public void Update(float dt) {
    }
}
