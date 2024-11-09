package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;
import pedernal.github.dicemode.DiceUntil;

public class DiceUntilMode extends Mode {
    private DiceUntil dice;

    public DiceUntilMode(Game mainProgram) {
        super(mainProgram);

        Viewport parentViewport = super.getStage().getViewport();

        dice = new DiceUntil(6, 1, getSkin());
        dice.setFont(getFont());
        pressRollButton(dice::roll);

        getTable().add(dice).expandY();
        getTable().row();
        getTable().add(getRollButton()).expandY().width(100).height(50);
    }
}
