package GameEvents;

import GameEvents.EventWatchers.EventWatchTiming;
import GameEvents.EventWatchers.IEventWatcher;

import java.util.ArrayList;

public class GameEventManager {
// ------------------------------ FIELDS ------------------------------

    static GameEventManager singleton;

    boolean isUpdating;
    ArrayList<GameEvent> events;
    ArrayList<GameEvent> queuedEvents;
    ArrayList<GameEvent> completedEvents;
    ArrayList<IEventWatcher> watchers;

// -------------------------- STATIC METHODS --------------------------

    public static GameEventManager GlobalEventManager() {
        if (singleton == null)
            singleton = new GameEventManager();
        return singleton;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private GameEventManager() {
        isUpdating = false;
        events = new ArrayList<GameEvent>();
        queuedEvents = new ArrayList<GameEvent>();
        completedEvents = new ArrayList<GameEvent>();
        watchers = new ArrayList<IEventWatcher>();
    }

// -------------------------- OTHER METHODS --------------------------

    public void AddEvent(GameEvent event) {
        ArrayList<GameEvent> eventList = isUpdating ? queuedEvents : events;
        eventList.add(event);
        CheckWatchers(event, EventWatchTiming.OnCreate);
    }

    void CheckWatchers(GameEvent event, EventWatchTiming timing) {
        EventWatchTiming watcherTiming;
        for (IEventWatcher watcher : watchers) {
            watcherTiming = watcher.GetWatchTiming();
            if ((watcherTiming == timing) || (watcherTiming == EventWatchTiming.Any))
                watcher.InspectEvent(event);
        }
    }

    public void AddWatcher(IEventWatcher watcher) {
        watchers.add(watcher);
    }

    public void RemoveEvent(GameEvent event) {
        events.remove(event);
    }

    public void RemoveWatcher(IEventWatcher watcher) {
        watchers.remove(watcher);
    }

    public void Update(float dt) {
        isUpdating = true;
        for (GameEvent event : events) {
            event.Update(dt);
            if (event.HasFired()) {
                completedEvents.add(event);
                CheckWatchers(event, EventWatchTiming.OnFire);
            }
        }
        isUpdating = false;

        RemoveCompletedEvents();
        if (queuedEvents.size() > 0) {
            AddQueuedEvents();
            //Call update with no change in time, to give added events that are 'instant' a chance to fire
            Update(0);
        }
    }

    void RemoveCompletedEvents() {
        for (GameEvent event : completedEvents)
            events.remove(event);
        completedEvents.clear();
    }

    void AddQueuedEvents() {
        for (GameEvent event : queuedEvents)
            events.add(event);
        queuedEvents.clear();
    }
}
