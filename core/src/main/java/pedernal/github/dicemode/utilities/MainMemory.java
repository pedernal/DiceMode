/**Singleton to 'remember' the configuration of die as Modes are changed and disposed*/

package pedernal.github.dicemode.utilities;

import java.util.EnumMap;
import java.util.LinkedList;

public enum MainMemory {
    INSTANCE(); //single instance of enum/class

    private final EnumMap<Modes, LinkedList<DieConfig>> modes;

    MainMemory() {
        modes = new EnumMap<Modes, LinkedList<DieConfig>>(Modes.class);
    }

    /**@return the single instance of MainMemory*/
    public static MainMemory getInstance() {
        return INSTANCE;
    }

    /**@param mode type of Mode according to enum Modes
     * @return the linked list of die configurations for a specific mode. Lazily initialized*/
    public LinkedList<DieConfig> getDieConfigs(Modes mode) {
        return modes.computeIfAbsent(mode, (Modes k) -> new LinkedList<DieConfig>()); //if hashmap doesn't have it, make it
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{\n");
        modes.forEach( (key, value) -> sb.append("\t").append(key).append(": ").append(value).append(",\n"));
        sb.append('}');

        return sb.toString();
    }

    /**Object that stores die data*/
    public static class DieConfig {
        public int numberOfFaces, limit;

        public DieConfig(int numberOfFaces, int limit) {
            this.numberOfFaces = numberOfFaces; this.limit = limit;
        }
        public DieConfig(int numberOfFaces) {
            this.numberOfFaces = numberOfFaces; limit = 1;
        }

        @Override
        public String toString() {
            return "{" +
                "numberOfFaces: " + numberOfFaces +
                ", limit: " + limit +
                '}';
        }
    }
}

