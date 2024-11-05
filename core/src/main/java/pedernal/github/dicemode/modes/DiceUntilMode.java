package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;
import pedernal.github.dicemode.DiceUntil;
import pedernal.github.dicemode.utilities.DicePart;

public class DiceUntilMode extends Mode {
    private DiceUntil dice;

    public DiceUntilMode(Game mainProgram) {
        super(mainProgram);

        Viewport parentViewport = super.getStage().getViewport();

        dice = new DiceUntil(6, 1, getSkin());
        dice.setFont(getFont());
        super.getTable().add(dice);
        pressRollButton(dice::roll);
    }
}
