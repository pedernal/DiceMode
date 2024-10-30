package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayList;

public class SimpleDice extends AbstractDice {

    public SimpleDice(int numberOfFaces) {
        super(numberOfFaces, 1, new ArrayList<Integer>());
        getMemory().add(numberOfFaces);
        setTotal(numberOfFaces);
    }

    @Override
    public void roll() {
        populateMemory();
        setTotal(getMemory().getFirst());
    }

    @Override
    public void populateMemory() {
        getMemory().set(0, getRandomNumber());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getFont().draw(batch, Integer.toString(getTotal()), getX(), getY());
    }

    @Override
    public String toString()
    {
        return formatTotalString();
    }
}
