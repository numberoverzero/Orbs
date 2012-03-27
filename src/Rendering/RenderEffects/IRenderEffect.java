package Rendering.RenderEffects;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public interface IRenderEffect {
    void LoadContent();
    void UnloadContent();
    void ApplyEffect(FrameBuffer preEffectBuffer, FrameBuffer postEffectBuffer);
}
