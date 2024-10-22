package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import pedernal.github.dicemode.DiceLoop;

public class DiceLoopMode extends Mode {
    private DiceLoop diceLoop;

    public DiceLoopMode(Game mainProgram) {
        super(mainProgram);

        diceLoop = new DiceLoop(6, 4);
        diceLoop.setBounds(0, 0, 10, 10);
        diceLoop.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { diceLoop.roll(); }

        });
        super.getTable().add(diceLoop);
    }

    @Override
    public void render(float v) {
        super.render(v);
    }
}
