package GameObjects.Behaviors;

import GameObjects.GameObject;
import Math.Util;

public class PrimitiveSteeringBehavior implements IBehavior {
// ------------------------------ FIELDS ------------------------------

    boolean isEnabled = true;

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
    public void Disable() {
        isEnabled = false;
    }

    @Override
    public void Enable() {
        isEnabled = true;
    }

    @Override
    public boolean IsEnabled() {
        return isEnabled;
    }

    @Override
    public boolean MeetsCriteria(GameObject object) {
        return true;
    }

    @Override
    public void Update(float dt) {
    }
}
