package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*FIXME: this should not be an actor nor disposable!*/

public class DiceLoop extends Actor implements Disposable {
    private Dice dice;
    private int rolls;
    private List<Integer> memory;
    private int total;
    private BitmapFont font;

    public DiceLoop(int faces, int rolls) {
        this.dice = new Dice(faces);
        this.rolls = rolls;
        this.memory = new ArrayList<Integer>(rolls);
        this.total = 0;
        font = new BitmapFont();
    }

    public int roll() {
        memory.clear();
        total = 0;

        for (int i = 0; i < rolls; ++i) {
            memory.add(dice.roll());
            total += memory.getLast();
        }

        return total;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        AtomicInteger line = new AtomicInteger(Math.round( this.getY() )); //safer way to mutate/increment a variable/counter in a function call or thread
        font.draw(batch, "Total: "+ total, this.getX()-getScaleX(), this.getY()+this.getHeight());
        memory.forEach((element) -> {
            font.draw(batch, element.toString(), this.getX()-(getScaleX()*2), line.getAndSet(line.get()-11));
        });
    }

    public int getTotal() {
        return total;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
