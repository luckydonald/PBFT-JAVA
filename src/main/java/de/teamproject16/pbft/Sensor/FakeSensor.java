package de.teamproject16.pbft.Sensor;

/**
 * This emulates sensor data.
 */
public class FakeSensor {
    public static float getSensorValue (){
        float random = (float) Math.random()*10;
        return random;
    }
}
