package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;

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

    @Override
    public void updateFrom(EditDiceSystem editDiceSystem) {
        editDiceSystem.setUpFacesInput();
        editDiceSystem.setUpLimitInput("Target");
        editDiceSystem.setUpUpdateButton(() -> { //validating input
            int parsedFacesInput = Integer.parseInt(editDiceSystem.getFacesInput());
            int parsedTargetInput = Integer.parseInt(editDiceSystem.getLimitInput());

            setNumberOfFaces(parsedFacesInput);
            setLimit(parsedTargetInput, (limit) -> limit <= 0 || limit > getNumberOfFaces());

            diceContainer.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" -> "+getLimit());
        }, () -> { //when validation fails
            setNumberOfFaces(6);
            setLimit(1);
            diceContainer.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" -> "+getLimit());
            Gdx.app.log("State", "Set dice to d6 -> 1");
        });
    }
}
