package GameObjects;

import GameEvents.GameEventArgs;
import GameObjects.Orders.Order;

import java.util.ArrayList;

public class GameObject {
    boolean active;
    boolean dirty;
    public int Health;
    public float Timescale;
    public Hostility Hostility;
    public ArrayList<Order> Orders;

    public boolean IsActive()
    {
        return active;
    }


    public GameObject()
    {
        this(0, false);
    }
    public GameObject(int health)
    {
        this(health, true);
    }
    public GameObject(int health, boolean active)
    {
        this.Health = health;
        this.active = active;
    }
    
    public void OnGameEvent(GameObject src, GameEventArgs args)
    {

    }
}
