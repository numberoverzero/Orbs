package World;

import GameObjects.GameObjectIterable;
import GameObjects.Orb;
import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IWorld {
// -------------------------- OTHER METHODS --------------------------

    void Draw(SpriteBatch batch, RenderPass pass);
    GameState GetGameState();
    ILevel GetLevel();
    boolean IsPaused();
    
    void LoadLevel(String levelFilePath, String stateFilePath);
    void SaveLevel(String stateFilePath);
    void UnloadLevel();
    void Update(float dt);
}
