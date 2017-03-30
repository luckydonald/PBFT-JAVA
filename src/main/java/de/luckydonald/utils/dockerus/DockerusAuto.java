package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;

import java.util.InvalidPropertiesFormatException;

/**
 * This selects a best fitting way to load config. See the other Dockerus* classes.
 * @author luckydonald
 */
public class DockerusAuto extends Dockerus {
    DockerusAuto() throws IDoNotWantThisException {
    }

    private static Dockerus instance;

    static public Dockerus getInstance() throws IDoNotWantThisException {
        if(DockerusAuto.instance != null) {
            return DockerusAuto.instance;
        }

        // Config file
        try {
            DockerusAuto.instance = DockerusFile.getInstance();
            System.out.println("DockerFile");
        } catch (IDoNotWantThisException e) {
            e.printStackTrace();
        }
        if(DockerusAuto.instance != null) {
            return DockerusAuto.instance;
        }

        // Docker instance
        try {
            DockerusAuto.instance = Dockerus.getInstance();
            System.out.println("Docker");
        } catch (IDoNotWantThisException e) {
            e.printStackTrace();
        }
        if(DockerusAuto.instance != null) {
            return DockerusAuto.instance;
        }

        // Dummy (Travis, etc.)
        try {
            DockerusAuto.instance = DockerusDummy.getInstance();
            System.out.println("DockerDummy");
        } catch (IDoNotWantThisException e) {
            e.printStackTrace();
        }

        if(DockerusAuto.instance != null) {
            return DockerusAuto.instance;
        }

        // Nothing is working
        System.err.println("LOL THIS SHOULDN'T FAIL BECAUSE FAILING IS NOT IMPLEMENTED!!!111");
        System.err.println("The config file, docker and the dummy class failed. At least the dummy class should have be successful.");
        throw new IDoNotWantThisException(new InvalidPropertiesFormatException("pfft!"));
    }

}
