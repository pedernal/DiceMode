package pedernal.github.dicemode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.modes.DiceMode;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        this.setScreen(new DiceMode(this));
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
    }
}
