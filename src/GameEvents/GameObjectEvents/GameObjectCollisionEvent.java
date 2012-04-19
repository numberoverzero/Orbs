package GameEvents.GameObjectEvents;

import GameEvents.GameEvent;
import GameEvents.GameEventArgs;
import GameObjects.GameObject;

/**
 * User: Joe Laptop
 * Date: 4/19/12
 * Time: 7:54 AM
 */
public class GameObjectCollisionEvent extends GameEvent {
// --------------------------- CONSTRUCTORS ---------------------------

    /*
    src - the object that collided with dst
    dst - the object that's being notified
    args may be null
     */
    public GameObjectCollisionEvent(GameObject src, GameObject dst, GameEventArgs args, float timeToSend) {
        super(src, dst, args, timeToSend);
    }
}
