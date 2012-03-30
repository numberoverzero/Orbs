package GameObjects.Orders;

public enum Order {
    // Used for easing transitions between orders
    Any,
    None,

    // Aggression orders
    Defend,
    Attack,

    // Orbit orders
    Seek,
    Hover,

    // Engagement orders
    Roam,
    Swarm
}
