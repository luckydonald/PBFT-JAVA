package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.teamproject16.pbft.Network.Receiver;

import java.io.IOException;

/**
 * Created by IngridBoldt on 06.10.16.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, DockerException, DockerCertificateException {
        System.out.println("HalloMain");
        Receiver receiver = new Receiver();
        NormalCase algo = new NormalCase(receiver);
        receiver.receiver();

    }
}
