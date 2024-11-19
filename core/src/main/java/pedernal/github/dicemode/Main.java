package pedernal.github.dicemode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.modes.DiceLoopMode;
import pedernal.github.dicemode.modes.Mode;
import pedernal.github.dicemode.modes.SimpleDiceMode;
import pedernal.github.dicemode.modes.DiceUntilMode;

public class Main extends Game {
    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        MainProgramInterface mainProgramExp = new MainProgramInterface() {
            @Override
            public void switchScreen(Mode mode) {
                Main.this.dispose();
                Main.this.setScreen(mode);
            }
        };
        this.setScreen(new DiceUntilMode(mainProgramExp));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        getScreen().resize(width, height);
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }

    public interface MainProgramInterface {
        public void switchScreen(Mode mode);
    }
}
