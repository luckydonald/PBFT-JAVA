package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.teamproject16.pbft.Messages.InitMessage;
import de.teamproject16.pbft.Messages.ProposeMessage;
import de.teamproject16.pbft.Network.Receiver;
import de.teamproject16.pbft.Network.Sender;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by IngridBoldt on 26.10.16.
 */
public class NormalCase {
    public int sequencelength = 1000;

    int leader = 1;

    ArrayList<InitMessage> initStore = null;
    Receiver r = null;
    private int sequenceNo;

    public NormalCase(Receiver receiver) {
        this.r = receiver;
    }


    /**
     * The row 15 to 27 from the algorithm.
     * @throws DockerException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     * @throws DockerCertificateException
     */
    public void normalFunction() throws DockerException, InterruptedException, UnsupportedEncodingException, DockerCertificateException {
        Sender sender = new Sender();
        initStore = new ArrayList<>();

        sender.sendMessage(new InitMessage(this.sequenceNo, DockerusAuto.getInstance().getNumber(), ToDO.getSensorValue()));  //UNixtimestampe

        if(this.leader == DockerusAuto.getInstance().getNumber()){
            if(this.initStore.size() >= (DockerusAuto.getInstance().getHostnames(true).size() - 1)/3){
                float median = Median.calculateMedian(this.initStore);
                sender.sendMessage(
                        new ProposeMessage(
                                (int) System.currentTimeMillis()/sequencelength,
                                DockerusAuto.getInstance().getNumber(),
                                DockerusAuto.getInstance().getNumber(),
                                median,
                                initStore
                        )
                );
            }
            //notify soll auf timeout oder genügend nachrichten warten

        }
    }

    public void cleanUp() {
        if(this.initStore != null) {
            this.initStore.clear();
            this.initStore = null;
        }
        this.sequenceNo = (int) System.currentTimeMillis()/sequencelength;
    }
}
