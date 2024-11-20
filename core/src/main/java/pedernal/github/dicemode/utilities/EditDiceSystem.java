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

    public void select(AbstractDice dice) {
        selected = dice;
        dice.updateFrom(this);

        subTalbe.setVisible(true);
    }

    public void unselect() {
        updateButton.clearListeners();
        subTalbe.setVisible(false);
    }

    public void setUpFacesInput() {
        subTalbe.add(facesLabel).height(30).space(10);
        subTalbe.add(facesInput).width(100).space(10);
        subTalbe.row();
    }

    public void setUpLimitInput(String str) {
        limitLabel.setText(str);
        subTalbe.add(limitLabel).height(30).space(10);
        subTalbe.add(limitInput).width(100).space(10);
        subTalbe.row();
    }
    public void setUpLimitInput() { setUpLimitInput("Limit"); }

    public void setUpUpdateButton(Runnable validate, Runnable handeling) {
        updateButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    validate.run();
                } catch (Exception exception) {
                    Gdx.app.error("Input error", exception.getClass().getName() + "; " + exception.getMessage());
                    handeling.run();
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

    public void highlight() {
        if (selected != null) {
            Drawable background = selected.getSkin().newDrawable("color", Color.CYAN);
            selected.setBackground(background);
        }
    }

    public void unhilgiht() {
        if (selected != null) {
            selected.setBackground(null);
        }
    }

    public void setSelected(AbstractDice selected) {
        this.selected = selected;
    }

    public AbstractDice getSelected() {
        return selected;
    }

    public Table getTable() {
        return subTalbe;
    }

    public String getFacesInput() {
        return facesInput.getText();
    }

    public String getLimitInput() {
        return limitInput.getText();
    }

    public TextButton getUpdateButton() {
        return updateButton;
    }
}
