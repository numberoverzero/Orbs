package Rendering;

import Rendering.RenderEffects.IRenderEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import java.util.ArrayList;

/**
 * User: Joe Laptop
 * Date: 3/27/12
 * Time: 10:29 AM
 */
public class RenderPipeline {
// ------------------------------ FIELDS ------------------------------

    public static final RenderPass[] Passes = {RenderPass.Background, RenderPass.PreEffect,
            RenderPass.Effects, RenderPass.PostEffect,
            RenderPass.UI, RenderPass.Debug};
    ArrayList<IRenderEffect> effects;
    FrameBuffer buffer;

// --------------------------- CONSTRUCTORS ---------------------------

    public RenderPipeline() {
        effects = new ArrayList<IRenderEffect>();
    }

// -------------------------- OTHER METHODS --------------------------

    void ApplyEffects() {
        for (IRenderEffect effect : effects) {
            SpriteBatch batch = new SpriteBatch();
            effect.ApplyEffect(buffer, buffer);
        }
    }

    public void LoadContent() {
        for (IRenderEffect effect : effects) {
            effect.LoadContent();
        }
    }
}
