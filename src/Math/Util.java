package Math;

public final class Util {
// -------------------------- STATIC METHODS --------------------------

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
}
