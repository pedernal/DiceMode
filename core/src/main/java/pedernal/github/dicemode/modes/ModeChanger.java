/**Widget that makes changing of Mode possible. Extends from LibGDX's HorizontalGroup.*/

package pedernal.github.dicemode.modes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ModeChanger extends Table {
    private TextButton leftButton;
    private TextButton rightButton;
    private Label header;

    public ModeChanger(Skin skin) {
        super();

        leftButton = new TextButton("Left", skin);
        rightButton = new TextButton("Right", skin);
        header = new Label("Mode", skin);
        add(leftButton).padRight(10);
        add(header).expandX();
        add(rightButton).padLeft(10);

        padBottom(50);
        padTop(50);
    }

    /**Sets up the buttons' implementation that should change to other Modes.
     * @param leftBehavior runnable lambda expression that implements the behavior of the left button.
     * @param rightBehavior runnable lambda expression that implements.
     * @param leftButtonStr String to display on left button.
     * @param rightButtonStr String to display on right button.
     * */
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

    /**Sets up the behavior of left button when pressed.
     * @param leftBehavior runnable lambda expression that implements the changing to a Mode.
     * @return this instance to chain call other setup methods.*/
    public ModeChanger setLeftButtonInput(Runnable leftBehavior) {
        leftButton.clearListeners();
        leftButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { leftBehavior.run(); }
        });
        return this;
    }

    /**Sets up the behavior of right button when pressed
     * @param rightBehavior runnable lambda expression that implements the changing to another Mode.
     * @return this instance to chain call other setup methods.*/
    public ModeChanger setRightButtonInput(Runnable rightBehavior) {
        rightButton.clearListeners();
        rightButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { rightBehavior.run(); }
        });
        return this;
    }

    /**Sets the text for the left button.
     * @param text
     * @return this instance to chain call other setup methods*/
    public ModeChanger setLeftButtonText(String text) {
        leftButton.setText(text);
        return this;
    }

    /**Sets the text for the right button.
     * @param text
     * @return this instance to chain call other setup methods*/
    public ModeChanger setRightButtonText(String text) {
        rightButton.setText(text);
        return this;
    }

    /**Sets the text for the header.
     * @param text*/
    public void setHeaderText(String text) {
        header.setText(text);
    }
}
