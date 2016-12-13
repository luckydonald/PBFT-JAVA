package de.luckydonald.utils.dockerus;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;

/**
 * Created by luckydonald on 17.10.16.
 */
public class DockerusAuto extends Dockerus {
    DockerusAuto() throws IDoNotWantThisException {
    }

    private static Dockerus instance;

    static public Dockerus getInstance() throws IDoNotWantThisException {
        if (DockerusAuto.instance == null) {
            try {
                DockerusAuto.instance = Dockerus.getInstance();
                System.out.println("Docker");
            } catch ( IDoNotWantThisException e) {
                try {
                    DockerusAuto.instance = DockerusFile.getInstance();
                    System.out.println("DockerFile");
                } catch (IDoNotWantThisException e1) {
                    e1.printStackTrace();
                    try {
                        DockerusAuto.instance = DockerusDummy.getInstance();
                        System.out.println("DockerDummy");
                    } catch (IDoNotWantThisException e2) {
                        System.err.println("LOL THIS SHOULDN'T FAIL BECAUSE FAILING IS NOT IMPLEMENTED!!!111");
                        e1.printStackTrace();
                        throw new IDoNotWantThisException(e2);
                    }
                }
            }
        }
        return DockerusAuto.instance;
    }

}
