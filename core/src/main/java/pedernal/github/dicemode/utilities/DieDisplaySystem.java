/**Widget UI system to display all sorts of children from AbstractDie, extends from LibGDX's VerticalGroup**/

package pedernal.github.dicemode.utilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import pedernal.github.dicemode.AssetWell;
import pedernal.github.dicemode.AssetWell.AssetID;

import java.util.EnumMap;

public class DieDisplaySystem extends VerticalGroup {
    private final EnumMap<DiePart, Container<Actor>> elements;
    private AssetWell assetWell;
    private ScrollPane scrollPane;

    public DieDisplaySystem(String name, AssetWell assetWell, float width, float height) {
        super();
        this.assetWell = assetWell;
        elements = new EnumMap<DiePart, Container<Actor>>(DiePart.class);

        //setting up unique style from skin for the labels' elements (adding background to these labels)
        Skin skin = assetWell.get(AssetID.SKIN, Skin.class);
        Drawable labelBackground = skin.newDrawable("tooltip-c");
        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class)); //clone LabelStyle from skin
        labelStyle.background = labelBackground;
        labelStyle.font = assetWell.get(AssetID.FONT_MONO, BitmapFont.class);

        Label nameLabel = new Label(name, labelStyle);
        nameLabel.setAlignment(Align.center);
        elements.put(DiePart.NAME, new Container<Actor>(nameLabel));
        System.out.println();
        scrollPane = new ScrollPane(new Label("", labelStyle), skin);
        scrollPane.setFadeScrollBars(false);
        elements.put(DiePart.BODY, new Container<Actor>(scrollPane));
        elements.put(DiePart.TOTAL, new Container<Actor>(
            new Label("Total: 0", labelStyle))
        );

        //setting up the height containers
        elements.values().forEach((container) -> container.width(width).height(30));
        elements.get(DiePart.BODY).height(height);

        elements.values().forEach((container) -> addActor(container));
        //this.pad(2);
    }
    public DieDisplaySystem(String name, AssetWell assetWell) {
        this(name, assetWell, 130, 100);
    }

    /**Updates the display of roll(s) and the total. Synchronized to assure thread safety.
     * @param body String to place on body Label of the display.
     * @param total String to place on total Label of the display.*/
    public synchronized void update(String body, String total) {
        Label labelTotal = getElement(DiePart.TOTAL);
        Label labelBody = getElement(DiePart.BODY);

        labelBody.setText(body);
        labelTotal.setText(total);

        childrenChanged();
    }

    /** Get specific Label that composes the UI widget.
     * @param label enum value from {@link DiePart}.
     * @return Label*/
    public Label getElement (DiePart label) {
        Label toReturn = null;

        switch (label) {
            case NAME:
                toReturn = (Label) (elements.get(DiePart.NAME).getActor());
                break;
            case BODY:
                toReturn = (Label) ((ScrollPane) elements.get(DiePart.BODY).getActor()).getActor();
                break;
            case TOTAL:
                toReturn = (Label) (elements.get(DiePart.TOTAL).getActor());
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
    public EnumMap<DiePart, Container<Actor>> getElements() {
        return elements;
    }
}
