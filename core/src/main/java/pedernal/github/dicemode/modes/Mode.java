package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Mode implements Screen {
    private Game mainProgram;
    private Stage stage;
    private Table table;
    private BitmapFont font;

    public Mode(Game mainProgram)
    {
        this.mainProgram = mainProgram;
        stage = new Stage(new FitViewport(120, 180));
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("./UbuntuMono-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 17;
        parameter.shadowColor = Color.DARK_GRAY;
        parameter.shadowOffsetX = 2; parameter.shadowOffsetY = 2;
        font = fontGenerator.generateFont(parameter);
        fontGenerator.dispose();


        table = new Table();
        table.setFillParent(true);
        table.center();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.GRAY);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1);
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

    public Stage getStage() { return stage; }

    public Table getTable() { return table; }

    public BitmapFont getFont () {
        return font;
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
