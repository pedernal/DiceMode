/**Mode for a die that can be rolled a specific number of times and adds each roll into a total*/

package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.DieLoop;
import pedernal.github.dicemode.Main.MainProgramInterface;
import pedernal.github.dicemode.utilities.MainMemory;
import pedernal.github.dicemode.utilities.Modes;

import java.util.LinkedList;

public class DieLoopMode extends Mode {
    private DieLoop die;

    public DieLoopMode(MainProgramInterface mainProgram) {
        super(mainProgram);

        //Create die with data from memory else default
        LinkedList<MainMemory.DieConfig> dieConfigs = MainMemory.INSTANCE.getDieConfigs(Modes.DIE_LOOP_MODE);
        die = (!dieConfigs.isEmpty())?
            new DieLoop(dieConfigs.getFirst().numberOfFaces, dieConfigs.getFirst().limit, getSkin())
            :
            new DieLoop(6, 4, getSkin());
    }

    @Override
    public void show() {
        super.show();
        setRollButton(die::roll);
        getEditDieSystem().select(die);
        setupTableLayout(() -> getTable().add(die));

        getModeChanger().setLeftButtonInput(() -> {
            saveToMemory();
            getMainProgram().switchScreen( new SimpleDieMode(getMainProgram()) );
            }).setLeftButtonText("<- Simple Die").
        setRightButtonInput(() -> {
            saveToMemory();
            getMainProgram().switchScreen( new DieUntilMode(getMainProgram()) );
        }).setRightButtonText("Until Mode ->");
        getModeChanger().setHeaderText("Die Loop Mode");
    }

    /**Helper method to save die data to main memory*/
    private void saveToMemory() {
        LinkedList<MainMemory.DieConfig> dieConfigs = MainMemory.INSTANCE.getDieConfigs(Modes.DIE_LOOP_MODE);
        if (dieConfigs.isEmpty()) {
            dieConfigs.add(new MainMemory.DieConfig(die.getNumberOfFaces(), die.getLimit()));
        } else {
            dieConfigs.getFirst().numberOfFaces = die.getNumberOfFaces();
            dieConfigs.getFirst().limit = die.getLimit();
        }
        Gdx.app.log("DieLoopMode state", dieConfigs.toString());
    }

    @Override
    public void dispose() {
        super.dispose();
        die.dispose();
    }
}
