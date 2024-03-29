package GameObjects;

import GameEvents.GameEventArgs;
import GameEvents.GameEventManager;
import GameEvents.GameObjectEvents.GameObjectCreatedEvent;
import GameEvents.GameObjectEvents.GameObjectDestroyedEvent;
import GameObjects.Behaviors.IBehavior;
import GameObjects.Orders.Order;
import Physics.PhysicsComponent;
import Rendering.ColorScheme;
import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameObject {
// ------------------------------ FIELDS ------------------------------

    public static GameEventManager EventManager = GameEventManager.GlobalEventManager();
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

    public GameObject(GameObject other, boolean fireOnCreateEvent) {
        dirty = other.dirty;
        Active = other.Active;
        Health = other.Health;
        Hostility = other.Hostility;
        Behaviors = new ArrayList<IBehavior>(other.Behaviors);
        Physics = new PhysicsComponent(other.Physics);
        Colors = other.Colors;
        CurrentOrder = other.CurrentOrder;
        if (fireOnCreateEvent)
            EventManager.AddEvent(new GameObjectCreatedEvent(this));
    }

    public GameObject(int health, boolean active, boolean fireOnCreateEvent) {
        Health = health;
        Active = active;
        Physics = new PhysicsComponent();
        Behaviors = new ArrayList<IBehavior>();
        Colors = new ColorScheme();
        CurrentOrder = Order.None;
        if (fireOnCreateEvent)
            EventManager.AddEvent(new GameObjectCreatedEvent(this));
    }

// -------------------------- OTHER METHODS --------------------------

    public void AddBehavior(IBehavior behavior) {
        Behaviors.add(behavior);
    }

    public void Destroy(boolean fireOnDestroyEvent) {
        Active = false;
        if (fireOnDestroyEvent)
            EventManager.AddEvent(new GameObjectDestroyedEvent(this));
    }

    public void Draw(SpriteBatch batch, RenderPass pass) {
    }

    public boolean HasOrder() {
        return !(CurrentOrder == Order.None || CurrentOrder == Order.Any);
    }

    public void OnGameEvent(GameObject src, GameEventArgs args) {
    }

    public void RemoveBehavior(IBehavior behavior) {
        Behaviors.remove(behavior);
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
