/**Class extension of AbstractDie to make a die that will keep rolling until a specific number (limit) is rolled.
 * Roll implements asynchronicity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import pedernal.github.dicemode.utilities.*;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("NewApi")
public class DieUntil extends AbstractDie{
    private final DieDisplaySystem dieDisplay;

    public DieUntil(int faces, int target, Skin skin) {
        super(faces, Math.min(Math.abs(target), faces), new LinkedList<Integer>(), skin);

        String name = "d"+faces+" -> "+target;
        dieDisplay = new DieDisplaySystem(name, skin);
        dieDisplay.update(formatMemoryString(), formatTotalString());

        setActor(dieDisplay);
    }

    @Override
    public void roll() {
        dieDisplay.update("       ···", "Total: ···");

        getFuture().cancel(true);
        setFuture(() -> {
           populateMemory();
           return new String[] {formatMemoryString(), formatTotalString()};
        }).thenAccept( (result) -> Gdx.app.postRunnable(() -> dieDisplay.update(result[0], result[1])) );
    }

    @Override
    public synchronized void populateMemory() {
        setTotal(0);
        getMemory().clear();
        int currentRoll;
        do {
            currentRoll = getRandomNumber();
            getMemory().add(currentRoll);
            setTotal(getTotal()+currentRoll);
        } while (currentRoll != getLimit());
    }

    @Override
    public void updateFrom(EditDieSystem editDieSystem) {
        editDieSystem.UISetup().
            facesInput().
            limitInput("Target").
            updateButton(() -> {
                int parsedFacesInput = Integer.parseInt(editDieSystem.getFacesInput());
                int parsedTargetInput = Integer.parseInt(editDieSystem.getLimitInput());

                //this order call matters, limit range needs to be validated before setting faces
                String errorMessage = "target cannot be 0 or less or bigger than number of faces";
                setLimit(parsedTargetInput, (target) -> target <= 0 || target > parsedFacesInput, errorMessage); //condition to invalidate input, if target is 0 or less or bigger than number of dice faces
                setNumberOfFaces(parsedFacesInput);

                dieDisplay.getElement(DiePart.NAME).setText("d"+getNumberOfFaces()+" -> "+getLimit());
                dieDisplay.childrenChanged();
            });
    }

    @Override
    public void dispose() {
        getFuture().cancel(true);
    }
}
