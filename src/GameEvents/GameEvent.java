package GameEvents;

import GameObjects.GameObject;

public class GameEvent {
// ------------------------------ FIELDS ------------------------------

    public GameObject src, dst;
    public GameEventArgs args;
    float timeToSend;
    boolean hasFired;

// --------------------------- CONSTRUCTORS ---------------------------

    public GameEvent(GameObject src, GameObject dst, GameEventArgs args, float timeToSend) {
        this.src = src;
        this.dst = dst;
        this.args = args;
        this.timeToSend = timeToSend;
        hasFired = timeToSend < 0 ? true : false;
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean HasFired() {
        return hasFired;
    }

    public void Update(float dt) {
        timeToSend -= dt;
        if (!hasFired && timeToSend <= 0)
            Fire();
    }

    private void Fire() {
        hasFired = true;
        if (dst != null)
            dst.OnGameEvent(src, args);
    }
}
