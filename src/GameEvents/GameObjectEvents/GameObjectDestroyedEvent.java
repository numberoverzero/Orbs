package GameEvents.GameObjectEvents;

import GameEvents.GameEvent;
import GameObjects.GameObject;

/**
 * User: Joe Laptop
 * Date: 4/19/12
 * Time: 6:17 PM
 */
public class GameObjectDestroyedEvent extends GameEvent {
// ------------------------------ FIELDS ------------------------------

    public GameObject Object;

// --------------------------- CONSTRUCTORS ---------------------------

    public GameObjectDestroyedEvent(GameObject object) {
        super(object, null, null, 0);
        Object = object;
    }
}
