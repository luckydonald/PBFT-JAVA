package de.teamproject16.pbft.Network;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.luckydonald.utils.dockerus.IDoNotWantThisException;
import de.teamproject16.pbft.Messages.Message;
import de.teamproject16.pbft.Network.Database.Dumper;
import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

/**
 * Created by IngridBoldt on 06.10.16.
 */
public class Sender {

    /**
     * Make the message object to a string and give it to the method broadcast.
     * @param msg object from message with specific data.
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     * @throws DockerException
     * @throws DockerCertificateException
     */
    public void sendMessage(Message msg) throws JSONException, InterruptedException, IDoNotWantThisException, DockerException, UnsupportedEncodingException {
        String json = msg.messageEncode().toString();
        Dumper.send(json);
        broadcast(json);
    }

    /**
     * Send broadcast messages in the network of registered nodes.
     * @param message String with the specific message (init, void...).
     * @throws UnsupportedEncodingException
     * @throws DockerCertificateException
     * @throws DockerException
     * @throws InterruptedException
     */
    public void broadcast(String message) throws IDoNotWantThisException, DockerException, InterruptedException, UnsupportedEncodingException {
        List<String> otherHostnames = DockerusAuto.getInstance().getHostnames(false);
        message += "\n";
        String msg = "ANSWER " + message.length() + "\n" + message;
        byte[] msgBytes = msg.getBytes("UTF-8");
        for (String nodeHost: otherHostnames){
            Boolean sent = false;
            while(!sent){
                try {
                    Socket socket = new Socket(nodeHost, 4458);  //open a socket port 4458, and the nodeHost names of the other hosts
                    DataOutputStream dataStream = new DataOutputStream(socket.getOutputStream()); //to send messages
                    dataStream.write(msgBytes); //write the messages at the datastream
                    sent = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
