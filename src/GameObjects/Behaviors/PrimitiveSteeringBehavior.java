package GameObjects.Behaviors;

import GameObjects.GameObject;
import Math.Util;

public class PrimitiveSteeringBehavior implements IBehavior {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface IBehavior ---------------------

    @Override
    public void Apply(GameObject object) {
        object.Physics.Rotation = Util.Angle(object.Physics.Velocity);
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
