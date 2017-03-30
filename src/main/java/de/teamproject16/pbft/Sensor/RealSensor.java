package de.teamproject16.pbft.Sensor;

import de.luckydonald.utils.dockerus.IDoNotWantThisException;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This emulates sensor data.
 */
public class RealSensor extends FakeSensor {
    ///sys/bus/w1/devices/XX-XXXX..../w1_slave
	/* path to search for devices in filesystem */
    private static String devicesPath = "/sys/bus/w1/devices";

    /* file of the measured values */
    private static String valueFile = "w1_slave";

    /* id of sensor */
    private static String id = null;

    public double value;

    public RealSensor() {
        File searchPath = new File(devicesPath);
        if (searchPath.listFiles()!=null) {
            for (File f: searchPath.listFiles()) {
                if (f.isDirectory() && !f.getName().startsWith("w1_bus_master"))
                    id = f.getName();
            }
        }
        this.id = id;
    }

    public double getRealSensorValue() throws IDoNotWantThisException {
        Path path = FileSystems.getDefault().getPath(devicesPath, id, valueFile);
        List<String> lines;

        int attempts = 3;
        boolean crcOK = false;

        while (attempts > 0) {
            try {
                lines = Files.readAllLines(path);
                for(String line: lines) {
                    if (line.endsWith("YES"))
                        crcOK = true;
                    else if (line.matches(".*t=[0-9]+") && crcOK)
                        return Integer.valueOf(line.substring(line.indexOf("=")+1))/1000.0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            attempts--;
        }

        throw new IDoNotWantThisException("");
    }


}