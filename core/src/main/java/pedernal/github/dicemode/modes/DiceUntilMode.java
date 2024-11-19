package pedernal.github.dicemode.modes;

import pedernal.github.dicemode.DiceUntil;
import pedernal.github.dicemode.Main.MainProgramInterface;

public class DiceUntilMode extends Mode {
    private DiceUntil dice;

    public DiceUntilMode(MainProgramInterface mainProgram) {
        super(mainProgram);

        dice = new DiceUntil(6, 1, getSkin());
        pressRollButton(dice::roll);

        getEditDiceSystem().select(dice);

        setupTableLayout(() -> getTable().add(dice));
    }
}
