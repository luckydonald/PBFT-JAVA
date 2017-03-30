package de.teamproject16.pbft.Sensor;

import de.luckydonald.utils.dockerus.IDoNotWantThisException;

/**
 * This emulates sensor data.
 */
public class FakeSensor {
    public double getSensorValue() throws IDoNotWantThisException {
        return Math.random()*10;
    }
}
