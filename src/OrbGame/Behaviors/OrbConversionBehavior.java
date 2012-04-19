package OrbGame.Behaviors;

import GameEvents.EventWatchers.EventWatchTiming;
import GameEvents.EventWatchers.IEventWatcher;
import GameEvents.GameEvent;
import GameEvents.GameObjectEvents.GameObjectCollisionEvent;
import GameObjects.Behaviors.IBehavior;
import GameObjects.GameObject;
import GameObjects.GameObjectIterable;
import GameObjects.Hostility;
import OrbGame.Orb;
import Physics.Collision;
import Physics.CollisionResult;

import java.util.ArrayList;

public class OrbConversionBehavior implements IBehavior, IEventWatcher {
// ------------------------------ FIELDS ------------------------------

    GameObjectIterable converter;
    GameObject converterObject;
    boolean isEnabled = true;
    int minConversionHealth, maxConversionHealth;
    ArrayList<Hostility> convertableHostilities = new ArrayList<Hostility>();

// --------------------------- CONSTRUCTORS ---------------------------

    public OrbConversionBehavior(GameObjectIterable converter,
                                 GameObject converterObject,
                                 int minConversionHealth,
                                 int maxConversionHealth) {
        this.converter = converter;
        this.converterObject = converterObject;
        this.minConversionHealth = minConversionHealth;
        this.maxConversionHealth = maxConversionHealth;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface IBehavior ---------------------

    @Override
    public void Apply(GameObject object) {
        if (MeetsCriteria(object))
            Convert(object);
    }

    @Override
    public void Destroy() {
        isEnabled = false;
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
        if (!isEnabled)
            return false;
        if (object == converterObject)
            return false;
        if (object.Health < minConversionHealth ||
                object.Health > maxConversionHealth)
            return false;
        if (!IsColliding(object))
            return false;
        for (Hostility cHostility : convertableHostilities)
            if (object.Hostility == cHostility)
                return true;
        return false;
    }

    @Override
    public void Update(float dt) {
    }

// --------------------- Interface IEventWatcher ---------------------

    @Override
    public EventWatchTiming GetWatchTiming() {
        return EventWatchTiming.OnFire;
    }

    @Override
    public void InspectEvent(GameEvent event) {
        GameObjectCollisionEvent cEvent = (GameObjectCollisionEvent) event;
        if (cEvent == null)
            return;
        GameObject collider = cEvent.dst;
        GameObject potentialConvert = cEvent.src;
        if (converterObject != null && converterObject == collider) {
            ApplyWithoutCollisionCheck(potentialConvert);
        } else {
            // Check the whole group for the collider
            if (converter.contains(collider))
                ApplyWithoutCollisionCheck(potentialConvert);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private void ApplyWithoutCollisionCheck(GameObject object) {
        if (!isEnabled)
            return;
        if (object == converterObject)
            return;
        if (object.Health < minConversionHealth ||
                object.Health > maxConversionHealth)
            return;
        for (Hostility cHostility : convertableHostilities)
            if (object.Hostility == cHostility)
                Convert(object);
    }

    private void Convert(GameObject object) {
        Orb asOrb = (Orb) object;
        if (asOrb == null)
            return;
        Orb newOrb = new Orb(asOrb);
        converter.AddGameObject(newOrb);
    }

    private boolean IsColliding(GameObject object) {
        if (converterObject == null) {
            // Check the whole group
            CollisionResult result = Collision.CheckIterable(converter, object);
            return result.Collided;
        } else {
            return Collision.Check(converterObject, object);
        }
    }
}
