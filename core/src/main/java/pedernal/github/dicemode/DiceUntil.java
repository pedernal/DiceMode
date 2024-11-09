package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.DiceDispalySystem;

import java.util.LinkedList;

public class DiceUntil extends AbstractDice{
    private DiceDispalySystem diceContainer;

    public DiceUntil(int faces, int target, Skin skin) {
        super(faces, Math.min(Math.abs(target), faces), new LinkedList<Integer>(), skin);

        String name = "d"+faces+" -> "+target;
        diceContainer = new DiceDispalySystem(name, skin);
        diceContainer.update(formatMemoryString(), formatTotalString());
        setActor(diceContainer);
        diceContainer.update(formatMemoryString(), formatTotalString());
    }

    @Override
    public void roll() {
        setTotal(0);
        populateMemory();
        diceContainer.update(formatMemoryString(), formatTotalString());
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
