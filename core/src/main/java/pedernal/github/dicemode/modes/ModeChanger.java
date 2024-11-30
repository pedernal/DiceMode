package pedernal.github.dicemode.modes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ModeChanger extends HorizontalGroup {
    private TextButton leftButton;
    private TextButton rightButton;

    public ModeChanger(Skin skin) {
        super();

        leftButton = new TextButton("left", skin);
        rightButton = new TextButton("right", skin);
        addActor(leftButton);
        addActor(rightButton);

        pad(50);
    }

    public void setUpButtons(Runnable leftBehavior, Runnable rightBehavior, String leftButtonStr, String rightButtonStr) {
        leftButton.clearListeners();
        rightButton.clearListeners();

        leftButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftBehavior.run();
            }
        });

        rightButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightBehavior.run();
            }
        });

        leftButton.setText(leftButtonStr);
        rightButton.setText(rightButtonStr);
    }
}
