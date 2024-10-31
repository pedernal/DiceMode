package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import pedernal.github.dicemode.DiceUntil;

public class DiceUntilMode extends Mode {
    private DiceUntil dice;

    public DiceUntilMode(Game mainProgram) {
        super(mainProgram);

        Viewport parentViewport = super.getStage().getViewport();

        dice = new DiceUntil(6, 1);
        dice.setBounds(0, 0, 50, 50);
        dice.setFont(getFont());
        super.getTable().add(dice);
        pressRollButton(dice::roll);
    }
}
