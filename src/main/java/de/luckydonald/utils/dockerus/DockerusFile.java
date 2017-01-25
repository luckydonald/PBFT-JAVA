package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IngridBoldt on 08.12.16.
 */
public class DockerusFile extends Dockerus {

    public static final String NODE_HOSTS_KEY = "node_hosts";
    public static final String JSON_FILENAME = "config.json";
    public static final String API_HOST_KEY = "api_host";
    public static final String OWN_HOST_KEY = "own_host";
    ArrayList<String> hostnames = new ArrayList<>();
    String api_host = null;
    String own_host = null;

    public DockerusFile() throws IDoNotWantThisException {
        try {
            read_json_file();
        } catch (IOException e) {
            System.err.println("File \"" + JSON_FILENAME + "\" could not be accessed. \n" +
                    "If you need to create one, have a look at the \"" + JSON_FILENAME + ".example\" file.");
            throw new IDoNotWantThisException(e);
        } catch (JSONException e) {
            System.err.println("File \"" + JSON_FILENAME + "\" seems to be no valid json.");
            throw new IDoNotWantThisException(e);
        }
    }
    static private Dockerus instance = null;

    static public Dockerus getInstance() throws IDoNotWantThisException {
        if (DockerusFile.instance == null) {
            DockerusFile.instance = new DockerusFile();
        }
        return DockerusFile.instance;
    }


    @Override
    public List<String> getHostnames(boolean excludeSelf) throws DockerException, InterruptedException {
        return hostnames;
    }

    @Override
    public int getTotal(boolean excludeSelf) {
        return hostnames.size();
    }

    @Override
    public int getNumber() throws DockerException, InterruptedException {
        return hostnames.indexOf(this.getHostname()); //Sich selbst in der liste finden und position zurückgeben
    }

    @Override
    public String getHostname() throws DockerException, InterruptedException {
        try {
            if (this.own_host!=null && this.own_host.length() > 0) {
                return this.own_host;
            }
            return "" + InetAddress.getLocalHost().getHostAddress();//IP über Socket / System abfragen
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() throws DockerException, InterruptedException {
        return null;
    }
    @Override
    public String getProject() throws DockerException, InterruptedException {
        return null;
    }

    public void read_json_file() throws JSONException, IOException, IDoNotWantThisException {
        String jsonData = readFile(JSON_FILENAME);
        JSONObject root = new JSONObject(jsonData);
        JSONArray hostnames_json = new JSONArray(root.getJSONArray(NODE_HOSTS_KEY).toString());
        for (int i = 0; i < hostnames_json.length(); i++) {
            String elem = hostnames_json.getString(i);
            this.hostnames.add(elem);
        }
        // api host (dumper)
        if (root.has(API_HOST_KEY) && !root.isNull(API_HOST_KEY)) {
            this.api_host = root.getString(API_HOST_KEY);
        } else {
            this.api_host = super.getApiHost();
        }
        if (root.has(OWN_HOST_KEY) && !root.isNull(OWN_HOST_KEY)) {
            this.own_host = root.getString(OWN_HOST_KEY);
            if (this.hostnames.indexOf(this.own_host) < 0) {
                throw new IDoNotWantThisException(
                        "Specified '"+OWN_HOST_KEY+"' ("+this.own_host+") " +
                        "is not contained in '"+NODE_HOSTS_KEY+"("+hostnames_json+")'!"
                );
            }
        }
    }
    public static String readFile(String filename) throws IOException {
        return readFile(new File(filename));
    }
    public static String readFile(File filename) throws IOException {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
}
