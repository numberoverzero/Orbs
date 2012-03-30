package Rendering.RenderEffects;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public interface IRenderEffect {
// -------------------------- OTHER METHODS --------------------------

    void ApplyEffect(FrameBuffer preEffectBuffer, FrameBuffer postEffectBuffer);
    void LoadContent();

    void UnloadContent();
}
