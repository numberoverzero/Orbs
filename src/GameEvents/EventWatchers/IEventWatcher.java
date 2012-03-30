package GameEvents.EventWatchers;

import GameEvents.GameEvent;

/**
 * User: Joe Laptop
 * Date: 3/27/12
 * Time: 5:43 PM
 */
public interface IEventWatcher {
// -------------------------- OTHER METHODS --------------------------

    EventWatchTiming GetWatchTiming();

    void InspectEvent(GameEvent event);
}
