package Math;


import static Math.Util.Lerp;

public final class Random {
    static final long seed = 123456789;
    static java.util.Random random = new java.util.Random(seed);

    public static float Float() {
        return random.nextFloat();
    }

    public static float Float(float min, float max) {
        return Lerp(Float(), min, max);
    }

    public static Vec2 Vec2(float mag) {
        float rx = Float(-1, 1),
                ry = Float(-1, 1);
        return new Vec2(rx, ry).Unit().Times(mag);
    }

    public static int Sign() {
        return Random.Float() > 0.5 ? 1 : -1;
    }
}
