package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by luckydonald on 17.10.16.
 */
public class DockerusAuto extends Dockerus {
    DockerusAuto() throws IDoNotWantThisException {
    }

    private static Dockerus instance;

    static public Dockerus getInstance() throws IDoNotWantThisException {
        // Config file
        if (DockerusAuto.instance == null) {
            try {
                DockerusAuto.instance = DockerusFile.getInstance();
                System.out.println("DockerFile");
            } catch (IDoNotWantThisException e) {
                e.printStackTrace();
            }
        }

        // Docker instance
        if (DockerusAuto.instance == null) {
            try {
                DockerusAuto.instance = Dockerus.getInstance();
                System.out.println("Docker");
            } catch (IDoNotWantThisException e) {
                e.printStackTrace();
            }
        }

        // Dummy (Travis, etc.)
        if (DockerusAuto.instance == null) {
            try {
                DockerusAuto.instance = DockerusDummy.getInstance();
                System.out.println("DockerDummy");
            } catch (IDoNotWantThisException e) {
                e.printStackTrace();
            }
        }
        // Nothing is working
        if (DockerusAuto.instance == null) {
            System.err.println("LOL THIS SHOULDN'T FAIL BECAUSE FAILING IS NOT IMPLEMENTED!!!111");
            System.err.println("The config file, docker and the dummy class failed. At least the dummy class should have be successful.");
            throw new IDoNotWantThisException(new InvalidPropertiesFormatException("pfft!"));
        }
        return DockerusAuto.instance;
    }

}
