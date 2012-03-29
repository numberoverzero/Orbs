package GameObjects;

import GameEvents.GameEventArgs;
import GameEvents.GameEventManager;
import GameEvents.GameObjectEvents.GameObjectCreatedEvent;
import GameObjects.Behaviors.IBehavior;
import Physics.PhysicsComponent;
import Rendering.ColorScheme;

import java.util.ArrayList;

public class GameObject {
    boolean dirty;
    public boolean Active;
    public int Health;
    public float Timescale;
    public Hostility Hostility;
    public ArrayList<IBehavior> Behaviors;
    public PhysicsComponent Physics;
    public ColorScheme Colors;
    public GameEventManager EventManager;

    public GameObject(GameEventManager eventManager) {
        this(0, eventManager);
    }

    public GameObject(int health, GameEventManager eventManager) {
        this(health, health > 0, true, eventManager);
    }

    public GameObject(int health, boolean active, boolean fireOnCreateEvent, GameEventManager eventManager) {
        Health = health;
        Active = active;
        Physics = new PhysicsComponent();
        Behaviors = new ArrayList<IBehavior>();
        Colors = new ColorScheme();
        if (eventManager == null)
            EventManager = GameEventManager.GlobalEventManager();
        else
            EventManager = eventManager;

        if (fireOnCreateEvent)
            EventManager.AddEvent(new GameObjectCreatedEvent(this));
    }

    public GameObject(GameObject other) {
        dirty = other.dirty;
        Active = other.Active;
        Health = other.Health;
        Hostility = other.Hostility;
        Behaviors = new ArrayList<IBehavior>(other.Behaviors);
        Physics = new PhysicsComponent(other.Physics);
        Colors = other.Colors;
        EventManager = other.EventManager;
    }

    public void OnGameEvent(GameObject src, GameEventArgs args) {

    }

    public void Update(float dt) {
        UpdateBehaviors(dt);
    }

    void UpdateBehaviors(float dt) {
        for (IBehavior behavior : Behaviors) {
            behavior.Update(dt);
            if (behavior.MeetsCriteria(this)) {
                behavior.Apply(this);
            }
        }
    }
}
