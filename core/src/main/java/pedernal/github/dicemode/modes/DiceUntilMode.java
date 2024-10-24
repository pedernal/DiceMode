package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import pedernal.github.dicemode.DiceUntil;

public class DiceUntilMode extends AbstractMode {
    private DiceUntil diceUntil;

    public DiceUntilMode(Game mainProgram) {
        super(mainProgram);

        diceUntil = new DiceUntil(6, 1, 0, 0, this.getStage().getViewport().getScreenWidth()/7, this.getStage().getViewport().getScreenHeight()/7);
        diceUntil.setBounds(0, 0, 50, 10);
        diceUntil.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                diceUntil.roll(DiceUntilMode.super.getStage().getBatch());
            }
        });
        super.getTable().add(diceUntil);
    }
}
