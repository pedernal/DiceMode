package pedernal.github.dicemode.modes;

import pedernal.github.dicemode.DiceUntil;
import pedernal.github.dicemode.Main.MainProgramInterface;

public class DiceUntilMode extends Mode {
    private DiceUntil dice;

    public DiceUntilMode(MainProgramInterface mainProgram) {
        super(mainProgram);
        dice = new DiceUntil(6, 1, getSkin());
    }

    @Override
    public void show() {
        super.show();
        setRollButton(dice::roll);
        getEditDiceSystem().select(dice);
        setupTableLayout(() -> getTable().add(dice));

        getModeChanger().
        setLeftButtonInput(() -> {
            getMainProgram().switchScreen( new DiceLoopMode(getMainProgram()) );
        }).setLeftButtonText("<- Loop Mode").
        setRightButtonInput(() -> {
            getMainProgram().switchScreen( new SimpleDiceMode(getMainProgram()) );
        }).setRightButtonText("Simple Dice ->");
        getModeChanger().setHeaderText("Dice Until Mode");
    }

    @Override
    public void dispose() {
        super.dispose();
        dice.dispose();
    }
}

