package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;
import java.util.LinkedList;

public class DiceUntil extends AbstractDice{
    private DiceDispalySystem diceDisplay;

    public DiceUntil(int faces, int target, Skin skin) {
        super(faces, Math.min(Math.abs(target), faces), new LinkedList<Integer>(), skin);

        String name = "d"+faces+" -> "+target;
        diceDisplay = new DiceDispalySystem(name, skin);
        diceDisplay.update(formatMemoryString(), formatTotalString());
        setActor(diceDisplay);
    }

    @Override
    public void roll() {
        setTotal(0);
        populateMemory();
        diceDisplay.update(formatMemoryString(), formatTotalString());
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

    @Override
    public void updateFrom(EditDiceSystem editDiceSystem) {
        editDiceSystem.setUpFacesInput();
        editDiceSystem.setUpLimitInput("Target");
        editDiceSystem.setUpUpdateButton(() -> { //validating input
            int parsedFacesInput = Integer.parseInt(editDiceSystem.getFacesInput());
            int parsedTargetInput = Integer.parseInt(editDiceSystem.getLimitInput());

            setNumberOfFaces(parsedFacesInput);
            setLimit(parsedTargetInput, (limit) -> limit <= 0 || limit > getNumberOfFaces());

            diceDisplay.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" -> "+getLimit());
        }, () -> { //when validation fails
            setNumberOfFaces(6);
            setLimit(1);
            diceDisplay.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" -> "+getLimit());
            Gdx.app.log("State", "Set dice to d6 -> 1");
        });
    }
}
