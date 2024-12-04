/**All Dice types extend from this abstract class. It extends LibGDX's Container so it can be added to a Table as a widget.
 * The VerticalGroup is intended to be an instance of DiceDisplaySystem.
 * Implements randomness, a List to remember rolls (for iterative rolls) and a total from all rolls.**/

package pedernal.github.dicemode;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import pedernal.github.dicemode.utilities.EditDiceSystem;
import pedernal.github.dicemode.utilities.TriPredicate;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public abstract class AbstractDice extends Container<VerticalGroup> implements Disposable {
    private int numberOfFaces;
    private List<Integer> memory;
    private int total;
    private int limit;
    private Random randomGenerator = new Random();
    private Skin skin;
    private TriPredicate<Integer, Integer, Integer> predicate = (Integer i, Integer size, Integer element) -> true; //predicate lambda exp for the default behavior of formatMemoryString()

    public AbstractDice(int numberOfFaces, int limit, List<Integer> memory, Skin skin) {
        super();
        this.numberOfFaces = numberOfFaces;
        total = 0;
        this.limit = limit;
        this.skin = skin;
        this.memory = memory;
    }

    /**Method to implement what happens whn child Dice is rolled.*/
    public abstract void roll();

    /**Method to implement how memory List will be populated, intended to be called in roll().*/
    public abstract void populateMemory();

    /**Method to implement how editDiceSystem will modify the child instance. Method is called by EditDiceSystem.select(), so not intended to be called directly, although it's possible to select an instance that way.
     * @param editDiceSystem EditDiceSystem object that will select a child instance and edit its values.*/
    public abstract void updateFrom(EditDiceSystem editDiceSystem);

    public int getTotal() {
        return total;
    }

    /**Set the total after dice roll(s).*/
    public void setTotal(int total) {
        this.total = total;
    }

    /**@return random number for a roll.*/
    public int getRandomNumber() {
        return randomGenerator.nextInt(numberOfFaces)+1;
    }

    /**@return the List containing the result of the roll(s).*/
    public List<Integer> getMemory() {
        return memory;
    }

    /**@return skin used by the dice for UI.*/
    public Skin getSkin() {
        return skin;
    }

    /**@return the number of faces of the dice.*/
    public int getNumberOfFaces() {
        return numberOfFaces;
    }

    /**Sets the number of faces for the dice.
     * Throws IllegalArgumentException if argument is 0 or less.
     * @param numberOfFaces number of faces for the dice.*/
    public void setNumberOfFaces(int numberOfFaces) throws IllegalArgumentException {
        if (numberOfFaces <= 0) {
            throw new IllegalArgumentException("A dice cannot have 0 or less faces");
        }
        this.numberOfFaces = numberOfFaces;
    }

    /**Sets the limit for iterative rolls. It is left to the users to implement validation through the predicate lambda.
     * Throws IllegalArgumentException if implemented evaluation fails for acceptable limit ranges
     * @param limit value to set new limit to.
     * @param predicate predicate lambda exception that returns a boolean for limit evaluation. Takes the limit parameter to evaluate acceptable ranges.*/
    public void setLimit(int limit, Predicate<Integer> predicate) throws IllegalArgumentException {
        if (predicate.test(limit)) {
            throw new IllegalArgumentException(limit + " is out of range of possible rolls");
        }

        this.limit = limit;
    }
    public void setLimit(int limit) throws IllegalArgumentException {
        setLimit(limit, (passedLimit) -> passedLimit <= 0);
    }
     /**@return the limit an iterative dice will iterate to.*/
    public int getLimit() {
        return limit;
    }

    /**Adds value to the total.
     * @param value to add to total.*/
    public void addToTotal(int value) {
        total += value;
    }

    /**Outputs string with formated total.*/
    public String formatTotalString() {
        return "Total: " + total;
    }

    /**Outputs string with formated memory, each number element on a line, lines spaced at same distance, and a relevant symbol at the end of each line. It's up to the user to make sure formating indicates populateMemory() behavior.
     * @param formatSpaces the max amount of blank spaces before each number, meant to aid magnitude alignment with each line.
     * @param condition a TriPredicate lambda that will implement determination of whether symbol should be appended at the end fo each line. Takes three Integers; current memory index, memory size and current memory element to help the implementation.
     * @param symbol the single character to be appended at the end of each line.*/
    public String formatMemoryString(int formatSpaces, TriPredicate<Integer, Integer, Integer> condition, char symbol) {
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

    /**Method calls both formatTotalString() and formatMemoryString(), thus the parameters, adds a line to separate total from memory and outputs as a single string.
     * @param condition a TriPredicate lambda expression that will implement determination of whether symbol should be appended at the end fo each line. Takes three Integers; current memory index, memory size and current memory element to help the implementation.
     * @param symbol the single character to be appended at the end of each memory line.*/
    public String formatStringAll(TriPredicate<Integer, Integer, Integer> condition, char symbol) {
        String toReturn = "";

        if (memory.isEmpty()) {
            toReturn = "       "+total+symbol+'\n' +
                       "       =\n" +
                       "Total: " + total;
        } else {
            int totalLength = Integer.toString(total).length();
            String formatedTotal = formatTotalString();
            String formatedMemory = formatMemoryString(formatedTotal.length(), condition, symbol);
            StringBuilder line = new StringBuilder("="); //line to divide memory and total
            for (int i = 1; i < totalLength; ++i) {
                line.append("=");
            }

            toReturn = String.format("%s%"+formatedTotal.length()+"s\n", formatedMemory, line);
            toReturn += formatedTotal;
        }

        return toReturn;
    }

    /**Method for things to dispose of*/
    @Override
    public void dispose() {}
}
