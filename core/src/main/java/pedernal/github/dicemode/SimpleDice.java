package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.util.ArrayList;

public class SimpleDice extends AbstractDice {
    private Container<Window> diceDisplay;

    public SimpleDice(int numberOfFaces, Skin skin) {
        super(numberOfFaces, 1, new ArrayList<Integer>(), skin);
        getMemory().add(numberOfFaces);
        setTotal(numberOfFaces);
        diceDisplay = new Container<Window>(
            new Window("d"+numberOfFaces, skin)
        );
        diceDisplay.width(100); diceDisplay.height(100);
        String content = String.format("%"+4+"d", getTotal());
        diceDisplay.getActor().add(content);
        addActor(diceDisplay);
    }

    @Override
    public void roll() {
        populateMemory();
        setTotal(getMemory().getFirst());
        diceDisplay.getActor().clear();
        String content = String.format("%"+4+"d", getTotal());
        diceDisplay.getActor().add(content);
    }

    @Override
    public void populateMemory() {
        getMemory().set(0, getRandomNumber());
    }

    @Override
    public String toString()
    {
        return formatTotalString();
    }
}
