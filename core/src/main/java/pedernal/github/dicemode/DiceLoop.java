package pedernal.github.dicemode;

import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.DiceDispalySystem;

public class DiceLoop extends AbstractDice{
    private DiceDispalySystem diceContainer;

    public DiceLoop(int faces, int rolls, Skin skin) {
        super(faces, Math.min(faces, rolls), new ArrayList<Integer>(rolls), skin);

        String name = "d"+faces+" x"+rolls;
        diceContainer = new DiceDispalySystem(name, skin);
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
        for (int i = 0; i < getLimit(); ++i) {
            int randomNum = getRandomNumber();
            getMemory().add(randomNum);
            addToTotal(getMemory().getLast());
        }
    }
}
