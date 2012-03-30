package GameObjects;

import GameEvents.GameEventArgs;
import GameEvents.GameEventManager;
import GameEvents.GameObjectEvents.GameObjectCreatedEvent;
import GameObjects.Behaviors.IBehavior;
import GameObjects.Orders.Order;
import Physics.PhysicsComponent;
import Rendering.ColorScheme;

import java.util.ArrayList;

public class GameObject {
// ------------------------------ FIELDS ------------------------------

    public static GameEventManager EventManager;
    public boolean Active;
    public int Health;
    public float Timescale;
    public Hostility Hostility;
    public ArrayList<IBehavior> Behaviors;
    public PhysicsComponent Physics;
    public ColorScheme Colors;
    public Order CurrentOrder;
    boolean dirty;

// --------------------------- CONSTRUCTORS ---------------------------

    public GameObject() {
        this(0);
    }

    public GameObject(int health) {
        this(health, health > 0, true);
    }

    public GameObject(GameObject other) {
        dirty = other.dirty;
        Active = other.Active;
        Health = other.Health;
        Hostility = other.Hostility;
        Behaviors = new ArrayList<IBehavior>(other.Behaviors);
        Physics = new PhysicsComponent(other.Physics);
        Colors = other.Colors;
        CurrentOrder = other.CurrentOrder;
    }

    public GameObject(int health, boolean active, boolean fireOnCreateEvent) {
        Health = health;
        Active = active;
        Physics = new PhysicsComponent();
        Behaviors = new ArrayList<IBehavior>();
        Colors = new ColorScheme();
        CurrentOrder = Order.None;
        EventManager.AddEvent(new GameObjectCreatedEvent(this));
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean HasOrder() {
        return !(CurrentOrder == Order.None || CurrentOrder == Order.Any);
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
