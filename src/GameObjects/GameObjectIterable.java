package GameObjects;

import java.util.ArrayList;
import java.util.Iterator;

public class GameObjectIterable<TGameObj extends GameObject> implements Iterable<TGameObj> {
// ------------------------------ FIELDS ------------------------------

    protected ArrayList<TGameObj> objects;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    public Iterator<TGameObj> iterator() {
        return new ActiveObjIterator();
    }

// -------------------------- OTHER METHODS --------------------------

    public void AddGameObject(TGameObj gObject) {
        objects.add(gObject);
    }

    public void ClearInactiveGameObjects() {
        for (int i = objects.size() - 1; i >= 0; i--) {
            if (!objects.get(i).Active)
                objects.remove(i);
        }
    }

    public boolean HasInactiveGameObject() {
        for (TGameObj gObj : objects) {
            if (!gObj.Active) {
                return true;
            }
        }
        return false;
    }

    public TGameObj NextInactiveGameObject() {
        for (TGameObj gObj : objects) {
            if (!gObj.Active) {
                return gObj;
            }
        }
        return null;
    }

    public void RemoveGameObject(TGameObj gObject) {
        objects.remove(gObject);
    }

// -------------------------- INNER CLASSES --------------------------

    protected class ActiveObjIterator implements Iterator<TGameObj> {
        protected TGameObj _nextObj;
        protected int currentIndex, lastIndex;

        public ActiveObjIterator() {
            currentIndex = 0;
            lastIndex = objects.size();
        }

        public boolean hasNext() {
            TGameObj temp;
            while (currentIndex < lastIndex) {
                temp = objects.get(currentIndex);
                currentIndex++;
                if (temp != null && temp.Active) {
                    _nextObj = temp;
                    return true;
                }
            }
            return false;
        }

        public TGameObj next() {
            return _nextObj;
        }

        public void remove() {

        }
    }
}
