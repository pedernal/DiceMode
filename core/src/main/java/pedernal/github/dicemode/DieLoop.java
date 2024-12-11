/**Class extension of AbstractDie to make a die that rolls for a given number of times.
 * Roll implements asynchronicity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode;

import java.util.ArrayList;
import java.util.concurrent.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.*;

public class DieLoop extends AbstractDie{
    private CompletableFuture<String[]> future;
    private final DieDisplaySystem dieDisplay;

    public DieLoop(int faces, int rolls, Skin skin) {
        super(faces, Math.abs(rolls), new ArrayList<Integer>(rolls), skin);

        future = CompletableFuture.supplyAsync(() -> { return new String[]{}; });
        String name = "d"+faces+" x"+rolls;
        dieDisplay = new DieDisplaySystem(name, skin);
        dieDisplay.update(formatMemoryString(), formatTotalString());
        setActor(dieDisplay);
    }

    @Override
    public void roll() {
        dieDisplay.update("       ···", "Total: ···");

        future.cancel(true);

        future = CompletableFuture.supplyAsync(() -> {
            populateMemory();
            return new String[]{formatMemoryString(), formatTotalString()};
        });
        future.thenAccept((result) ->
            Gdx.app.postRunnable(() -> dieDisplay.update(result[0], result[1]))
        );
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
    public void updateFrom(EditDieSystem editDieSystem) {
        editDieSystem.setUpFacesInput();
        editDieSystem.setUpLimitInput("Rolls");
        editDieSystem.setUpUpdateButton(() -> {
            int parsedFacesInput = Integer.parseInt(editDieSystem.getFacesInput());
            int parsedRollsInput = Integer.parseInt(editDieSystem.getLimitInput());
            int oldLimit = getLimit();

            setNumberOfFaces(parsedFacesInput);
            setLimit(parsedRollsInput);

            resetMemoryCapacity(oldLimit, parsedRollsInput);

            dieDisplay.getElement(DiePart.NAME).setText("d"+getNumberOfFaces()+" x"+getLimit());
            dieDisplay.childrenChanged();
        });
    }

    private void resetMemoryCapacity(int oldLimit, int newLimit) {
        if (oldLimit < newLimit) {
            ((ArrayList<Integer>) getMemory()).ensureCapacity(newLimit);
        }
    }

    @Override
    public void dispose() {
        future.cancel(true);
    }
}
