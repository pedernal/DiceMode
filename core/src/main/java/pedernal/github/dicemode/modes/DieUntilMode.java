/**Mode for a die that can be rolled until a specific roll is hit and adds each roll to a total*/

package pedernal.github.dicemode.modes;

import com.badlogic.gdx.Gdx;
import pedernal.github.dicemode.DieUntil;
import pedernal.github.dicemode.Main.MainProgramInterface;
import pedernal.github.dicemode.utilities.MainMemory;
import pedernal.github.dicemode.utilities.MainMemory.DieConfig;
import pedernal.github.dicemode.utilities.Modes;

import java.util.LinkedList;

public class DieUntilMode extends Mode {
    private DieUntil die;

    public DieUntilMode(MainProgramInterface mainProgram) {
        super(mainProgram);

        //Create die with data from memory else default
        LinkedList<DieConfig> dieConfigs = MainMemory.INSTANCE.getDieConfigs(Modes.DIE_UNTIL_MODE);
        die = (!dieConfigs.isEmpty())?
            new DieUntil(dieConfigs.getFirst().numberOfFaces, dieConfigs.getFirst().limit, getSkin())
            :
            new DieUntil(6, 1, getSkin());
    }

    @Override
    public void show() {
        super.show();
        setRollButton(die::roll);
        getEditDieSystem().select(die);
        setupTableLayout(() -> getTable().add(die));

        getModeChanger().
        setLeftButtonInput(() -> {
            saveToMemory();
            getMainProgram().switchScreen( new DieLoopMode(getMainProgram()) );
        }).setLeftButtonText("<- Loop Mode").
        setRightButtonInput(() -> {
            saveToMemory();
            getMainProgram().switchScreen( new SimpleDieMode(getMainProgram()) );
        }).setRightButtonText("Simple Die ->");
        getModeChanger().setHeaderText("Die Until Mode");
    }

    /**Helper method to save die data to main memory*/
    private void saveToMemory() {
        LinkedList<DieConfig> dieConfigs = MainMemory.INSTANCE.getDieConfigs(Modes.DIE_UNTIL_MODE);
        if (dieConfigs.isEmpty()) {
            dieConfigs.add(new MainMemory.DieConfig(die.getNumberOfFaces(), die.getLimit()));
        } else {
            dieConfigs.getFirst().numberOfFaces = die.getNumberOfFaces();
            dieConfigs.getFirst().limit = die.getLimit();
        }
        Gdx.app.log("DieUntilMode state", dieConfigs.toString());
    }

    @Override
    public void dispose() {
        super.dispose();
        die.dispose();
    }
}

