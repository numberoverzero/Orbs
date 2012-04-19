package OrbGame.Behaviors;

import GameEvents.EventWatchers.EventWatchTiming;
import GameEvents.EventWatchers.IEventWatcher;
import GameEvents.GameEvent;
import GameEvents.GameObjectEvents.CollisionEventArgs;
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

    GameObjectIterable converterContainer;
    GameObject converterCollisionObject;
    boolean isEnabled = true;
    int minConversionHealth, maxConversionHealth;
    ArrayList<Hostility> convertableHostilities = new ArrayList<Hostility>();

// --------------------------- CONSTRUCTORS ---------------------------

    public OrbConversionBehavior(GameObjectIterable converterContainer,
                                 GameObject converterCollisionObject,
                                 int minConversionHealth,
                                 int maxConversionHealth) {
        this.converterContainer = converterContainer;
        this.converterCollisionObject = converterCollisionObject;
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
        converterCollisionObject = null;
        converterContainer = null;
        minConversionHealth = maxConversionHealth = 0;
        convertableHostilities = null;
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
        if (object == converterCollisionObject)
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
        CollisionEventArgs cEventArgs = (CollisionEventArgs) event.args;
        if (cEventArgs == null)
            return;
        GameObject collider = event.dst;
        GameObject potentialConvert = event.src;
        if (converterCollisionObject != null && converterCollisionObject == collider) {
            ApplyWithoutCollisionCheck(potentialConvert);
        } else {
            // Check the whole group for the collider
            if (converterContainer.contains(collider))
                ApplyWithoutCollisionCheck(potentialConvert);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public void AddConvertableHostility(Hostility hostility) {
        convertableHostilities.add(hostility);
    }

    private void ApplyWithoutCollisionCheck(GameObject object) {
        if (!isEnabled)
            return;
        if (object == converterCollisionObject)
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

        Orb newOrb = new Orb(asOrb, true);
        converterContainer.AddGameObject(newOrb);

        object.Destroy(true);
    }

    private boolean IsColliding(GameObject object) {
        if (converterCollisionObject == null) {
            // Check the whole group
            CollisionResult result = Collision.CheckIterable(converterContainer, object);
            return result.Collided;
        } else {
            return Collision.Check(converterCollisionObject, object);
        }
    }

    public void RemoveConvertableHostility(Hostility hostility) {
        convertableHostilities.remove(hostility);
    }
}
