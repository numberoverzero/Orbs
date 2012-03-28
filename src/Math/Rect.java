package Math;

/**
 * User: Joe Laptop
 * Date: 3/28/12
 * Time: 6:10 PM
 */
public class Rect {
    public float X, Y, Width, Height;


    public Rect()
    {
        this(0,0,0,0);
    }

    public Rect(float width, float height)
    {
        this(0,0,width, height);
    }

    public Rect(float x, float y, float width, float height)
    {
        X = x;
        Y = y;
        Width = width;
        Height = height;
    }

    public Vec2 Dimensions()
    {
        return new Vec2(Width, Height);
    }

    public float Left()
    {
        return X;
    }
    public float Right()
    {
        return X + Width;
    }
    public float Top()
    {
        return Y;
    }
    public float Bottom()
    {
        return Y + Height;
    }
    
    
    public boolean Intersects(Rect other)
    {
        return false;
    }
    
    public static Rect CenteredAt(Vec2 center, float width, float height)
    {
        float hw = width / 2;
        float hh = height / 2;
        return new Rect(center.X - hw, center.Y - hh, width, height);
    }
}
