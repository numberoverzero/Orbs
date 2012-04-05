package World;

import GameObjects.GameObjectIterable;
import GameObjects.Orb;
import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IWorld {
    boolean IsPaused();
    GameState GetGameState();
    ILevel GetLevel();
    void Update(float dt);
    void Draw(SpriteBatch batch, RenderPass pass);
    
    void LoadLevel(String levelFilePath, String stateFilePath);
    void SaveLevel(String stateFilePath);
    void UnloadLevel();
}
