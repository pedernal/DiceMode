package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.TriPredicate;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public abstract class AbstractDice extends Container<VerticalGroup> {
    private int numberOfFaces;
    private List<Integer> memory;
    private int total;
    private int limit;
    private Random randomGenerator = new Random();
    private Skin skin;
    private TriPredicate<Integer, Integer, Integer> predicate = (Integer i, Integer size, Integer element) -> true;

    public AbstractDice(int numberOfFaces, int limit, List<Integer> memory, Skin skin) {
        super();
        this.numberOfFaces = numberOfFaces;
        total = 0;
        this.limit = limit;
        this.skin = skin;
        this.memory = memory;
    }

    public abstract void roll();

    public abstract void populateMemory();

    public abstract void updateFrom(EditDiceSystem editDiceSystem);

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRandomNumber() {
        return randomGenerator.nextInt(numberOfFaces)+1;
    }

    public List<Integer> getMemory() {
        return memory;
    }

    public Skin getSkin() {
        return skin;
    }

    public int getNumberOfFaces() {
        return numberOfFaces;
    }

    public void setNumberOfFaces(int numberOfFaces) throws IllegalArgumentException {
        if (numberOfFaces <= 0) {
            throw new IllegalArgumentException("A dice cannot have 0 or less faces");
        }
        this.numberOfFaces = numberOfFaces;
    }

    public void setLimit(int limit, Predicate<Integer> predicate) throws IllegalArgumentException {
        if (predicate.test(limit)) {
            throw new IllegalArgumentException("Parameter out of logical range");
        }

        this.limit = limit;
    }
    public void setLimit(int limit) throws IllegalArgumentException {
        setLimit(limit, (passedLimit) -> passedLimit <= 0);
    }

    public int getLimit() {
        return limit;
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
