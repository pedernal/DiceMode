package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import pedernal.github.dicemode.Dice;

public class DiceMode extends AbstractMode {
    private Game mainProgram;
    private Dice d6;

    public DiceMode(Game mainProgram) {
        super(mainProgram);

        d6 = new Dice(6);
        d6.setBounds(0, 0, 10, 10);
        d6.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { d6.roll(); }
        });
        super.getTable().add(d6);
    }
}
