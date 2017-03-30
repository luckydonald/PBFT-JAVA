import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DS1820Reader {

	///sys/bus/w1/devices/XX-XXXX..../w1_slave
	/* path to search for devices in filesystem */
    private static String devicesPath = "/sys/bus/w1/devices";

    /* file of the measured values */
    private static String valueFile = "w1_slave";

    /* id of sensor */
    private static String id = null;


    public static double read() {
       // if id is null, search for sensor and take the first
       if (id==null) {
         findSensorID();
         if (id == null)
          return Double.MAX_VALUE;
       }
       
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

       return Double.MAX_VALUE;
    }


    public static String findSensorID() {
       File searchPath = new File(devicesPath);
       if (searchPath.listFiles()!=null) {
         for (File f: searchPath.listFiles()) {
          if (f.isDirectory() && !f.getName().startsWith("w1_bus_master")) 
        	  id = f.getName();
              
         }
       }
       return id;
    }

    public static void main(String[] args) {
       System.out.println("DS1820 Temperature Sensor Tool");
       System.out.println("------------------------------");
       if(findSensorID() != null){
          System.out.println("  found sensor: " + id);
          System.out.print("  read sensor " + id + " ...");
          double t = DS1820Reader.read();
          System.out.println(" --> temp: " + t);
       }
        
   }


}

