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
        setRollButton(dice::roll);
        getEditDiceSystem().select(dice);
        setupTableLayout(() -> getTable().add(dice));

        getModeChanger().setLeftButtonInput(() -> {
            getMainProgram().switchScreen( new SimpleDiceMode(getMainProgram()) );
        }).setLeftButtonText("<- Simple Dice").
        setRightButtonInput(() -> {
            getMainProgram().switchScreen( new DiceUntilMode(getMainProgram()) );
        }).setRightButtonText("Until Mode ->");
        getModeChanger().setHeaderText("Dice Loop Mode");
    }

    @Override
    public void dispose() {
        super.dispose();
        dice.dispose();
    }
}
