package GameObjects;

import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;

public class GameObjectIterable<TGameObj extends GameObject> extends GameObject implements Iterable<TGameObj> {
// ------------------------------ FIELDS ------------------------------

    protected ArrayList<TGameObj> objects = new ArrayList<TGameObj>();

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    public Iterator<TGameObj> iterator() {
        return new ActiveObjIterator();
    }

// -------------------------- OTHER METHODS --------------------------

    public void AddGameObject(TGameObj gObject) {
        gObject.Hostility = this.Hostility;
        objects.add(gObject);
    }

    public void ClearInactiveGameObjects() {
        for (int i = objects.size() - 1; i >= 0; i--) {
            if (!objects.get(i).Active)
                objects.remove(i);
        }
    }

    @Override
    public void Draw(SpriteBatch batch, RenderPass pass) {
        for (TGameObj gObj : this)
            gObj.Draw(batch, pass);
    }

    public boolean HasInactiveGameObjects() {
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

    @Override
    public void Update(float dt) {
        for (TGameObj gObj : this)
            gObj.Update(dt);
    }

    public boolean contains(TGameObj gObject) {
        return objects.contains(gObject);
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
