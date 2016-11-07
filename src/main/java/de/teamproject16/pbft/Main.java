package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.teamproject16.pbft.Network.Receiver;
import org.json.JSONException;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the program. Strarting stuff.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, DockerException, DockerCertificateException, JSONException {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        Logger.getAnonymousLogger().addHandler(consoleHandler);
        Logger.getGlobal().addHandler(consoleHandler);
        Logger.getAnonymousLogger();
        System.out.println("HalloMain");
        Receiver receiver = new Receiver();
        receiver.start();
        NormalCase algo = new NormalCase(receiver);
        StringBuilder sb = new StringBuilder("[Environment] \n");
        sb.append("Node container: ").append(DockerusAuto.getInstance().getEnvHostname()).append("\n");
        sb.append("Node hostname:  ").append(DockerusAuto.getInstance().getHostname()).append("\n");
        sb.append("Node number:    ").append(DockerusAuto.getInstance().getNumber()).append("\n");
        sb.append("Node name:      ").append(DockerusAuto.getInstance().getName()).append("\n");
        sb.append("Node project:   ").append(DockerusAuto.getInstance().getProject()).append("\n");
        sb.append("\n[Other nodes]\n");
        for (String n : DockerusAuto.getInstance().getHostnames(false)) {
            sb.append(" - ").append(n).append("\n");
        }
        System.out.println(sb.toString());

        while(true) {
            System.out.println("### STARTING ROUND ###");
            double result = algo.normalFunction();
            algo.cleanUp();
            System.out.println("### RESULT: " + result);
        }


    }
}
