package ScreenManagement;

import Math.Vec2;
import Math.Util;
import Rendering.RenderPass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Screen {
// ------------------------------ FIELDS ------------------------------

    // Normally when one screen is brought up over the top of another,
    // the first screen will transition off to make room for the new
    // one. This property indicates whether the screen is only a small
    // popup, in which case screens underneath it do not need to bother
    // transitioning off.
    public boolean IsPopup = false;

    public float TransitionOnTime = 0;

    public float TransitionOffTime = 0;

    public Vec2 bottomLeftPosition = Vec2.Zero();
    
    public Vec2 GetBottomLeftPosition()
    {
        return bottomLeftPosition;
    }
    public ScreenManager ScreenManager;

    boolean otherScreenHasFocus;

    // Zero (fully active, no transition) to
    // One (transitioned fully off to nothing).
    protected float transitionPosition = 1;

    protected ScreenState screenState = ScreenState.TransitionOn;

    // There are two possible reasons why a screen might be transitioning
    // off. It could be temporarily going away to make room for another
    // screen that is on top of it, or it could be going away for good.
    // This property indicates whether the screen is exiting for real:
    // if set, the screen will automatically remove itself as soon as the
    // transition finishes.
    protected boolean isExiting = false;

// -------------------------- OTHER METHODS --------------------------

    public void Draw(SpriteBatch batch, RenderPass pass) {
    }

    // Tells the screen to go away. Unlike ScreenManager.RemoveScreen, which
    // instantly kills the screen, this method respects the transition timings
    // and will give the screen a chance to gradually transition off.
    public void ExitScreen() {
        if (Util.IsZero(TransitionOffTime)) {
            // If the screen has a zero transition time, remove it immediately.
            ScreenManager.RemoveScreen(this);
        } else {
            // Otherwise flag that it should transition off and then exit.
            isExiting = true;
        }
    }

    public ScreenState GetScreenState() {
        return screenState;
    }

    // Gets the current alpha of the screen transition, ranging
    // 255 (fully active, no transition) to
    // 0 (transitioned fully off to nothing).
    public int GetTransitionAlpha() {
        return 255 - (int) (transitionPosition * 255);
    }

    public float GetTransitionPosition() {
        return transitionPosition;
    }

    // Allows the screen to handle user input. Unlike Update, this method
    // is only called when the screen is active, and not when some other
    // screen has taken the focus.
    public void HandleInput() {
    }

    public boolean IsActive() {
        return !otherScreenHasFocus &&
                (screenState == screenState.TransitionOn ||
                        screenState == screenState.Active);
    }

    public boolean IsExiting() {
        return isExiting;
    }

    public void LoadContent() {
    }

    public void UnloadContent() {
        ScreenManager = null;
    }

    // Allows the screen to run logic, such as updating the transition position.
    // Unlike HandleInput, this method is called regardless of whether the screen
    // is active, hidden, or in the middle of a transition.
    public void Update(float dt, boolean otherScreenHasFocus,
                       boolean coveredByOtherScreen) {
        this.otherScreenHasFocus = otherScreenHasFocus;

        if (isExiting) {
            // If the screen is going away to die, it should transition off.
            screenState = screenState.TransitionOff;

            if (!UpdateTransition(dt, TransitionOffTime, 1)) {
                // When the transition finishes, remove the screen.
                ScreenManager.RemoveScreen(this);

                isExiting = false;
            }
        } else if (coveredByOtherScreen) {
            // If the screen is covered by another, it should transition off.
            if (UpdateTransition(dt, TransitionOffTime, 1)) {
                // Still busy transitioning.
                screenState = screenState.TransitionOff;
            } else {
                // Transition finished!
                screenState = screenState.Hidden;
            }
        } else {
            // Otherwise the screen should transition on and become active.
            if (UpdateTransition(dt, TransitionOnTime, -1)) {
                // Still busy transitioning.
                screenState = screenState.TransitionOn;
            } else {
                // Transition finished!
                screenState = screenState.Active;
            }
        }
    }

    // Helper for updating the screen transition position.
    boolean UpdateTransition(float dt, float time, int direction) {
        // How much should we move by?
        float transitionDelta = 0;

        if (Util.IsZero(time))
            if (Util.IsZero(time))
                transitionDelta = 1;
            else
                transitionDelta = dt / time;

        // Update the transition position.
        transitionPosition += transitionDelta * direction;

        // Did we reach the end of the transition?
        if ((transitionPosition <= 0) || (transitionPosition >= 1)) {
            transitionPosition = Util.Clamp(transitionPosition, 0, 1);
            return false;
        }

        // Otherwise we are still busy transitioning.
        return true;
    }
    
    public static Screen NoneScreen()
    {
        return new Screen();
    }
}
