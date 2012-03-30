package Math;

public final class Util {
    public static float Lerp(float val, float min, float max) {
        return min + val * (max - min);
    }

    public static float Clamp(float val, float min, float max) {
        val = val > min ? val : min;
        val = val < max ? val : max;
        return val;
    }
}
