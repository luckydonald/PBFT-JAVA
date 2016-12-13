package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IngridBoldt on 08.12.16.
 */
public class DockerusFile extends Dockerus {

    ArrayList hostnames = new ArrayList();

    DockerusFile() throws DockerCertificateException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/Hostnames.csv"));
            String line = null;
            try {
                while((line = reader.readLine()) != null){
                    hostnames.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static private Dockerus instance = null;

    static public Dockerus getInstance() throws DockerCertificateException {
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
}
