/**System to select and edit properties of any given object that extends AbstractDice (a dice).*/

package pedernal.github.dicemode.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import pedernal.github.dicemode.AbstractDice;

public class EditDiceSystem {
    private AbstractDice selected;
    private Table subTalbe;
    private Label facesLabel, limitLabel;
    private TextField facesInput, limitInput;
    private TextButton updateButton;

    public EditDiceSystem(Skin skin) {
        selected = null;
        subTalbe = new Table();
        subTalbe.setVisible(false);

        facesLabel = new Label("Faces", skin);
        facesInput = new TextField("", skin);
        limitLabel = new Label("Limit", skin);
        limitInput = new TextField("", skin);
        updateButton = new TextButton("Update", skin);
    }

    /**Selects a dice object, passes this instance to the dice and sets menu to edit dice visible.
     * @param dice The instance of a dice to be selected by the system.*/
    public void select(AbstractDice dice) {
        selected = dice;
        dice.updateFrom(this);
        subTalbe.setVisible(true);
    }

    /**Deselects the dice, clears system buttons' listeners.*/
    public void deselect() {
        subTalbe.setVisible(false);
        updateButton.clearListeners();
        selected = null;
    }

    /**Sets up input UI field for new faces of selected dice.*/
    public void setUpFacesInput() {
        subTalbe.add(facesLabel).height(30).space(10);
        subTalbe.add(facesInput).width(100).space(10);
        subTalbe.row();
    }

    /**Sets up input UI field for new limit for selected dice.
     * @param str text to show on the label of field.*/
    public void setUpLimitInput(String str) {
        limitLabel.setText(str);
        subTalbe.add(limitLabel).height(30).space(10);
        subTalbe.add(limitInput).width(100).space(10);
        subTalbe.row();
    }
    //public void setUpLimitInput() { setUpLimitInput("Limit"); }

    /**Sets up button's input listener to update the selected dice.
     * @param validate runnable lambda expression that should implement how the input will be validated.
     * @param handling runnable lambda expression that should implement what to do if validation fails.*/
    public void setUpUpdateButton(Runnable validate, Runnable handling) {
        updateButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    validate.run();
                } catch (Exception exception) {
                    Gdx.app.error("Input error", exception.getClass().getSimpleName() + "; " + exception.getMessage());
                    handling.run();
                }
            }
        });

        subTalbe.add(updateButton).colspan(2).spaceTop(10);
    }
    public void setUpUpdateButton(Runnable validate){
        setUpUpdateButton(validate, () -> {
            Gdx.app.log("State", "Some or all changes didn't apply");
        });
    }

    /**If a dice has been selected, visually highlights it.*/
    public void highlight() {
        if (selected != null) {
            Drawable background = selected.getSkin().newDrawable("color", Color.CYAN);
            selected.setBackground(background);
            selected.getActor().pad(2);
        }
    }

    /**If a dice has been selected, removes the visual highlight from selected dice.*/
    public void unhilgiht() {
        if (selected != null) {
            selected.setBackground(null);
            selected.getActor().pad(0);
        }
    }

    /*public void setSelected(AbstractDice selected) {
        this.selected = selected;
    }

    public AbstractDice getSelected() {
        return selected;
    }*/

     /**@return the table that makes the UI layout of the system.*/
    public Table getTable() {
        return subTalbe;
    }

    /**@return the input stored on the field for dice faces.*/
    public String getFacesInput() {
        return facesInput.getText();
    }

    /**@return the input stored on the field for dice limit.*/
    public String getLimitInput() {
        return limitInput.getText();
    }

    /*public TextButton getUpdateButton() {
        return updateButton;
    }*/
}
