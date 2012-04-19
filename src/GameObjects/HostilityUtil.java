package GameObjects;

/**
 * User: Joe Laptop
 * Date: 4/19/12
 * Time: 6:00 PM
 */
public final class HostilityUtil {
// -------------------------- STATIC METHODS --------------------------

    public static Hostility OppositeHostility(Hostility hostility) {
        Hostility oppositeHostility;
        switch (hostility) {
            case Any:
                oppositeHostility = Hostility.None;
                break;
            case None:
                oppositeHostility = Hostility.Any;
                break;
            case Player:
            case Friend:
                oppositeHostility = Hostility.Enemy;
                break;
            case Enemy:
                oppositeHostility = Hostility.Player;
                break;
            case Neutral:
                oppositeHostility = Hostility.Any;
                break;
            default:
                oppositeHostility = Hostility.None;
        }
        return oppositeHostility;
    }

    public static boolean IsAlly(Hostility hostility, Hostility otherHostility) {
        boolean isAllied;
        switch (hostility) {
            case Any:
                isAllied = false;
            case None:
                isAllied = false;
            case Player:
            case Friend:
                isAllied = otherHostility == Hostility.Player || otherHostility == Hostility.Friend;
                break;
            case Enemy:
                isAllied = otherHostility == Hostility.Enemy;
                break;
            case Neutral:
                isAllied = false;
            default:
                isAllied = false;
                break;
        }
        return isAllied;
    }

    public static boolean IsEnemy(Hostility hostility, Hostility otherHostility) {
        boolean isEnemy;
        switch (hostility) {
            case Any:
                isEnemy = false;
            case None:
                isEnemy = false;
            case Player:
            case Friend:
                isEnemy = otherHostility == Hostility.Enemy;
                break;
            case Enemy:
                isEnemy = otherHostility == Hostility.Player || otherHostility == Hostility.Friend;
                break;
            case Neutral:
                isEnemy = false;
            default:
                isEnemy = false;
                break;
        }
        return isEnemy;
    }
}
