package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.LinkedList;

public class DiceUntil extends AbstractDice{
    public DiceUntil(int faces, int target) {
        super(faces, Math.min(Math.abs(target), faces), new LinkedList<Integer>());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getFont().draw(batch, toString(), getX()-25, getY()+100);
    }

    @Override
    public void roll() {
        setTotal(0);
        populateMemory();
    }

    @Override
    public void populateMemory() {
        getMemory().clear();
        int currentRoll = 0;
        do {
            currentRoll = getRandomNumber();
            getMemory().add(currentRoll);
            setTotal(getTotal()+currentRoll);
        } while (currentRoll != getLimit());
    }
}
