package pedernal.github.dicemode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.modes.DiceLoopMode;
import pedernal.github.dicemode.modes.Mode;
import pedernal.github.dicemode.modes.SimpleDiceMode;
import pedernal.github.dicemode.modes.DiceUntilMode;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private Mode mode;

    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        mode = new DiceUntilMode(this);
        this.setScreen(mode);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int height, int width) {
    }

    @Override
    public void dispose() {
        mode.dispose();
    }
}
