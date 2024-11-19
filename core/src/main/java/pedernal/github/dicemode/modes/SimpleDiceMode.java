package pedernal.github.dicemode.modes;

import pedernal.github.dicemode.SimpleDice;
import pedernal.github.dicemode.Main.MainProgramInterface;

public class SimpleDiceMode extends Mode {
    private SimpleDice d6;

    public SimpleDiceMode(MainProgramInterface mainProgram) {
        super(mainProgram);

        d6 = new SimpleDice(6, getSkin());
        pressRollButton(d6::roll);

        getEditDiceSystem().select(d6);

        setupTableLayout(() -> getTable().add(d6));
    }
}
