package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.DiceDispalySystem;

import java.util.LinkedList;

public class DiceUntil extends AbstractDice{
    private DiceDispalySystem diceWidget;

    public DiceUntil(int faces, int target, Skin skin) {
        super(faces, Math.min(Math.abs(target), faces), new LinkedList<Integer>(), skin);

        String name = "d"+faces+" -> "+target;
        diceWidget = new DiceDispalySystem(name, skin);
        diceWidget.update(formatMemoryString(), formatTotalString());
        addActor(diceWidget);
    }

    @Override
    public void roll() {
        setTotal(0);
        populateMemory();
        diceWidget.update(formatMemoryString(), formatTotalString());
    }

    @Override
    public void populateMemory() {
        getMemory().clear();
        int currentRoll = 0;
        do {
            currentRoll = getRandomNumber();
            getMemory().add(currentRoll);
            setTotal(getTotal()+currentRoll);
        } while (currentRoll != getLimit());
    }
}
