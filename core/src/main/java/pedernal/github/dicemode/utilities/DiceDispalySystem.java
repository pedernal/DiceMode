/**Widget UI system to display all sorts of children from AbstractDice, extends from LibGDX's VerticalGroup**/

package pedernal.github.dicemode.utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.EnumMap;

public class DiceDispalySystem extends VerticalGroup {
    private EnumMap<DicePart, Container<Actor>> elements;
    private Skin skin;
    private ScrollPane scrollPane;

    public DiceDispalySystem(String name, Skin skin, float width, float height) {
        super();
        this.skin = skin;
        elements = new EnumMap<DicePart, Container<Actor>>(DicePart.class);

        //setting up unique style from skin for the labels' elements (adding background to these labels)
        Drawable labelBackground = this.skin.newDrawable("tooltip-c");
        Label.LabelStyle labelStyle = new Label.LabelStyle(this.skin.get(Label.LabelStyle.class)); //clone LabelStyle from skin
        labelStyle.background = labelBackground;
        labelStyle.font = this.skin.getFont("NotoMono");

        elements.put(DicePart.NAME, new Container<Actor>(
            new Label(name, labelStyle))
        );
        scrollPane = new ScrollPane(new Label("", labelStyle), this.skin);
        scrollPane.setFadeScrollBars(false);
        elements.put(DicePart.BODY, new Container<Actor>(scrollPane));
        elements.put(DicePart.TOTAL, new Container<Actor>(
            new Label("Total: 0", labelStyle))
        );

        //setting up the height containers
        elements.values().forEach((container) -> {
            container.width(width).height(30);
        });
        elements.get(DicePart.BODY).height(height);

        elements.values().forEach((container) -> addActor(container));
        //this.pad(2);
    }
    public DiceDispalySystem(String name, Skin skin) {
        this(name, skin, 130, 100);
    }

    /**Updates the display of body (dice memory) and the total. Synchronized to assure thread safety.
     * @param body String to place on body Label of the display.
     * @param total String to place on total Label of the display.*/
    public synchronized void update(String body, String total) {
        Label labelTotal = getElement(DicePart.TOTAL);
        Label labelBody = getElement(DicePart.BODY);

        labelBody.setText(body);
        labelTotal.setText(total);

        childrenChanged();
    }

    /** Get specific Label that composes the UI widget.
     * @param label enum value.
     * @return Label*/
    public Label getElement (DicePart label) {
        Label toReturn = null;

        switch (label) {
            case NAME:
                toReturn = (Label) (elements.get(DicePart.NAME).getActor());
                break;
            case BODY:
                toReturn = (Label) ((ScrollPane) elements.get(DicePart.BODY).getActor()).getActor();
                break;
            case TOTAL:
                toReturn = (Label) (elements.get(DicePart.TOTAL).getActor());
                break;
            default:
                break;
        }

        return toReturn;
    }

    /**For when visual elements stat has changed, call this at the end*/
    @Override
    public void childrenChanged() {
        super.childrenChanged();
    }

    /**@return the EnumMap of all Container objects that make the UI widget*/
    public EnumMap<DicePart, Container<Actor>> getElements() {
        return elements;
    }
}
