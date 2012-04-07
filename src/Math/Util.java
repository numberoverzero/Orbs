package Math;

public final class Util {
// -------------------------- STATIC METHODS --------------------------

    public static boolean IsZero(float value)
    {
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

    public static float ToRadians(double degrees) {
        return (float) (degrees * Math.PI / 180);
    }

    public static float ToDegrees(double radians) {
        return (float) (radians * 180 / Math.PI);
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

    public static double[] GetRectCorners(Vec2 dimensions, float borderWidth) {
        // Centered at the origin, returns corners A, B, C, D, E
        /*
          B------------------C
          |                  |
          |                  |
          |                  |
          A/E----------------D
         */
        final double d2x = dimensions.X / 2;
        final double d2y = dimensions.Y / 2;
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

    public static void Offset(double[] xypairs, Vec2 position) {
        for (int i = 0; i < xypairs.length; i += 2) {
            xypairs[i] += position.X;
            xypairs[i + 1] += position.Y;
        }
    }
}
