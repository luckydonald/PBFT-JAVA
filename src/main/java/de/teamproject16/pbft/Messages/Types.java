package de.teamproject16.pbft.Messages;

/**
 * Enum class for the messagetyps.
 */
public class Types {
    public static final int INIT = 1;  // Broadcasting the initial values.
    public static final int PROPOSE = 2;  // Only Leader sends this.
    public static final int PREVOTE = 3;  // Our median we calculated all by our self!
    public static final int VOTE = 4;
    public static final int LEADER_CHANGE = 5;
}
