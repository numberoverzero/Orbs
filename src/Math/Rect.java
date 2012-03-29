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

    public Vec2 Center()
    {
        return new Vec2(Left() + Width / 2, Bottom() + Height / 2);
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
        return Y + Height;
    }
    public float Bottom()
    {
        return Y;
    }
    
    
    public boolean IsIntersecting(Rect other)
    {
        return AreIntersecting(this, other);
    }
    
    public static boolean AreIntersecting(Rect rect1, Rect rect2)
    {
        return ((rect1.Right()  >= rect2.Left()   ) &&
                (rect1.Left()   <= rect2.Right()  ) &&
                (rect1.Top()    >= rect2.Bottom() ) &&
                (rect1.Bottom() <= rect2.Top()    ));
    }
    
    public static Rect CenteredAt(Vec2 center, float width, float height)
    {
        float hw = width / 2;
        float hh = height / 2;
        return new Rect(center.X - hw, center.Y - hh, width, height);
    }
    
    public Rect MinBoundsOf(float rot)
    {
        return MinBoundsOf(this, rot);
    }
    public static Rect MinBoundsOf(Vec2 center, float width, float height, float rot)
    {
        Rect rect = Rect.CenteredAt(center, width, height);
        return MinBoundsOf(rect, rot);
    }
    
    public static Rect MinBoundsOf(Rect rect, float rot)
    {
        Vec2 pivot = rect.Center();
        Vec2[] corners = rect.Corners();
        float minX=pivot.X, maxX=pivot.X, minY=pivot.Y, maxY=pivot.Y;
        for(Vec2 corner : corners)
        {
            Vec2.RotateAbout(corner, pivot, rot);
            minX = minX < corner.X ? minX : corner.X;
            maxX = maxX > corner.X ? maxX : corner.X;
            minY = minY < corner.Y ? minY : corner.Y;
            maxY = maxY > corner.Y ? maxY : corner.Y;
        }
        return new Rect(minX, minY, maxX-minX, maxY-minY);
    }
    
    public Vec2[] Corners()
    {
        Vec2[] corners = new Vec2[4];
        corners[0] = new Vec2(Left(), Bottom());
        corners[1] = new Vec2(Right(), Bottom());
        corners[2] = new Vec2(Right(), Top());
        corners[3] = new Vec2(Left(), Top());
        return corners;
    }
}
