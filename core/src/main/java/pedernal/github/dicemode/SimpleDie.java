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

public class SimpleDie extends AbstractDie {
    private CompletableFuture<String> future;
    private final DieDisplaySystem dieDisplay;

    public SimpleDie(int numberOfFaces, Skin skin) {
        super(numberOfFaces, 1, new ArrayList<Integer>(), skin);

        future = CompletableFuture.supplyAsync(() -> "\0");
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
    public void roll() {
        updateDieDisplay("···"); //set display to "···" before running thread to update the die

        future.cancel(true); //if not completed when roll again, method will cancel future

        future = CompletableFuture.supplyAsync(() -> { //create new future
            populateMemory();
            return Integer.toString(getTotal());
        });
        future.thenAccept((result) -> //once future is completed, send to the thread that draws stuff what to do with/after future's result
            Gdx.app.postRunnable(() -> updateDieDisplay(result))
        );
    }

    //Synchronized to assure thread safety
    @Override
    public synchronized void populateMemory() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        getMemory().set(0, getRandomNumber()); //this die only hols one value in memory
        setTotal(getMemory().getFirst());
    }

    @Override
    public void updateFrom(EditDieSystem editDieSystem) {
        editDieSystem.setUpFacesInput();
        editDieSystem.setUpUpdateButton(() -> {
            int parsedInput = Integer.parseInt(editDieSystem.getFacesInput());

            setNumberOfFaces(parsedInput);

            dieDisplay.getElement(DiePart.NAME).setText("d"+parsedInput);
            updateDieDisplay(Integer.toString(parsedInput));
        });
    }

    /**Helper method to facilitate updating */
    private void updateDieDisplay(String content) {
        dieDisplay.update(content, "");
    }

    @Override
    public void dispose() {
        future.cancel(true);
    }
}
