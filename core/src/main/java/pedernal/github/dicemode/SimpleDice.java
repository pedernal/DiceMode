package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import pedernal.github.dicemode.utilities.EditDiceSystem;

import java.util.ArrayList;

public class SimpleDice extends AbstractDice {
    private Container<Window> diceDisplay;

    public SimpleDice(int numberOfFaces, Skin skin) {
        super(numberOfFaces, 0, new ArrayList<Integer>(), skin);
        getMemory().add(numberOfFaces);
        setTotal(numberOfFaces);
        diceDisplay = new Container<Window>(
            new Window("d"+numberOfFaces, skin)
        );
        diceDisplay.width(100); diceDisplay.height(100);
        String content = String.format("%"+4+"d", getTotal());
        diceDisplay.getActor().add(content);
        VerticalGroup group = new VerticalGroup(); group.addActor(diceDisplay);
        setActor(group);
    }

    @Override
    public void roll() {
        populateMemory();
        setTotal(getMemory().getFirst());

        String content = String.format("%"+4+"d", getTotal());
        updateDiceDisplay(content);
    }

    @Override
    public void populateMemory() {
        getMemory().set(0, getRandomNumber());
    }

    @Override
    public void updateFrom(EditDiceSystem editDiceSystem) {
        editDiceSystem.setUpFacesInput();
        editDiceSystem.setUpUpdateButton(() -> {
            int parsedInput = Integer.parseInt(editDiceSystem.getFacesInput());

            setNumberOfFaces(parsedInput);
            diceDisplay.getActor().getTitleLabel().setText("d"+parsedInput);
            updateDiceDisplay(Integer.toString(parsedInput));
        });
    }

    private void updateDiceDisplay(String content) {
        diceDisplay.getActor().clear();
        diceDisplay.getActor().add(content);
    }

    @Override
    public String toString() {
        return formatTotalString();
    }
}
