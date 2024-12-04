/**Class extention of AbstractDice to make a dice that will keep rolling until a specific number (limit) is rolled.
 * Roll implements asynchronity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class DiceUntil extends AbstractDice{
    private CompletableFuture<String[]> future;
    private DiceDispalySystem diceDisplay;

    public DiceUntil(int faces, int target, Skin skin) {
        super(faces, Math.min(Math.abs(target), faces), new LinkedList<Integer>(), skin);

        future = CompletableFuture.supplyAsync(() -> { return new String[]{}; });
        String name = "d"+faces+" -> "+target;
        diceDisplay = new DiceDispalySystem(name, skin);
        diceDisplay.update(formatMemoryString(), formatTotalString());

        setActor(diceDisplay);
    }

    @Override
    public void roll() {
        diceDisplay.update("···", "···");

        future.cancel(true);

        future = CompletableFuture.supplyAsync(() -> {
            populateMemory();
            return new String[]{formatMemoryString(), formatTotalString()};
        });
        future.thenAccept((result) -> {
            Gdx.app.postRunnable(() -> { diceDisplay.update(result[0], result[1]); });
        });
    }

    @Override
    public synchronized void populateMemory() {
        setTotal(0);
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
            diceDisplay.childrenChanged();
        }, () -> { //when validation fails
            setNumberOfFaces(6);
            setLimit(1);
            diceDisplay.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" -> "+getLimit());
            diceDisplay.childrenChanged();
            Gdx.app.log("State", "Set dice to d6 -> 1");
        });
    }

    @Override
    public void dispose() {
        future.cancel(true);
    }
}
