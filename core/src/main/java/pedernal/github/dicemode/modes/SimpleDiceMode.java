package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import pedernal.github.dicemode.SimpleDice;

public class SimpleDiceMode extends Mode {
    private Game mainProgram;
    private SimpleDice d6;

    public SimpleDiceMode(Game mainProgram) {
        super(mainProgram);

        d6 = new SimpleDice(6);
        System.out.println(d6.toString());
        d6.setBounds(0, 0, 10, 10);
        d6.setFont(getFont());
        d6.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                d6.roll();
            }
        });
        super.getTable().add(d6);
    }
}
