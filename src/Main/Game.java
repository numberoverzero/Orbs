package Main;

import GameObjects.Behaviors.IBehavior;
import GameObjects.Behaviors.OrbitBehavior;
import GameObjects.Behaviors.PrimitiveSteeringBehavior;
import GameObjects.GameObject;
import GameObjects.GameObjectIterable;
import GameObjects.Hostility;
import GameObjects.Orb;
import Rendering.RenderLayer;
import Rendering.RenderPass;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class Game implements ApplicationListener {
// ------------------------------ FIELDS ------------------------------

    private ShaderProgram shader;
    private Mesh mesh;
    static SpriteBatch batch;
    static Texture texture;
    static int width = 100, height = 50;
    GameObjectIterable<Orb> orbs = new GameObjectIterable<Orb>();
    int nOrbs = 10;

    int centerX, centerY;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ApplicationListener ---------------------

    @Override
    public void create() {
        batch = new SpriteBatch();

        SetupShader();

        MakeOrbs();
    }

    void MakeOrbs()
    {
        centerX = Gdx.graphics.getWidth() / 2;
        centerY = Gdx.graphics.getHeight() / 2;
        Orb orb;
        for (int i = 0; i < nOrbs; i++) {
            orb = new Orb(10, 4, true, true);
            orb.Physics.Position = new Vector2(centerX + 80, centerY);
            orb.Hostility = Hostility.Player;
            orb.Colors.SetColor(Hostility.Player, RenderLayer.Base, new Color(72 / 255f, 61 / 255f, 139 / 255f, 255 / 255f));
            orb.Colors.SetColor(Hostility.Player, RenderLayer.Highlight, new Color(106 / 255f, 90 / 255f, 205 / 255f, 255 / 255f));

            GameObject orbitTarget = new GameObject(1, true, false);
            orbitTarget.Physics.Position = new Vector2(centerX, centerY);
            IBehavior orbitBehavior = new OrbitBehavior(orbitTarget);
            orbitTarget.Physics.Position = new Vector2(centerX, centerY);
            orb.AddBehavior(orbitBehavior);

            IBehavior steering = new PrimitiveSteeringBehavior();
            orb.AddBehavior(steering);

            orbs.AddGameObject(orb);
        }
    }

    void SetupShader()
    {
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("assets/default.vert").readString(),
                Gdx.files.internal("assets/default.frag").readString());
        if(!shader.isCompiled()) {
            Gdx.app.log("Game", shader.getLog());
        }
        batch.setShader(null);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        for (Orb orb : orbs)
            orb.Update(dt);

        if (batch == null)
            create();
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //shader.begin();
        for (Orb orb : orbs)
            orb.Draw(batch, RenderPass.Effects);
        batch.end();
        //shader.end();
        //orb.Physics.Rotation += dt * 2 * Math.PI / 5;
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
        mesh.dispose();
    }
}
