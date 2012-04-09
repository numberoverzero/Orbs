package Math;

import static java.lang.Math.sqrt;

/**
 * User: Joe Laptop
 * Date: 3/28/12
 * Time: 5:03 PM
 */
public class Vec2 {
// ------------------------------ FIELDS ------------------------------

    public float X, Y;

// -------------------------- STATIC METHODS --------------------------

    public static Vec2 FromAngle(double theta) {
        return new Vec2(Math.cos(theta), Math.sin(theta));
    }

    public static void Rotate(Vec2 point, Vec2 pivot, double theta) {
        double dx = point.X - pivot.X;
        double dy = point.Y - pivot.Y;
        double st = Math.sin(theta);
        double ct = Math.cos(theta);

        double x = pivot.X + (ct * dx) - (st * dy);
        double y = pivot.Y + (st * dx) + (ct * dy);
        point.X = (float) x;
        point.Y = (float) y;
    }

    public static float Distance2(Vec2 vec1, Vec2 vec2) {
        return (vec2.X - vec1.X) * (vec2.X - vec1.X) + (vec2.Y - vec1.Y) * (vec2.Y - vec1.Y);
    }

    public static Vec2 One() {
        return new Vec2(1);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Vec2() {
        this(0);
    }

    public Vec2(double scalar) {
        this(scalar, scalar);
    }

    public Vec2(Vec2 other) {
        X = other.X;
        Y = other.Y;
    }

    public Vec2(double X, double Y) {
        this.X = (float) X;
        this.Y = (float) Y;
    }

// -------------------------- OTHER METHODS --------------------------

    public void Add(double c) {
        X += c;
        Y += c;
    }

    public void Add(Vec2 other) {
        X += other.X;
        Y += other.Y;
    }

    public Vec2 AddOut(double c) {
        return new Vec2(X + c, Y + c);
    }

    public Vec2 AddOut(Vec2 other) {
        return new Vec2(X + other.X, Y + other.Y);
    }

    public float Angle() {
        return (float) Math.atan2(Y, X);
    }

    public Vec2 DivOut(float c) {
        return new Vec2(X / c, Y / c);
    }

    public boolean IsZero() {
        return Util.IsZero(Mag2());
    }

    public float Mag2() {
        return X * X + Y * Y;
    }

    public void LinearMul(Vec2 other) {
        X *= other.X;
        Y *= other.Y;
    }

    public void Mul(float c) {
        X *= c;
        Y *= c;
    }

    public Vec2 MulOut(float c) {
        return new Vec2(X * c, Y * c);
    }

    public Vec2 NegOut() {
        return new Vec2(-X, -Y);
    }

    public void Negate() {
        X *= -1;
        Y *= -1;
    }

    public void Normalize() {
        float m = Mag();
        X /= m;
        Y /= m;
    }

    public float Mag() {
        return (float) sqrt(Mag2());
    }

    public void Rotate(double theta) {
        Rotate(this, Vec2.Zero(), theta);
    }

    public static Vec2 Zero() {
        return new Vec2(0);
    }

    public void Rotate(Vec2 pivot, double theta) {
        double dx = X - pivot.X;
        double dy = Y - pivot.Y;
        double st = Math.sin(theta);
        double ct = Math.cos(theta);

        double x = pivot.X + (ct * dx) - (st * dy);
        double y = pivot.Y + (st * dx) + (ct * dy);
        X = (float) x;
        Y = (float) y;
    }

    public Vec2 RotateOut(double theta) {
        return RotateOut(Vec2.Zero(), theta);
    }

    public Vec2 RotateOut(Vec2 pivot, double theta) {
        double dx = X - pivot.X;
        double dy = Y - pivot.Y;
        double st = Math.sin(theta);
        double ct = Math.cos(theta);

        double x = pivot.X + (ct * dx) - (st * dy);
        double y = pivot.Y + (st * dx) + (ct * dy);
        return new Vec2(x, y);
    }

    public void Sub(Vec2 other) {
        X -= other.X;
        Y -= other.Y;
    }

    public Vec2 SubOut(Vec2 other) {
        return new Vec2(X - other.X, Y - other.Y);
    }

    public void Translate(Vec2 offset) {
        X += offset.X;
        Y += offset.Y;
    }

    public Vec2 Unit() {
        float m = Mag();
        return new Vec2(X / m, Y / m);
    }
}
