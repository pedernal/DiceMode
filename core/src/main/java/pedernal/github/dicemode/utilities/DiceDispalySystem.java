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
            container.width(width); container.height(30);
        });
        elements.get(DicePart.BODY).height(height);

        elements.values().forEach((container) -> addActor(container));
    }
    public DiceDispalySystem(String name, Skin skin) {
        this(name, skin, 130, 100);
    }

    public void update(String body, String total) {
        Label labelTotal = getElement(DicePart.TOTAL);
        Label labelBody = getElement(DicePart.BODY);

        labelBody.setText(body);
        labelTotal.setText(total);

        super.childrenChanged();
    }

    /**@param label
     * Given the enum value, returns the corresponding label that composes the dice display system
     * */
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

    public EnumMap<DicePart, Container<Actor>> getElements() {
        return elements;
    }
}
