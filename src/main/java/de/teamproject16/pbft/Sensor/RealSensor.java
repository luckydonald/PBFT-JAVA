package de.teamproject16.pbft.Sensor;

/**
 * This emulates sensor data.
 */
public class RealSensor extends FakeSensor {
    public double value;

    public RealSensor(double value) {
        this.value = value;
    }

    public double getRealSensorValue(){
        return this.value;
    }
}