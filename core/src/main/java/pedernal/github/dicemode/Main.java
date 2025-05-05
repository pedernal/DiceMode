/**Main program class. Implements an interface that exposes the mechanism for Modes (Screen) to change to other Modes. Extends LibGDX's Game.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import pedernal.github.dicemode.modes.DieLoopMode;
import pedernal.github.dicemode.modes.Mode;
import pedernal.github.dicemode.modes.SimpleDieMode;

public class Main extends Game {
    private AssetWell assetWell;

    @Override
    public void create() {
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();

        assetWell = new AssetWell();
        //Implementing interface to expose Mode switching mechanism to Mode types
        MainProgramInterface mainProgramExp = new MainProgramInterface() {

            @Override
            public void switchScreen(Mode mode) {
                try {
                    assetWell.load();
                    assetWell.getManager().finishLoading();

                    getScreen().dispose();
                    Main.this.setScreen(mode);
                } catch (Exception e) {
                    Gdx.app.error("AssetManager", "At switching screen; "+e.getMessage());
                }
            }

            @Override
            public AssetWell getAssetWell() {
                return assetWell;
            }
        };

        assetWell.load();
        assetWell.getManager().finishLoading();
        this.setScreen(new SimpleDieMode(mainProgramExp));
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
        assetWell.dispose();
    }

    /**Interface to implement how Mods will change.*/
    public interface MainProgramInterface {
        /**@param mode a new instance of a Mode.*/
        void switchScreen(Mode mode);
        public AssetWell getAssetWell();
    }
}
