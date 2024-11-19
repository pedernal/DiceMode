package pedernal.github.dicemode;

import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;

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

    @Override
    public void updateFrom(EditDiceSystem editDiceSystem) {
        editDiceSystem.setUpFacesInput();
        editDiceSystem.setUpLimitInput("Rolls");
        editDiceSystem.setUpUpdateButton(() -> {
            int parsedFacesInput = Integer.parseInt(editDiceSystem.getFacesInput());
            int parsedRollsInput = Integer.parseInt(editDiceSystem.getLimitInput());

            setNumberOfFaces(parsedFacesInput);
            setLimit(parsedRollsInput);

            diceContainer.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" x"+getLimit());
        });
    }
}
