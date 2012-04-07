package UI;

import Math.Util;
import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Screen
{
    /// <summary>
    /// Normally when one screen is brought up over the top of another,
    /// the first screen will transition off to make room for the new
    /// one. This property indicates whether the screen is only a small
    /// popup, in which case screens underneath it do not need to bother
    /// transitioning off.
    /// </summary>
    public boolean IsPopup = false;

    /// <summary>
    /// Indicates how long the screen takes to
    /// transition on when it is activated.
    /// </summary>
    public float TransitionOnTime = 0;

    /// <summary>
    /// Indicates how long the screen takes to
    /// transition off when it is deactivated.
    /// </summary>
    public float TransitionOffTime = 0;

    /// <summary>
    /// Gets the current position of the screen transition, ranging
    /// from zero (fully active, no transition) to one (transitioned
    /// fully off to nothing).
    /// </summary>
    public float TransitionPosition = 1;

    /// <summary>
    /// Gets the current alpha of the screen transition, ranging
    /// from 255 (fully active, no transition) to 0 (transitioned
    /// fully off to nothing).
    /// </summary>
    public int TransitionAlpha()
    {
        return 255 - (int)(TransitionPosition * 255);
    }

    /// <summary>
    /// Gets the current screen transition state.
    /// </summary>
    public ScreenState ScreenState = UI.ScreenState.TransitionOn;

    /// <summary>
    /// There are two possible reasons why a screen might be transitioning
    /// off. It could be temporarily going away to make room for another
    /// screen that is on top of it, or it could be going away for good.
    /// This property indicates whether the screen is exiting for real:
    /// if set, the screen will automatically remove itself as soon as the
    /// transition finishes.
    /// </summary>
    public boolean IsExiting = false;

    /// <summary>
    /// Checks whether this screen is active and can respond to user input.
    /// </summary>
    public boolean IsActive()
    {
        return !otherScreenHasFocus &&
                (ScreenState == ScreenState.TransitionOn ||
                        ScreenState == ScreenState.Active);
    }

    boolean otherScreenHasFocus;


    /// <summary>
    /// Gets the manager that this screen belongs to.
    /// </summary>
    public ScreenManager ScreenManager;

    /// <summary>
    /// Load graphics content for the screen.
    /// </summary>
    public void LoadContent() { }

    /// <summary>
    /// Unload content for the screen.
    /// </summary>
    public void UnloadContent() { }

    /// <summary>
    /// Allows the screen to run logic, such as updating the transition position.
    /// Unlike HandleInput, this method is called regardless of whether the screen
    /// is active, hidden, or in the middle of a transition.
    /// </summary>
    public void Update(float dt, boolean otherScreenHasFocus,
                               boolean coveredByOtherScreen)
    {
    this.otherScreenHasFocus = otherScreenHasFocus;

    if (IsExiting)
    {
        // If the screen is going away to die, it should transition off.
        ScreenState = ScreenState.TransitionOff;

        if (!UpdateTransition(dt, TransitionOffTime, 1))
        {
            // When the transition finishes, remove the screen.
            ScreenManager.RemoveScreen(this);

            IsExiting = false;
        }
    }
    else if (coveredByOtherScreen)
    {
        // If the screen is covered by another, it should transition off.
        if (UpdateTransition(dt, TransitionOffTime, 1))
        {
            // Still busy transitioning.
            ScreenState = ScreenState.TransitionOff;
        }
        else
        {
            // Transition finished!
            ScreenState = ScreenState.Hidden;
        }
    }
    else
    {
        // Otherwise the screen should transition on and become active.
        if (UpdateTransition(dt, TransitionOnTime, -1))
        {
            // Still busy transitioning.
            ScreenState = ScreenState.TransitionOn;
        }
        else
        {
            // Transition finished!
            ScreenState = ScreenState.Active;
        }
    }
}

    /// <summary>
    /// Helper for updating the screen transition position.
    /// </summary>
    boolean UpdateTransition(float dt, float time, int direction)
    {
        // How much should we move by?
        float transitionDelta;

        if (Math.Util.IsZero(time))
            transitionDelta = 1;
        else
            transitionDelta = dt / time;

        // Update the transition position.
        TransitionPosition += transitionDelta * direction;

        // Did we reach the end of the transition?
        if ((TransitionPosition <= 0) || (TransitionPosition >= 1))
        {
            TransitionPosition = Util.Clamp(TransitionPosition, 0, 1);
            return false;
        }

        // Otherwise we are still busy transitioning.
        return true;
    }

    /// <summary>
    /// Allows the screen to handle user input. Unlike Update, this method
    /// is only called when the screen is active, and not when some other
    /// screen has taken the focus.
    /// </summary>
    public void HandleInput(InputState input) { }

    /// <summary>
    /// Screen-specific update to gamer rich presence.
    /// </summary>
    public void UpdatePresence() { }

    /// <summary>
    /// This is called when the screen should draw itself.
    /// </summary>
    public void Draw(SpriteBatch batch, RenderPass pass) {}

    /// <summary>
    /// Tells the screen to go away. Unlike ScreenManager.RemoveScreen, which
    /// instantly kills the screen, this method respects the transition timings
    /// and will give the screen a chance to gradually transition off.
    /// </summary>
    public void ExitScreen()
    {
        if (Util.IsZero(TransitionOffTime)){
            // If the screen has a zero transition time, remove it immediately.
            ScreenManager.RemoveScreen(this);
        }
        else{
            // Otherwise flag that it should transition off and then exit.
            IsExiting = true;
        }
    }
}
