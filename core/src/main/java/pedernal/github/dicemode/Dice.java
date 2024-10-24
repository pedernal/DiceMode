package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class Dice extends Actor implements Disposable {
    private int numberOfFaces;
    private int memory;
    private final Random randomGenerator = new Random();
    private BitmapFont font;


    public Dice(int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
        this.memory = numberOfFaces;
        this.font = new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, Integer.toString(getMemory()), this.getX()-font.getScaleX(), this.getY()+this.getHeight());
    }

    public int roll() {
        memory = randomGenerator.nextInt(numberOfFaces)+1;
        return memory;

    }

    public BitmapFont getFont() {
        return this.font;
    }

    public int getMemory() {
        return memory;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
