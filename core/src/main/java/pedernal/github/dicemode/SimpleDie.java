/**Class extension of AbstractDie to make a simple dice that rolls once.
 * Roll implements asynchronicity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import pedernal.github.dicemode.utilities.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("NewApi")
public class SimpleDie extends AbstractDie {
    private final DieDisplaySystem dieDisplay;

    public SimpleDie(int numberOfFaces, Skin skin) {
        super(numberOfFaces, 1, new ArrayList<Integer>(), skin);

        dieDisplay = new DieDisplaySystem("d"+numberOfFaces, skin, 130, 104);
        getMemory().add(numberOfFaces);
        setTotal(numberOfFaces);

        //setting up unique style from skin for the labels' elements (increasing font size on body Label)
        Label.LabelStyle labelStyle = new Label.LabelStyle(dieDisplay.getElement(DiePart.BODY).getStyle()); //clone LabelStyle
        labelStyle.font = skin.getFont("BigNotoMono");

        dieDisplay.getElement(DiePart.BODY).setStyle(labelStyle);
        dieDisplay.getElement(DiePart.BODY).setAlignment(Align.center);
        dieDisplay.getElements().get(DiePart.TOTAL).setVisible(false); //make the total part not show
        updateDieDisplay(Integer.toString(numberOfFaces));

        setActor(dieDisplay);
    }

    @Override
    public CompletableFuture<String[]> roll() throws ExecutionException, InterruptedException {
        updateDieDisplay("···"); //set display to "···" before running thread to update the die

        getFuture().cancel(true);

        setFuture(() -> {
            populateMemory();
            return new String[] {Integer.toString(getTotal())};
        });
        getFuture().thenAccept( (result) -> Gdx.app.postRunnable(() -> updateDieDisplay(result[0])) );

        return getFuture();
    }

    //Synchronized to assure thread safety
    @Override
    public synchronized void populateMemory() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Gdx.app.error("Thread error", e.getClass().getSimpleName()+"; "+e.getMessage());
        }
        getMemory().set(0, getRandomNumber()); //this die only hols one value in memory
        setTotal(getMemory().getFirst());
    }

    @Override
    public void updateFrom(EditDieSystem editDieSystem) {
        editDieSystem.UISetup().
            facesInput().
            updateButton(() -> {
            int parsedInput = Integer.parseInt(editDieSystem.getFacesInput());

            setNumberOfFaces(parsedInput);

            dieDisplay.getElement(DiePart.NAME).setText("d"+parsedInput);
            updateDieDisplay(Integer.toString(parsedInput));
        });
    }

    /**Helper method to facilitate updating die's display.*/
    private void updateDieDisplay(String content) {
        dieDisplay.update(content, "");
    }

    @Override
    public void dispose() {
        getFuture().cancel(true);
    }
}
