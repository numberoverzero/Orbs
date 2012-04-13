package Math;

import com.badlogic.gdx.math.Vector2;

public final class Util {
// -------------------------- STATIC METHODS --------------------------

    public static boolean IsZero(float value) {
        return Math.abs(value) <= 0.00000001;
    }

    public static float Lerp(float val, float min, float max) {
        return min + val * (max - min);
    }

    public static float Clamp(float val, float min, float max) {
        val = val > min ? val : min;
        val = val < max ? val : max;
        return val;
    }

    public static float ToDegrees(double radians) {
        return (float) (radians * 180 / Math.PI);
    }

    public static double Angle(Vector2 direction) {
        return ToRadians(direction.angle());
    }

    public static float ToRadians(double degrees) {
        return (float) (degrees * Math.PI / 180);
    }

    public static double Angle(double y, double x) {
        return Math.atan2(y, x);
    }

    public static float Distance(double dx, double dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static float DistanceSquared(double dx, double dy) {
        return (float) (dx * dx + dy * dy);
    }

    public static double[] GetRectCorners(Vector2 dimensions, float borderWidth) {
        // Centered at the origin, returns corners A, B, C, D, E
        /*
          B------------------C
          |                  |
          |                  |
          |                  |
          A/E----------------D
         */
        final double d2x = dimensions.x / 2;
        final double d2y = dimensions.y / 2;
        return new double[]{
                -d2x + borderWidth, -d2y + borderWidth,
                -d2x + borderWidth, d2y - borderWidth,
                d2x - borderWidth, d2y - borderWidth,
                d2x - borderWidth, -d2y + borderWidth,
                -d2x + borderWidth, -d2y + borderWidth};
    }

    public static void Rotate(double[] xypairs, double theta) {
        final double st = Math.sin(theta);
        final double ct = Math.cos(theta);
        double tempx, tempy;
        for (int i = 0; i < xypairs.length; i += 2) {
            tempx = (ct * xypairs[i]) - (st * xypairs[i + 1]);
            tempy = (st * xypairs[i]) + (ct * xypairs[i + 1]);
            xypairs[i] = tempx;
            xypairs[i + 1] = tempy;
        }
    }

    public static Vector2 Rotate(Vector2 point, double theta) {
        return Rotate(point, new Vector2(), theta);
    }

    public static Vector2 Rotate(Vector2 point, Vector2 origin, double theta) {
        Vector2 outVec = new Vector2(origin);
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        outVec.x = cost * (point.x - origin.x) - sint * (point.y - origin.y);
        outVec.y = sint * (point.x - origin.x) + cost * (point.y - origin.y);
        return outVec;
    }

    public static Vector2 FromAngle(double theta) {
        return new Vector2((float) Math.cos(theta),
                (float) Math.sin(theta));
    }

    public static void RotateInPlace(Vector2 point, double theta) {
        point.rotate((float) theta);
    }

    public static void RotateInPlace(Vector2 point, Vector2 origin, double theta) {
        final float cost = (float) Math.cos(theta);
        final float sint = (float) Math.sin(theta);
        final float outx = cost * (point.x - origin.x) - sint * (point.y - origin.y);
        final float outy = sint * (point.x - origin.x) + cost * (point.y - origin.y);
        point.x = outx;
        point.y = outy;
    }

    public static void Offset(double[] xypairs, Vector2 position) {
        for (int i = 0; i < xypairs.length; i += 2) {
            xypairs[i] += position.x;
            xypairs[i + 1] += position.y;
        }
    }
}
