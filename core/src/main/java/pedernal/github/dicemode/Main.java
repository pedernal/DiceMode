/**Main program class. Implements an interface that exposes the mechanism for Modes (Screen) to change to other Modes. Extends LibGDX's Game.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.modes.Mode;
import pedernal.github.dicemode.modes.SimpleDiceMode;

public class Main extends Game {
    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        //Implementing interface to expose Mode switching mechanism to Mode types
        MainProgramInterface mainProgramExp = new MainProgramInterface(){
            @Override
            public void switchScreen(Mode mode) {
                getScreen().dispose();
                Main.this.setScreen(mode);
            }
        };
        this.setScreen(new SimpleDiceMode(mainProgramExp));
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
        //getScreen().dispose();
    }

    /**Interface to implement how Mods will change.*/
    public interface MainProgramInterface {
        /**@param mode a new instance of a Mode.*/
        public void switchScreen(Mode mode);
    }
}
