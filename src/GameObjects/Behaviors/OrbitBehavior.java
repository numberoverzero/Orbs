package GameObjects.Behaviors;

import GameObjects.GameObject;
import GameObjects.Orders.Order;
import Math.Random;
import Math.Vec2;

public class OrbitBehavior implements IBehavior {
// ------------------------------ FIELDS ------------------------------

    static float defOrbitRadius = 60, defOrbitPeriod = 3, defCorrectionPct = 600;
    float orbitRadius, orbitPeriod, orbitElapsed, orbitSign, orbitMag, correctionPct;
    GameObject orbitObject;

// --------------------------- CONSTRUCTORS ---------------------------

    public OrbitBehavior(GameObject orbitObject) {
        super();
        this.orbitObject = orbitObject;
        orbitRadius = defOrbitRadius;
        orbitPeriod = defOrbitPeriod;
        orbitElapsed = 0;
        correctionPct = defCorrectionPct;

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
        Vec2 targetPos = CalculateTargetPos(object);
        Vec2 direction = targetPos.Minus(object.Physics.Position);
        object.Physics.Velocity.Add(direction);
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

    private Vec2 CalculateTargetPos(GameObject object) {
        Vec2 target = new Vec2(orbitObject.Physics.Position);
        if (object.CurrentOrder == Order.Hover || object.CurrentOrder == Order.Seek) {
            Vec2 orbitOffset = Vec2.FromAngle(orbitElapsed * 2 * Math.PI / orbitPeriod);
            orbitOffset.Mul(orbitRadius);
            target.Add(orbitOffset);
        }
        return target;
    }

    private void JitterOrbit(GameObject object) {
        Vec2 objVel = object.Physics.Velocity;
        float rpx = Random.Float(0, 0.07f),
                rpy = Random.Float(0, 0.07f);
        Vec2 jitterOffset = new Vec2(rpx, rpy);
        jitterOffset.LinearMul(objVel);
        objVel.Add(jitterOffset);
    }

    private void UpdateOrder(GameObject object) {
        // Do some magic here to adjust max speed, and set acceleration to the center of the orbit.

        Vec2 tarPos = orbitObject.Physics.Position;
        Vec2 objPos = object.Physics.Position;
        float distance2 = Vec2.Distance2(tarPos, objPos);

        Vec2 objDims = object.Physics.Dimensions;
        float objDim = objDims.X > objDims.Y ? objDims.X : objDims.Y;

        if (!object.HasOrder() || object.CurrentOrder == Order.Hover) {
            float hoverDist = orbitRadius + objDim * (2 + correctionPct / 100);
            if (distance2 > hoverDist * hoverDist)
                object.CurrentOrder = Order.Seek;
        } else if (!object.HasOrder() || object.CurrentOrder == Order.Seek) {
            float seekDist = orbitRadius + 2 * objDim;
            if (distance2 <= seekDist * seekDist) {
                object.CurrentOrder = Order.Hover;
                Vec2 direction = tarPos.Minus(objPos);
                if (direction.Mag2() > 0) {
                    direction.Normalize();
                    float rTheta = (float) (Random.Float() * 2 * Math.PI);
                    direction = direction.Rotate(rTheta);
                    orbitElapsed = (float) (Math.asin(direction.Y) * orbitPeriod / (2 * Math.PI));
                    orbitSign = Random.Sign();
                }
            }
        }
    }
}
