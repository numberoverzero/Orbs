package Math;

import static java.lang.Math.sqrt;

/**
 * User: Joe Laptop
 * Date: 3/28/12
 * Time: 5:03 PM
 */
public class Vec2 {
    public float X, Y;
    
    public Vec2()
    {
        this(0);
    }
    
    public Vec2(float s)
    {
        this(s,s);
    }

    public Vec2(float X, float Y)
    {
        this.X = X;
        this.Y = Y;
    }

    public Vec2(double X, double Y)
    {
        this.X = (float)X;
        this.Y = (float)Y;
    }
    
    public void Add(float c)
    {
        X += c;
        Y += c;
    }
    
    public void Add(Vec2 other)
    {
        X += other.X;
        Y += other.Y;
    }
    
    public void Sub(Vec2 other)
    {
        X -= other.X;
        Y -= other.Y;
    }
    
    public void Mul(float c)
    {
        X *= c;
        Y *= c;
    }
    
    public void Div(float c)
    {
        X /= c;
        Y /= c;
    }
    
    public float Angle()
    {
        return (float)Math.atan2(Y, X);
    }
    
    public float Mag()
    {
        return (float) sqrt(Mag2());
    }

    public float Mag2()
    {
        return X * X + Y * Y;
    }

    public void Normalize()
    {
        float m = Mag();
        X /= m;
        Y /= m;
    }

    public static void Rotate(Vec2 vec, float theta)
    {
        RotateAbout(vec, Vec2.Zero(), theta);
    }
    
    public Vec2 Rotate(float theta)
    {
        return RotateAbout(Vec2.Zero(), theta);
    }
    
    public Vec2 RotateAbout(Vec2 pivot, float theta)
    {
        double dx = X - pivot.X;
        double dy = Y - pivot.Y;
        double st = Math.sin(theta);
        double ct = Math.cos(theta);

        double x = pivot.X + (ct * dx) - (st * dy);
        double y = pivot.Y + (st * dx) + (ct * dy);
        return new Vec2(x, y);
    }
    
    public static void RotateAbout(Vec2 point, Vec2 pivot, float theta)
    {
        double dx = point.X - pivot.X;
        double dy = point.Y - pivot.Y;
        double st = Math.sin(theta);
        double ct = Math.cos(theta);

        double x = pivot.X + (ct * dx) - (st * dy);
        double y = pivot.Y + (st * dx) + (ct * dy);
        point.X = (float)x;
        point.Y = (float)y;
    }

    public Vec2 Unit()
    {
        float m = Mag();
        return new Vec2(X/m, Y/m);
    }

    // Static methods for (0,0) and (1,1)

    public static Vec2 Zero()
    {
        return new Vec2(0);
    }
    public static Vec2 One()
    {
        return new Vec2(1);
    }

    
}
