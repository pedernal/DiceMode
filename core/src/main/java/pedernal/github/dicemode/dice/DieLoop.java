/**Class extension of AbstractDie to make a die that rolls for a given number of times.
 * Roll implements asynchronicity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode.dice;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.AssetWell;
import pedernal.github.dicemode.utilities.*;

@SuppressWarnings("NewApi")
public class DieLoop extends AbstractDie{
    private final DieDisplaySystem dieDisplay;

    public DieLoop(int faces, int rolls, AssetWell assetWell) {
        super(faces, Math.abs(rolls), new ArrayList<Integer>(rolls), assetWell);

        String name = "d"+faces+" x"+rolls;
        dieDisplay = new DieDisplaySystem(name, assetWell);
        dieDisplay.update(formatMemoryString(), formatTotalString());
        setActor(dieDisplay);
    }

    @Override
    public CompletableFuture<String[]> roll() throws ExecutionException, InterruptedException {
        dieDisplay.update("       ···", "Total: ···");

        getFuture().cancel(true);

        setFuture(() -> {
            populateMemory();
            return new String[]{formatMemoryString(), formatTotalString()};
        });
        getFuture().thenAccept( (result) -> Gdx.app.postRunnable(() -> dieDisplay.update(result[0], result[1])) );

        return getFuture();
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
        editDieSystem.UISetup().
            facesInput().
            limitInput("Rolls").
            updateButton(() -> {
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

    /*@Override
    public void dispose() {
        getFuture().cancel(true);
    }*/
}
