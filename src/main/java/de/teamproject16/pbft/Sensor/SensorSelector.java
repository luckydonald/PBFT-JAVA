package de.teamproject16.pbft.Sensor;

import de.luckydonald.utils.ObjectWithLogger;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.luckydonald.utils.dockerus.IDoNotWantThisException;

/**
 * Created by  on
 *
 * @author luckydonald
 * @since 30.03.2017
 **/
public class SensorSelector extends ObjectWithLogger {
    private static FakeSensor instance = null;

    static public FakeSensor getInstance() {
        if(SensorSelector.instance != null) {
            return SensorSelector.instance;
        }
        try {
            if (DockerusAuto.getInstance().getSensorSimulate()) {
                throw new IDoNotWantThisException("Plz use fake.");
            }
            SensorSelector.instance = new RealSensor();
        } catch (IDoNotWantThisException e) {
            System.out.println("Using simulated sensor because " + e.toString());
            SensorSelector.instance = new FakeSensor();
        }
        return SensorSelector.instance;
    }

    static public double getSensorValue() throws IDoNotWantThisException {
        return SensorSelector.getInstance().getSensorValue();
    }
}