package Physics;

import Math.Vec2;
import Math.Rect;
/**
 * User: Joe Laptop
 * Date: 3/28/12
 * Time: 5:02 PM
 */
public class PhysicsComponent {
    public Vec2 Dimensions, Position, Velocity, Acceleration, DecayAcceleration;
    public float Rotation, Mass, MaxSpeed;

    
    public PhysicsComponent()
    {
        Position = Vec2.Zero();
        Velocity = Vec2.Zero();
        Acceleration = Vec2.Zero();
        Dimensions = Vec2.Zero();
        DecayAcceleration = Vec2.Zero();
        Rotation = 0;
        Mass = 1;
        MaxSpeed = Float.MAX_VALUE;
    }
    
    public void Update(float dt)
    {
        Acceleration.X *= (1-DecayAcceleration.X);
        Acceleration.Y *= (1-DecayAcceleration.Y);

        Velocity.X += Acceleration.X * dt;
        Velocity.Y += Acceleration.Y * dt;
        ClampVelocity();

        Position.X += Velocity.X * dt;
        Position.Y += Velocity.Y * dt;
    }

    void ClampVelocity()
    {
        if(Velocity.Mag2() > MaxSpeed * MaxSpeed)
        {
            Velocity.Normalize();
            Velocity.Mul(MaxSpeed);
        }
    }
    
    public Rect GetAABB()
    {
        return Rect.CenteredAt(Position, Dimensions.X, Dimensions.Y);
    }
    
    public Rect GetMinAABB()
    {
        return GetAABB().MinBoundsOf(Rotation);
    }
}
