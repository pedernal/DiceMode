/**Mode that runs a simple die to roll, number of faces can be set*/

package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.SimpleDie;
import pedernal.github.dicemode.Main.MainProgramInterface;
import pedernal.github.dicemode.utilities.MainMemory;
import pedernal.github.dicemode.utilities.MainMemory.DieConfig;
import pedernal.github.dicemode.utilities.Modes;

import java.util.LinkedList;

public class SimpleDieMode extends Mode {
    private SimpleDie die;

    public SimpleDieMode(MainProgramInterface mainProgram) {
        super(mainProgram);

        //Create die with data from memory else default
        LinkedList<DieConfig> dieConfigs = MainMemory.INSTANCE.getDieConfigs(Modes.SIMPLE_DIE_MODE);
        die = (!dieConfigs.isEmpty())?
            new SimpleDie(dieConfigs.getFirst().numberOfFaces, getSkin())
            :
            new SimpleDie(6, getSkin());
    }

    @Override
    public void show() {
        super.show();
        setRollButton(die::roll);
        getEditDieSystem().select(die);
        setupTableLayout(() -> getTable().add(die));

        getModeChanger().setLeftButtonInput(() -> {
            saveToMemory();
            getMainProgram().switchScreen( new DieUntilMode(getMainProgram()) );
        }).setLeftButtonText("<- Until Mode").
        setRightButtonInput(() -> {
            saveToMemory();
            getMainProgram().switchScreen( new DieLoopMode(getMainProgram()) );
        }).setRightButtonText("Loop Mode ->");
        getModeChanger().setHeaderText("Simple Die Mode");
    }

    /**Helper method to save dice data to main memory*/
    private void saveToMemory() {
        LinkedList<DieConfig> dieConfigs = MainMemory.INSTANCE.getDieConfigs(Modes.SIMPLE_DIE_MODE);
        if (dieConfigs.isEmpty()){
            dieConfigs.add(new MainMemory.DieConfig(die.getNumberOfFaces()));
        } else {
            dieConfigs.getFirst().numberOfFaces = die.getNumberOfFaces();
        }
        Gdx.app.log("SimpleDieMode state", dieConfigs.toString());

    }

    @Override
    public void dispose() {
        super.dispose();
        die.dispose();
    }
}
