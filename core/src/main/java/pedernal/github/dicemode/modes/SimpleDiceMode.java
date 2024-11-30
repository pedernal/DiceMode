package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.SimpleDice;
import pedernal.github.dicemode.Main.MainProgramInterface;

public class SimpleDiceMode extends Mode {
    private SimpleDice d6;

    public SimpleDiceMode(MainProgramInterface mainProgram) {
        super(mainProgram);
        d6 = new SimpleDice(6, getSkin());
    }

    @Override
    public void show() {
        super.show();
        getModeChanger().setUpButtons(() -> {
                getMainProgram().switchScreen( new DiceUntilMode(getMainProgram()) );
        },
        () -> {
            getMainProgram().switchScreen( new DiceLoopMode(getMainProgram()) );
        },
            "<- Until Mode", "Loop Mode ->"
        );
        pressRollButton(d6::roll);
        getEditDiceSystem().select(d6);
        setupTableLayout(() -> getTable().add(d6));
    }
}
