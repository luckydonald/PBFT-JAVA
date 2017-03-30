package de.teamproject16.pbft.Messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum class for the message types.
 */
public class Types {
    public static final int INIT = 1;  // Broadcasting the initial values.
    public static final int PROPOSE = 2;  // Only Leader sends this.
    public static final int PREVOTE = 3;  // Our median we calculated all by our self!
    public static final int VOTE = 4;
    public static final int LEADER_CHANGE = 5;
    public static final int ACKNOWLEDGE = -1;

    public static final Map<Integer, String> _NAMES_ = new HashMap<Integer, String>(){
        {
            put(INIT, "init");
            put(PROPOSE, "propose");
            put(PREVOTE, "prevote");
            put(VOTE, "vote");
            put(LEADER_CHANGE, "leader_change");

            put(ACKNOWLEDGE, "acknowledge");
        }
    };
}
