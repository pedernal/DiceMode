package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayList;

public class DiceLoop extends AbstractDice{
    public DiceLoop(int faces, int rolls) {
        super(faces, Math.min(Math.abs(rolls), faces), new ArrayList<Integer>(rolls));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getFont().draw(batch, toString(), getX()-25, getY()+50);
    }

    @Override
    public void roll() {
        setTotal(0);
        populateMemory();
    }

    @Override
    public void populateMemory() {
        getMemory().clear();
        for (int i = 0; i < getLimit(); ++i) {
            int randomNum = getRandomNumber();
            getMemory().add(randomNum);
            addToTotal(getMemory().getLast());
        }
    }
}
