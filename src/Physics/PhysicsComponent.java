package Physics;

import Math.Shapes.Circle;
import Math.Shapes.Rect;
import Math.Vec2;

public class PhysicsComponent {
// ------------------------------ FIELDS ------------------------------

    static final float DEFAULT_ACCEL_DECAY = 0.0f;

    public Vec2 Dimensions, Position, Velocity, Acceleration, DecayAcceleration;
    public float Mass, MaxSpeed;
    public double Rotation;
    public ColliderType ColliderType;

// --------------------------- CONSTRUCTORS ---------------------------

    public PhysicsComponent() {
        Position = Vec2.Zero();
        Velocity = Vec2.Zero();
        Acceleration = new Vec2(DEFAULT_ACCEL_DECAY);
        Dimensions = Vec2.Zero();
        DecayAcceleration = Vec2.Zero();
        Rotation = 0;
        Mass = 1;
        MaxSpeed = Float.MAX_VALUE;
        ColliderType = ColliderType.Rectangle;
    }

    public PhysicsComponent(PhysicsComponent other) {
        Position = new Vec2(other.Position);
        Velocity = new Vec2(other.Velocity);
        Acceleration = new Vec2(other.Acceleration);
        Dimensions = new Vec2(other.Dimensions);
        DecayAcceleration = new Vec2(other.DecayAcceleration);
        Rotation = other.Rotation;
        Mass = other.Mass;
        MaxSpeed = other.MaxSpeed;
        ColliderType = other.ColliderType;
    }

// -------------------------- OTHER METHODS --------------------------

    public void ApplyForce(Vec2 force) {
        Vec2 accelOffset = force.DivOut(Mass);
        Acceleration.Add(accelOffset);
    }

    public void ApplyForce(Vec2 direction, float mag) {
        ApplyForce(direction.MulOut(mag));
    }

    public Rect GetAABB() {
        return GetOBB().MinBoundsOf(Rotation);
    }

    public Circle GetBoundingCircle() {
        return new Circle(GetRadius(), Position);
    }

    public float GetRadius() {
        return Dimensions.X;
    }

    public Rect GetOBB() {
        return Rect.CenteredAt(Position, Dimensions.X, Dimensions.Y);
    }

    public void SetRadius(float radius) {
        Dimensions.X = radius;
    }

    public void Update(float dt) {
        Acceleration.X *= (1 - DecayAcceleration.X);
        Acceleration.Y *= (1 - DecayAcceleration.Y);

        Velocity.X += Acceleration.X * dt;
        Velocity.Y += Acceleration.Y * dt;
        ClampVelocity();

        Position.X += Velocity.X * dt;
        Position.Y += Velocity.Y * dt;
    }

    void ClampVelocity() {
        if (Velocity.Mag2() > MaxSpeed * MaxSpeed) {
            Velocity.Normalize();
            Velocity.Mul(MaxSpeed);
        }
    }
}
