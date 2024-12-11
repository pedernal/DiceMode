/**Class that provides a "console" widget to show messages.*/

package pedernal.github.dicemode.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Console extends Container<ScrollPane> {
    private Label label;

    public Console(Skin skin) {
        super();
        LabelStyle labelStyle = new LabelStyle(skin.get(LabelStyle.class));
        labelStyle.font = skin.getFont("NotoConsole");
        label = new Label("", labelStyle);
        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.setFadeScrollBars(false);

        setActor(scrollPane);
        setBackground(skin.newDrawable("color", Color.DARK_GRAY));
        left();
        pad(10);
    }

    /**Clears and sets the text and color of the console text.
     * @param text String to be displayed on the console.
     * @param fontColor color the text will be displayed on.*/
    public void setText(String text, Color fontColor) {
        label.getStyle().fontColor = fontColor;
        label.setText(text);
    }
}
