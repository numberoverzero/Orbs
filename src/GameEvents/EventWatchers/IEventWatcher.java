package GameEvents.EventWatchers;

import GameEvents.GameEvent;

/**
 * User: Joe Laptop
 * Date: 3/27/12
 * Time: 5:43 PM
 */
public interface IEventWatcher {
    EventWatchTiming GetWatchTiming();

    void InspectEvent(GameEvent event);
}
