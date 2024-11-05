package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import pedernal.github.dicemode.SimpleDice;

public class SimpleDiceMode extends Mode {
    private Game mainProgram;
    private SimpleDice d6;

    public SimpleDiceMode(Game mainProgram) {
        super(mainProgram);

        d6 = new SimpleDice(20, getSkin());
        d6.setFont(getFont());
        super.getTable().add(d6);
        pressRollButton(d6::roll);
    }
}
