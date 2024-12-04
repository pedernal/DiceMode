/**Class extention of AbstractDice to make a dice that rolls for a given number of times.
 * Roll implements asynchronity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;

public class DiceLoop extends AbstractDice{
    private CompletableFuture<String[]> future;
    private DiceDispalySystem diceDisplay;

    public DiceLoop(int faces, int rolls, Skin skin) {
        super(faces, Math.min(faces, rolls), new ArrayList<Integer>(rolls), skin);

        future = CompletableFuture.supplyAsync(() -> { return new String[]{}; });
        String name = "d"+faces+" x"+rolls;
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

            diceDisplay.getElement(DicePart.NAME).setText("d"+getNumberOfFaces()+" x"+getLimit());
            diceDisplay.childrenChanged();
        });
    }

    @Override
    public void dispose() {
        future.cancel(true);
    }
}
