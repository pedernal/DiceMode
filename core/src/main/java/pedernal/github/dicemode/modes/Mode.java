package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import pedernal.github.dicemode.Main.MainProgramInterface;
import pedernal.github.dicemode.utilities.EditDiceSystem;

import java.util.function.Supplier;

public class Mode implements Screen {
    private MainProgramInterface mainProgram;
    private Stage stage;
    private Table table;
    private Skin skin;
    private EditDiceSystem editDiceSystem;
    private TextButton rollButton;
    private ModeChanger modeChanger;

    public Mode(MainProgramInterface mainProgram)
    {
        this.mainProgram = mainProgram;
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("./skin/clean-crispy-ui.json"));
        //setup skin stuff
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("./skin/clean-crispy-ui.atlas"));
        skin.addRegions(atlas);
            //Generate fonts to add to skin
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("./NotoSansMono-Bold.ttf"));
        skin.add( "NotoMono", fontGenerator.generateFont(varyFontSize(12)) );
        skin.add( "BigNotoMono", fontGenerator.generateFont(varyFontSize(40)) );
        fontGenerator.dispose();

        table = new Table();
        table.setFillParent(true);

        editDiceSystem = new EditDiceSystem(skin);

        rollButton = new TextButton("Roll", skin);

        modeChanger = new ModeChanger(skin);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        //setup table stuff
        table.setDebug(false); //TODO: DELETE: Only for debugging
        table.add(modeChanger);
        table.row();
        table.add(editDiceSystem.getTable());
        table.row();
    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(Color.GRAY);

        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    public void setupTableLayout(Supplier<Cell <? extends Actor>> addButtons) {
        addButtons.get().expandY().bottom();
        table.row();
        table.add(rollButton).
            expandY().
            width(100).
            height(90).
            bottom().
            padBottom(50);
    }

    public MainProgramInterface getMainProgram() {
        return mainProgram;
    }

    public Skin getSkin() {
        return skin;
    }

    public Table getTable() { return table; }

    public EditDiceSystem getEditDiceSystem() { return editDiceSystem; }


    public ModeChanger getModeChanger() { return modeChanger; }

    public void pressRollButton(Runnable diceBehavior) {
        rollButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { diceBehavior.run(); }
        });
    }

    private FreeTypeFontParameter varyFontSize(int size) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.DARK_GRAY;
        parameter.shadowColor = Color.WHITE;
        parameter.shadowOffsetY = 2;
        return parameter;
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
