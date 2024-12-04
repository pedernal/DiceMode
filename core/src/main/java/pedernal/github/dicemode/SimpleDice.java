/**Class extention of AbstractDice to make a simple dice that rolls once.
 * Roll implements asynchronity so the roll is done on a separate thread.
 * There will one thread at a time.*/

package pedernal.github.dicemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class SimpleDice extends AbstractDice {
    private CompletableFuture<String> future;
    private DiceDispalySystem diceDisplay;

    public SimpleDice(int numberOfFaces, Skin skin) {
        super(numberOfFaces, 1, new ArrayList<Integer>(), skin);

        future = CompletableFuture.supplyAsync(() -> {return "\0";});
        diceDisplay = new DiceDispalySystem("d"+numberOfFaces, skin, 130, 104);
        getMemory().add(numberOfFaces);
        setTotal(numberOfFaces);

        //setting up unique style from skin for the labels' elements (increasing font size on body Label)
        Label.LabelStyle labelStyle = new Label.LabelStyle(diceDisplay.getElement(DicePart.BODY).getStyle()); //clone LabelStyle
        labelStyle.font = skin.getFont("BigNotoMono");

        diceDisplay.getElement(DicePart.BODY).setStyle(labelStyle);
        diceDisplay.getElement(DicePart.BODY).setAlignment(Align.center);
        diceDisplay.getElements().get(DicePart.TOTAL).setVisible(false); //make the total part not show
        updateDiceDisplay(Integer.toString(numberOfFaces));

        setActor(diceDisplay);
    }

    @Override
    public void roll() {
        updateDiceDisplay("···"); //set display to "···" before running thread to update the dice

        future.cancel(true); //if not completed when roll again, method will cancel future

        future = CompletableFuture.supplyAsync(() -> { //create new future
            populateMemory();
            return Integer.toString(getTotal());
        });
        future.thenAccept((result) -> { //once future is completed, send to the thread that draws stuff what to do with/after future's result
            Gdx.app.postRunnable(() -> { updateDiceDisplay(result); });
        });
    }

    //Synchronized to assure thread safety
    @Override
    public synchronized void populateMemory() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        getMemory().set(0, getRandomNumber()); //this dice only hols one value in memory
        setTotal(getMemory().getFirst());
    }

    @Override
    public void updateFrom(EditDiceSystem editDiceSystem) {
        editDiceSystem.setUpFacesInput();
        editDiceSystem.setUpUpdateButton(() -> {
            int parsedInput = Integer.parseInt(editDiceSystem.getFacesInput());

            setNumberOfFaces(parsedInput);
            diceDisplay.getElement(DicePart.NAME).setText("d"+parsedInput);
            updateDiceDisplay(Integer.toString(parsedInput));
        });
    }

    /**Helper method to facilitate updating */
    private void updateDiceDisplay(String content) {
        diceDisplay.update(content, "");
    }

    @Override
    public void dispose() {
        future.cancel(true);
    }
}
