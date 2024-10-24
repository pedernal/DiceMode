package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DiceLoop extends AbstractDice {
    private Dice dice;
    private int rolls;
    private List<Integer> memory;

    public DiceLoop(int faces, int rolls, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.dice = new Dice(faces);
        this.rolls = rolls;
        this.memory = new ArrayList<Integer>(rolls);
    }

    @Override
    public void roll(Batch batch) {
        populateMemory();
        super.setTextureRegion(generateTextureRegion(batch));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(super.getTextureRegion(), super.getX(), super.getY());
    }

    private void populateMemory()
    {
        memory.clear();
        super.setTotal(0);
        for (int i = 0; i < rolls; ++i) {
            memory.add(dice.roll());
            addToTotal(memory.getLast());
        }
    }

    private void addToTotal(int rollToAdd) {
        super.setTotal(super.getTotal()+rollToAdd);
    }

    private TextureRegion generateTextureRegion(Batch batch) {
        return super.getResterizer().resterize(() -> {
            batch.begin();

            AtomicInteger line = new AtomicInteger(Math.round( super.getY()-12 ));
            dice.getFont().draw( batch, "Total: "+super.getTotal(), super.getX(), super.getY() );
            memory.forEach((element) -> {
                dice.getFont().draw(
                    batch,
                    element.toString(),
                    super.getX(),
                    line.getAndSet(line.get()-12)
                );
            });

            batch.end();
        });
    }

    @Override
    public void dispose() {
        dice.getFont().dispose();
    }
}
