package GameObjects;

import GameEvents.GameEventArgs;
import GameObjects.Orders.Order;
import Physics.PhysicsComponent;

import java.util.ArrayList;

public class GameObject {
    boolean dirty;
    public boolean Active;
    public int Health;
    public float Timescale;
    public Hostility Hostility;
    public ArrayList<Order> Orders;
    public PhysicsComponent Physics;

    public GameObject() {
        this(0, false);
    }

    public GameObject(int health) {
        this(health, true);
    }

    public GameObject(int health, boolean active) {
        Health = health;
        Active = active;
        Physics = new PhysicsComponent();
    }

    public void OnGameEvent(GameObject src, GameEventArgs args) {

    }
}
