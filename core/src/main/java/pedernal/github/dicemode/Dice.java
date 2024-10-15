package pedernal.github.dicemode;

import java.util.Random;

public class Dice {
    private int numberOfFaces;
    private Random randomGenerator = new Random();

    public Dice() {
        this.numberOfFaces = 6;
    }
    public Dice(int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
    }

    public void setNumberOfFaces(int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
    }

    public int roll() {
        return randomGenerator.nextInt(numberOfFaces)+1;
    }
}
