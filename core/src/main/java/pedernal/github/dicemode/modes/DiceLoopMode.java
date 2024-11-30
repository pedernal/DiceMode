package pedernal.github.dicemode.modes;

import pedernal.github.dicemode.DiceLoop;
import pedernal.github.dicemode.Main.MainProgramInterface;

public class DiceLoopMode extends Mode {
    private DiceLoop dice;

    public DiceLoopMode(MainProgramInterface mainProgram) {
        super(mainProgram);
        dice = new DiceLoop(6, 4, getSkin());
    }

    @Override
    public void show() {
        super.show();
        getModeChanger().setUpButtons(() -> {
                getMainProgram().switchScreen( new SimpleDiceMode(getMainProgram()) );
            },
            () -> {
                getMainProgram().switchScreen( new DiceUntilMode(getMainProgram()) );
            },
            "<- Simple Dice", "Until Mode ->"
        );
        pressRollButton(dice::roll);
        getEditDiceSystem().select(dice);
        setupTableLayout(() -> getTable().add(dice));
    }
}
