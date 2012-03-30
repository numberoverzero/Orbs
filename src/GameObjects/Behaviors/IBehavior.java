package GameObjects.Behaviors;

import GameObjects.GameObject;

public interface IBehavior {
// -------------------------- OTHER METHODS --------------------------

    void Apply(GameObject object);

    void Destroy();
    boolean MeetsCriteria(GameObject object);

    void Update(float dt);
}