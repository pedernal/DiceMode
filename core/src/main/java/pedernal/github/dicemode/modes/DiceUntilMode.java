package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import pedernal.github.dicemode.DiceUntil;

public class DiceUntilMode extends AbstractMode {
    private DiceUntil diceUntil;

    public DiceUntilMode(Game mainProgram) {
        super(mainProgram);

        Viewport parentViewport = super.getStage().getViewport();
        diceUntil = new DiceUntil(6, 1, 0, 0, parentViewport.getScreenWidth()/7, parentViewport.getScreenHeight()/7);
        diceUntil.setBounds(0, 0, 50, 50);
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
