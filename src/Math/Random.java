package Math;


import com.badlogic.gdx.math.Vector2;

import static Math.Util.Lerp;

public final class Random {
// ------------------------------ FIELDS ------------------------------

    static final long seed = 123456789;
    static java.util.Random random = new java.util.Random(seed);

// -------------------------- STATIC METHODS --------------------------

    public static Vector2 Vec2(float mag) {
        float rx = Float(-1, 1),
                ry = Float(-1, 1);
        return new Vector2(rx, ry).nor().mul(mag);
    }

    public static float Float(float min, float max) {
        return Lerp(Float(), min, max);
    }

    public static int Sign() {
        return Random.Float() > 0.5 ? 1 : -1;
    }

    public static float Float() {
        return random.nextFloat();
    }
}
