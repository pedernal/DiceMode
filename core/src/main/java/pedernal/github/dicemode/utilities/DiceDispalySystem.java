package pedernal.github.dicemode.utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.EnumMap;

public class DiceDispalySystem extends VerticalGroup {
    private EnumMap<DicePart, Container<Actor>> elements;
    private Skin skin;
    private ScrollPane scrollPane;

    public DiceDispalySystem(String name, Skin skin, float width, float height) {
        super();
        this.skin = skin;
        elements = new EnumMap<DicePart, Container<Actor>>(DicePart.class);

        elements.put(DicePart.NAME, new Container<Actor>(
            new Label(name, this.skin))
        );
        scrollPane = new ScrollPane(new Label("", this.skin), this.skin);
        elements.put( DicePart.BODY, new Container<Actor>(scrollPane));
        elements.put(DicePart.TOTAL, new Container<Actor>(
            new Label("Total: 0", this.skin))
        );

        elements.values().forEach((container) -> {
            container.width(width); container.height(30);
        });
        elements.get(DicePart.BODY).height(height);

        elements.values().forEach((container) -> addActor(container));
    }
    public DiceDispalySystem(String name, Skin skin) {
        this(name, skin, 100, 100);
    }

    public void update(String body, String total) {
        Label labelTotal = (Label) elements.get(DicePart.TOTAL).getActor();
        Label labelBody = (Label) ((ScrollPane) elements.get(DicePart.BODY).getActor()).getActor();

        labelBody.setText(body);
        labelTotal.setText(total);

        super.childrenChanged();
    }

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
}
