package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.teamproject16.pbft.Network.Receiver;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IngridBoldt on 06.10.16.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, DockerException, DockerCertificateException {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        Logger.getAnonymousLogger().addHandler(consoleHandler);
        Logger.getGlobal().addHandler(consoleHandler);
        Logger.getAnonymousLogger();
        System.out.println("HalloMain");
        Receiver receiver = new Receiver();
        receiver.start();
        NormalCase algo = new NormalCase(receiver);
        double result = algo.normalFunction();
        System.out.println(result);
        receiver.receiver();

    }
}
