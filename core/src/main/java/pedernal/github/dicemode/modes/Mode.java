package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Mode implements Screen {
    private Game mainProgram;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Skin skin;
    private TextButton rollButton;

    public Mode(Game mainProgram)
    {
        this.mainProgram = mainProgram;
        stage = new Stage(new ScreenViewport(new OrthographicCamera()));
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("./NotoSansMono-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 17;
        parameter.shadowColor = Color.DARK_GRAY;
        parameter.shadowOffsetX = 2; parameter.shadowOffsetY = 2;
        font = fontGenerator.generateFont(parameter);
        fontGenerator.dispose();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./skin/clean-crispy-ui.atlas"));
        skin = new Skin(Gdx.files.internal("./skin/clean-crispy-ui.json"));
        skin.addRegions(atlas);

        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true); //FIXME: DELETE THIS. Only for debugging

        TextButtonStyle buttonStyle = skin.get(TextButtonStyle.class);
        rollButton = new TextButton("Roll", buttonStyle);

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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() { return stage; }

    public Table getTable() { return table; }

    public BitmapFont getFont () {
        return font;
    }

    public TextButton getRollButton() {
        return rollButton;
    }

    public void pressRollButton(Runnable action) {
        rollButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { action.run(); }
        });
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        skin.dispose();
    }
}
