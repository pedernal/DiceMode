package pedernal.github.dicemode.modes;

import pedernal.github.dicemode.SimpleDice;
import pedernal.github.dicemode.Main.MainProgramInterface;

public class SimpleDiceMode extends Mode {
    private SimpleDice dice;

    public SimpleDiceMode(MainProgramInterface mainProgram) {
        super(mainProgram);
        dice = new SimpleDice(6, getSkin());
    }

    @Override
    public void show() {
        super.show();
        setRollButton(dice::roll);
        getEditDiceSystem().select(dice);
        setupTableLayout(() -> getTable().add(dice));

        getModeChanger().setLeftButtonInput(() -> {
            getMainProgram().switchScreen( new DiceUntilMode(getMainProgram()) );
        }).setLeftButtonText("<- Until Mode").
        setRightButtonInput(() -> {
            getMainProgram().switchScreen( new DiceLoopMode(getMainProgram()) );
        }).setRightButtonText("Loop Mode ->");
        getModeChanger().setHeaderText("Simple Dice Mode");
    }

    @Override
    public void dispose() {
        super.dispose();
        dice.dispose();
    }
}
