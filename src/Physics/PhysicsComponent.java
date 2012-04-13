package Physics;

import Math.Shapes.Circle;
import Math.Shapes.Rect;
import com.badlogic.gdx.math.Vector2;

public class PhysicsComponent {
// ------------------------------ FIELDS ------------------------------

    static final float DEFAULT_ACCEL_DECAY = 0.0f;

    public Vector2 Dimensions, Position, Velocity, Acceleration, DecayAcceleration;
    public float Mass, MaxSpeed;
    public double Rotation;
    public ColliderType ColliderType;

// --------------------------- CONSTRUCTORS ---------------------------

    public PhysicsComponent() {
        Position = new Vector2();
        Velocity = new Vector2();
        Acceleration = new Vector2(DEFAULT_ACCEL_DECAY, DEFAULT_ACCEL_DECAY);
        Dimensions = new Vector2();
        DecayAcceleration = new Vector2();
        Rotation = 0;
        Mass = 1;
        MaxSpeed = Float.MAX_VALUE;
        ColliderType = ColliderType.Rectangle;
    }

    public PhysicsComponent(PhysicsComponent other) {
        Position = new Vector2(other.Position);
        Velocity = new Vector2(other.Velocity);
        Acceleration = new Vector2(other.Acceleration);
        Dimensions = new Vector2(other.Dimensions);
        DecayAcceleration = new Vector2(other.DecayAcceleration);
        Rotation = other.Rotation;
        Mass = other.Mass;
        MaxSpeed = other.MaxSpeed;
        ColliderType = other.ColliderType;
    }

// -------------------------- OTHER METHODS --------------------------

    public void ApplyForce(Vector2 force) {
        Vector2 accelOffset = force.cpy().mul(1/Mass);
        Acceleration.add(accelOffset);
    }

    public void ApplyForce(Vector2 direction, float mag) {
        ApplyForce(direction.cpy().mul(mag));
    }

    public Rect GetAABB() {
        return GetOBB().MinBoundsOf(Rotation);
    }

    public Circle GetBoundingCircle() {
        return new Circle(GetRadius(), Position);
    }

    public float GetRadius() {
        return Dimensions.x;
    }

    public Rect GetOBB() {
        return Rect.CenteredAt(Position, Dimensions.x, Dimensions.y);
    }

    public void SetRadius(float radius) {
        Dimensions.x = radius;
    }

    public void Update(float dt) {
        Acceleration.x *= (1 - DecayAcceleration.x);
        Acceleration.y *= (1 - DecayAcceleration.y);

        Velocity.x += Acceleration.x * dt;
        Velocity.y += Acceleration.y * dt;
        ClampVelocity();

        Position.x += Velocity.x * dt;
        Position.y += Velocity.y * dt;
    }

    void ClampVelocity() {
        if (Velocity.len2() > MaxSpeed * MaxSpeed) {
            Velocity.nor();
            Velocity.mul(MaxSpeed);
        }
    }
}
