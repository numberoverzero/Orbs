package GameObjects.Behaviors;

import GameObjects.GameObject;

public interface IBehavior {
    boolean MeetsCriteria(GameObject object);

    void Update(float dt);

    void Apply(GameObject object);

    void Destroy();
}