package Main;

import Math.Vec2;
import Rendering.Shapes.Rectangle;
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
        //texture = new Texture(Gdx.files.internal("assets/badlogic.jpg"));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        double r = Math.PI / 2;
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;

        if (batch == null)
            create();
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        Rectangle.DrawCentered(batch, new Vec2(centerX, centerY), new Vec2(width, height), 0, new Color(1, 0, 0, 1));
        Rectangle.DrawOutline(batch, new Vec2(centerX, centerY), new Vec2(width / 2, height / 2), Math.PI / 2, new Color(0, 1, 1, 1), 2);
        Rectangle.Draw(batch, new Vec2(centerX, centerY), Vec2.One(), new Color(1, 1, 0, 1));
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
    }
}
