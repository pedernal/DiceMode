package pedernal.github.dicemode;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import pedernal.github.dicemode.utilities.TriPredicate;
import java.util.List;
import java.util.Random;

public abstract class AbstractDice extends Container<VerticalGroup> {
    private int numberOfFaces;
    private List<Integer> memory;
    private int total;
    private int limit;
    private Random randomGenerator = new Random();
    private BitmapFont font;
    private Skin skin;
    private TriPredicate<Integer, Integer, Integer> predicate = (Integer i, Integer size, Integer element) -> true;

    public AbstractDice(int numberOfFaces, int limit, List<Integer> memory, Skin skin) {
        this.numberOfFaces = numberOfFaces;
        total = 0;
        this.limit = limit;
        font = new BitmapFont();
        this.skin = skin;
        this.memory = memory;
    }

    public abstract void roll();

    public abstract void populateMemory();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRandomNumber() {
        return randomGenerator.nextInt(numberOfFaces)+1;
    }

    public BitmapFont getFont() {
        return this.font;
    }

    public void setFont(BitmapFont font) {
        this.font.dispose();
        this.font = font;
    }

    public List<Integer> getMemory() {
        return memory;
    }

    public int getLimit() {
        return limit;
    }

    public Skin getSkin() {
        return skin;
    }

    public void addToTotal(int value) {
        total += value;
    }

    public String formatTotalString() {
        return "Total: " + total;
    }

    public String formatMemoryString(int formatSpaces, TriPredicate condition, char symbol) {
        String toReturn = "\n";

        int size = memory.size();
        for (int i = 0; i < size; ++i) {
            int element = memory.get(i);
            char determinedSymbol = ( condition.test(i, size, element) )? symbol : ' ';
            toReturn += String.format("%"+formatSpaces+"d%c\n", memory.get(i), determinedSymbol);
        }

        return toReturn;
    }
    public String formatMemoryString() {
        return formatMemoryString(formatTotalString().length(), predicate, '+');
    }

    public String formatStringAll(TriPredicate condition, char symbol) {
        String toReturn = "";

        if (memory.isEmpty()) {
            toReturn = "       "+total+"+\n" +
                       "       =\n" +
                       "Total: " + total;
        } else {
            int totalLength = Integer.toString(total).length();
            String formatedTotal = formatTotalString();
            String formatedMemory = formatMemoryString(formatedTotal.length(), condition, symbol);
            String line = "=";
            for (int i = 1; i < totalLength; ++i) {
                line += "=";
            }

            toReturn = String.format("%s%"+formatedTotal.length()+"s\n", formatedMemory, line);
            toReturn += formatedTotal;
        }

        return toReturn;
    }

    @Override
    public String toString() {
        return formatStringAll(predicate, '+');
    }
}
