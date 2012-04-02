package Main;

import GameObjects.Hostility;
import GameObjects.Orb;
import Math.Vec2;
import Rendering.RenderLayer;
import Rendering.RenderPass;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {
// ------------------------------ FIELDS ------------------------------

    static SpriteBatch batch;
    static Texture texture;
    static int width = 100, height = 50;
    Orb orb;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ApplicationListener ---------------------

    @Override
    public void create() {
        batch = new SpriteBatch();
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(Color.RED);
        p.fillRectangle(0, 0, 1, 1);
        texture = new Texture(p);
        p.dispose();
        orb = new Orb(80, 4, true, true);
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;
        orb.Physics.Position = new Vec2(centerX, centerY);
        orb.Hostility = Hostility.Player;
        orb.Colors.SetColor(Hostility.Player, RenderLayer.Base, new Color(72 / 255f, 61 / 255f, 139 / 255f, 255 / 255f));
        orb.Colors.SetColor(Hostility.Player, RenderLayer.Highlight, new Color(106 / 255f, 90 / 255f, 205 / 255f, 255 / 255f));
        //defaultScheme[Hostility.Player, LayerType.Base] = Color.DarkSlateBlue;
        //defaultScheme[Hostility.Player, LayerType.Highlight] = Color.SlateBlue;

        //texture = new Texture(Gdx.files.internal("assets/badlogic.jpg"));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        double r = Math.PI / 2;
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;

        if (batch == null)
            create();
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        orb.Draw(batch, RenderPass.Effects);
        batch.end();
        orb.Physics.Rotation += dt * 2 * Math.PI / 5;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
