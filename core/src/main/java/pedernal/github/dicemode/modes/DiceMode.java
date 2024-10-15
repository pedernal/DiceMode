package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import pedernal.github.dicemode.Dice;

public class DiceMode implements Screen {
    private Dice d6;
    private Stage stage;

    public DiceMode() {
        stage = new Stage(new FitViewport(9, 6));
        d6 = new Dice();

        //TODO: add actors to the stage
    }

    @Override
    /**When the screen gains focus*/
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.GRAY);

        input();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int height, int width) {
        stage.getViewport().update(height, width, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    //OTHER METHODS:
    //TODO: handle input by InputListener in Stage, with an actor
    //call Gdx.input.setInputProcessor(stage); on show()
    private void input() {
        if (Gdx.input.isTouched()) {
            System.out.println(d6.roll());
        }
    }
}
