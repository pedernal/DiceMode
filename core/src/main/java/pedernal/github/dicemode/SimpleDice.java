package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import pedernal.github.dicemode.utilities.DiceDispalySystem;
import pedernal.github.dicemode.utilities.DicePart;
import pedernal.github.dicemode.utilities.EditDiceSystem;

import java.util.ArrayList;

public class SimpleDice extends AbstractDice {
    private DiceDispalySystem diceDisplay;

    public SimpleDice(int numberOfFaces, Skin skin) {
        super(numberOfFaces, 1, new ArrayList<Integer>(), skin);
        getMemory().add(numberOfFaces);
        setTotal(numberOfFaces);
        diceDisplay = new DiceDispalySystem("d"+numberOfFaces, skin, 130, 104);

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
        populateMemory();
        setTotal(getMemory().getFirst());

        updateDiceDisplay( Integer.toString(getTotal()) );
    }

    @Override
    public void populateMemory() {
        getMemory().set(0, getRandomNumber()); //this dice only hols one value in memory
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

    private void updateDiceDisplay(String content) {
        //somewhat centering the numbers using String.format()
        int spaces = ((int) diceDisplay.getElements().get(DicePart.BODY).getPrefWidth())/40; //div container with by font size
        //diceDisplay.update(String.format("%"+spaces+"s", content), content); //second arg is for total but won't be shown so it no matter
        diceDisplay.update(content, "");
    }
}
