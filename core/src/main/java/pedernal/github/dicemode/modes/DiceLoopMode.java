package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import pedernal.github.dicemode.DiceLoop;

public class DiceLoopMode extends Mode {
    private DiceLoop dice;

    public DiceLoopMode(Game mainProgram) {
        super(mainProgram);

        dice = new DiceLoop(10, 6, getSkin());
        dice.setFont(getFont());
        getTable().add(dice);

        pressRollButton(dice::roll);
    }
}
