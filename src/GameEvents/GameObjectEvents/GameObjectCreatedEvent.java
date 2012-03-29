package GameEvents.GameObjectEvents;

import GameEvents.GameEvent;
import GameObjects.GameObject;

public class GameObjectCreatedEvent extends GameEvent {
    public GameObject Object;

    public GameObjectCreatedEvent(GameObject object) {
        super(object, null, null, 0);
        Object = object;
    }
}
