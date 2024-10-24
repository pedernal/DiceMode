package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class DiceUntil extends AbstractDice {
    private int target;
    private Dice dice;
    private LinkedList<Integer> memory;

    public DiceUntil(int faces, int target, float x, float y, int width, int height) {
        super(x, y, width, height);
        this.target = Math.min(Math.abs(target), faces);
        dice = new Dice(faces);
        memory = new LinkedList<Integer>();
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

    private void populateMemory() {
        memory.clear();
        super.setTotal(0);

        int currentRoll = 0;
        while (currentRoll != target) {
            currentRoll = dice.roll();
            memory.add(currentRoll);
            addToTotal(currentRoll);
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
        super.getTextureRegion().getTexture().dispose();
    }
}
