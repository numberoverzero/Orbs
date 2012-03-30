package GameObjects;

/**
 * User: Joe Laptop
 * Date: 3/30/12
 * Time: 7:52 AM
 */
public class Orb extends GameObject {
// ------------------------------ FIELDS ------------------------------

    public int Size;

// --------------------------- CONSTRUCTORS ---------------------------

    public Orb(int size, int health, boolean active, boolean fireOnCreateEvent) {
        super(health, active, fireOnCreateEvent);
        Size = size;
    }
}
