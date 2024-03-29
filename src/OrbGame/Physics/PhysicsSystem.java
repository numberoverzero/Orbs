package OrbGame.Physics;

import GameEvents.GameEvent;
import GameEvents.GameEventManager;
import GameEvents.GameObjectEvents.CollisionEventArgs;
import GameObjects.GameObjectIterable;
import OrbGame.Orb;
import Physics.Collision;

public class PhysicsSystem {
// ------------------------------ FIELDS ------------------------------

    public static GameEventManager EventManager = GameEventManager.GlobalEventManager();

    public GameObjectIterable<Orb> player;
    public GameObjectIterable<Orb> enemy;
    public GameObjectIterable<Orb> neutral;

// -------------------------- OTHER METHODS --------------------------

    public void Update(float dt) {
        UpdateNewtonian(dt);

        CheckGroups(player, neutral);
        CheckGroups(player, enemy);
        CheckGroups(enemy, neutral);
    }

    void UpdateNewtonian(float dt) {
        if (player != null)
            for (Orb pOrb : player)
                pOrb.Physics.Update(dt);
        if (neutral != null)
            for (Orb nOrb : neutral)
                nOrb.Physics.Update(dt);
        if (enemy != null)
            for (Orb eOrb : enemy)
                eOrb.Physics.Update(dt);
    }

    void CheckGroups(GameObjectIterable<Orb> orbs1, GameObjectIterable<Orb> orbs2) {
        if (orbs1 == null || orbs2 == null)
            return;
        for (Orb orb1 : orbs1)
            for (Orb orb2 : orbs2)
                if (Collision.Check(orb1, orb2))
                    ResolveOrbOrbCollision(orb1, orb2);
    }

    void ResolveOrbOrbCollision(Orb orb1, Orb orb2) {
        CollisionEventArgs collisionEventArgs = new CollisionEventArgs();
        GameEvent cEvent1 = new GameEvent(orb1, orb2, collisionEventArgs, 0);
        GameEvent cEvent2 = new GameEvent(orb2, orb1, collisionEventArgs, 0);
        EventManager.AddEvent(cEvent1);
        EventManager.AddEvent(cEvent2);
    }
}
