package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import pedernal.github.dicemode.SimpleDice;

public class SimpleDiceMode extends Mode {
    private Game mainProgram;
    private SimpleDice d6;

    public SimpleDiceMode(Game mainProgram) {
        super(mainProgram);

        d6 = new SimpleDice(6, getSkin());
        d6.setFont(getFont());
        getTable().add(d6);
        pressRollButton(d6::roll);

        getTable().row();

        getTable().add(getRollButton()).width(100).height(50);
    }
}
