package GameObjects.Behaviors;

import GameObjects.GameObject;
import GameObjects.Orders.Order;
import Math.Random;
import Math.Util;
import com.badlogic.gdx.math.Vector2;

public class OrbitBehavior implements IBehavior {
// ------------------------------ FIELDS ------------------------------

    static float defOrbitRadius = 60, defOrbitPeriod = 3, defCorrectionPct = 600, defJitterPct = 0.35f;
    float orbitRadius, orbitPeriod, orbitElapsed, orbitSign, orbitMag,
            correctionPct, jitterPct;
    GameObject orbitObject;

// --------------------------- CONSTRUCTORS ---------------------------

    public OrbitBehavior(GameObject orbitObject) {
        super();
        this.orbitObject = orbitObject;
        orbitRadius = defOrbitRadius;
        orbitPeriod = defOrbitPeriod;
        orbitElapsed = 0;
        correctionPct = defCorrectionPct;
        jitterPct = defJitterPct;
        orbitSign = Random.Sign();
        orbitMag = Random.Float(0.8f, 1);
    }

    public OrbitBehavior(GameObject orbitObject, float orbitRadius, float orbitPeriod) {
        this(orbitObject);
        this.orbitRadius = orbitRadius;
        this.orbitPeriod = orbitPeriod;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface IBehavior ---------------------

    @Override
    public void Apply(GameObject object) {
        UpdateOrder(object);

        object.Physics.MaxSpeed = (float) (2 * Math.PI * orbitRadius / orbitPeriod);
        Vector2 targetPos = CalculateTargetPos(object);
        Vector2 direction = targetPos.sub(object.Physics.Position);
        object.Physics.Velocity.add(direction);
        JitterOrbit(object);
    }

    @Override
    public void Destroy() {
        orbitObject = null;
    }

    @Override
    public boolean MeetsCriteria(GameObject object) {
        if (object == null || !object.Active)
            return false;
        return (!object.HasOrder() ||
                (object.CurrentOrder == Order.Seek) ||
                (object.CurrentOrder == Order.Hover));
    }

    @Override
    public void Update(float dt) {
        orbitElapsed += orbitSign * orbitMag * dt;
    }

// -------------------------- OTHER METHODS --------------------------

    private Vector2 CalculateTargetPos(GameObject object) {
        Vector2 target = new Vector2(orbitObject.Physics.Position);
        if (object.CurrentOrder == Order.Hover || object.CurrentOrder == Order.Seek) {
            Vector2 orbitOffset = Util.FromAngle(orbitElapsed * 2 * Math.PI / orbitPeriod);
            orbitOffset.mul(orbitRadius);
            target.add(orbitOffset);
        }
        return target;
    }

    public void ForceOrbitSign(float sign) {
        orbitSign = sign > 0 ? 1 : -1;
    }

    private void JitterOrbit(GameObject object) {
        Vector2 objVel = object.Physics.Velocity;
        float rpx = Random.Float(0, jitterPct),
                rpy = Random.Float(0, jitterPct);
        Vector2 jitterOffset = new Vector2(rpx, rpy);
        jitterOffset.x *= objVel.x;
        jitterOffset.y *= objVel.y;
        objVel.add(jitterOffset);
    }

    public void SetJitterUpperLimit(float jitterUpperPct) {
        jitterPct = Util.Clamp(jitterUpperPct, -1, 1);
    }

    private void UpdateOrder(GameObject object) {
        // Do some magic here to adjust max speed, and set acceleration to the center of the orbit.

        Vector2 tarPos = orbitObject.Physics.Position;
        Vector2 objPos = object.Physics.Position;
        float distance2 = tarPos.dst2(objPos);

        Vector2 objDims = object.Physics.Dimensions;
        float objDim = objDims.x > objDims.y ? objDims.x : objDims.y;

        if (!object.HasOrder() || object.CurrentOrder == Order.Hover) {
            float hoverDist = orbitRadius + objDim * (2 + correctionPct / 100);
            if (distance2 > hoverDist * hoverDist)
                object.CurrentOrder = Order.Seek;
        } else if (!object.HasOrder() || object.CurrentOrder == Order.Seek) {
            float seekDist = orbitRadius + 2 * objDim;
            if (distance2 <= seekDist * seekDist) {
                object.CurrentOrder = Order.Hover;
                Vector2 direction = tarPos.cpy().sub(objPos);
                if (direction.len2() > 0) {
                    direction.nor();
                    float rTheta = (float) (Random.Float() * 2 * Math.PI);
                    Util.RotateInPlace(direction, rTheta);
                    orbitElapsed = (float) (Math.asin(direction.y) * orbitPeriod / (2 * Math.PI));
                    orbitSign = Random.Sign();
                }
            }
        }
    }
}
