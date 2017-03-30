package de.teamproject16.pbft.Network;

/**
 * Raised if the connection should be closed.
 */
public class CloseConnectionPlease extends Exception {
    public CloseConnectionPlease(String msg) {
        super(msg);
    }
    public CloseConnectionPlease() {
        super();
    }
}
