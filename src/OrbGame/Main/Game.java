package OrbGame.Main;

import GameEvents.GameEventManager;
import GameObjects.Behaviors.IBehavior;
import GameObjects.Behaviors.OrbitBehavior;
import GameObjects.Behaviors.PrimitiveSteeringBehavior;
import GameObjects.GameObject;
import GameObjects.GameObjectIterable;
import GameObjects.Hostility;
import OrbGame.Orb;
import OrbGame.Physics.PhysicsSystem;
import Rendering.RenderLayer;
import Rendering.RenderPass;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class Game implements ApplicationListener {
// ------------------------------ FIELDS ------------------------------

    static SpriteBatch batch;
    static Texture texture;
    static int width = 100, height = 50;
    GameObjectIterable<Orb> playerOrbs = new GameObjectIterable<Orb>();
    GameObjectIterable<Orb> enemyOrbs = new GameObjectIterable<Orb>();
    PhysicsSystem physicsSystem = new PhysicsSystem();
    int nOrbs = 100;
    float orbSize = 10;

    int centerX, centerY;
    GameObject playerCenter = new GameObject(1);
    private ShaderProgram shader;

// --------------------------- CONSTRUCTORS ---------------------------

    public Game() {
        playerOrbs.Hostility = Hostility.Player;
        enemyOrbs.Hostility = Hostility.Enemy;
        physicsSystem.player = playerOrbs;
        physicsSystem.enemy = enemyOrbs;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ApplicationListener ---------------------

    @Override
    public void create() {
        batch = new SpriteBatch();
        SetupShader();
        MakeOrbs(Hostility.Player);
        MakeOrbs(Hostility.Enemy);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        playerOrbs.Update(dt);
        enemyOrbs.Update(dt);
        physicsSystem.Update(dt);

        GameEventManager.GlobalEventManager().Update(dt);

        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        playerOrbs.Draw(batch, RenderPass.Effects);
        enemyOrbs.Draw(batch, RenderPass.Effects);
        batch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        shader.dispose();
    }

// -------------------------- OTHER METHODS --------------------------

    void MakeOrbs(Hostility hostility) {
        centerX = Gdx.graphics.getWidth() / 2;
        centerY = Gdx.graphics.getHeight() / 2;
        playerCenter.Physics.Position = new Vector2(centerX, centerY);

        GameObjectIterable<Orb> orbs = hostility == Hostility.Player ? playerOrbs : enemyOrbs;
        Orb orb;
        for (int i = 0; i < nOrbs; i++) {
            orb = new Orb(orbSize, 4, true, true);
            orb.Health = 5000;
            orb.Physics.Position = new Vector2(centerX, centerY);
            orb.Physics.Dimensions = new Vector2(orbSize, orbSize);
            orb.Colors.SetColor(Hostility.Player, RenderLayer.Base, new Color(72 / 255f, 61 / 255f, 139 / 255f, 255 / 255f));
            orb.Colors.SetColor(Hostility.Player, RenderLayer.Highlight, new Color(106 / 255f, 90 / 255f, 205 / 255f, 255 / 255f));
            orb.Colors.SetColor(Hostility.Enemy, RenderLayer.Base, new Color(255 / 255f, 0 / 255f, 0 / 255f, 255 / 255f));
            orb.Colors.SetColor(Hostility.Enemy, RenderLayer.Highlight, new Color(189 / 255f, 50 / 255f, 50 / 255f, 255 / 255f));

            IBehavior orbitBehavior = new OrbitBehavior(playerCenter);
            orb.AddBehavior(orbitBehavior);

            IBehavior steering = new PrimitiveSteeringBehavior();
            orb.AddBehavior(steering);

            orbs.AddGameObject(orb);
        }
    }

    void SetupShader() {
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("assets/default.vert").readString(),
                Gdx.files.internal("assets/default.frag").readString());
        if (!shader.isCompiled()) {
            Gdx.app.log("Game", shader.getLog());
        }
        batch.setShader(shader);
    }
}
