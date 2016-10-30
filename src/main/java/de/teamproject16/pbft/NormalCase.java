package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.teamproject16.pbft.Messages.InitMessage;
import de.teamproject16.pbft.Messages.PrevoteMessage;
import de.teamproject16.pbft.Messages.ProposeMessage;
import de.teamproject16.pbft.Messages.VoteMessage;
import de.teamproject16.pbft.Network.MessageQueue;
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
    private float median;

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
                median = Median.calculateMedian(this.initStore);
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
            //wait mit while um die drei if. timeout

            ArrayList<PrevoteMessage> prevotStore = new ArrayList<>();
            ArrayList<VoteMessage> voteStore = new ArrayList<>();
            boolean prevoteDone = false;
            while(true){
                if(!MessageQueue.proposeM.isEmpty() && verifyProposal()){
                    sender.sendMessage(
                            new PrevoteMessage((int) System.currentTimeMillis()/sequencelength,
                            DockerusAuto.getInstance().getNumber(),
                            DockerusAuto.getInstance().getNumber(), median));
                }
                if(!MessageQueue.prevoteM.isEmpty() && !prevoteDone){ //abfrage das die sequenznr stimmt fehlt
                    prevotStore.add((PrevoteMessage) MessageQueue.prevoteM.take());
                    for (PrevoteMessage e : prevotStore){
                        float value = e.value;
                        int count = 0;
                        for (PrevoteMessage i : prevotStore){
                            if (value == i.value){
                                count++;
                            }
                        }
                        if (count >= (DockerusAuto.getInstance().getHostnames(true).size() - 1)/3){
                            sender.sendMessage(new VoteMessage((int) System.currentTimeMillis()/sequencelength,
                                    DockerusAuto.getInstance().getNumber(),
                                    DockerusAuto.getInstance().getNumber(), value));
                            prevoteDone = true;
                            break;
                        }

                    }
                }
                if(!MessageQueue.voteM.isEmpty()){//abfrage dessen das der median bei genügend node gleich ist und sequenznr stimmt fehlt
                    voteStore.add((VoteMessage) MessageQueue.voteM.take());
                }
            }
        }
    }

    public Boolean verifyProposal() throws InterruptedException {
        ProposeMessage pM = (ProposeMessage) MessageQueue.proposeM.take();
        float medianS = Median.calculateMedian(pM.value_store);
        return median == medianS ? true : false;
            //fragen nach gleicher sequenznr. und warum tun wir das nicht beim initstore noch mal?
    }


    public void cleanUp() {
        if(this.initStore != null) {
            this.initStore.clear();
            this.initStore = null;
        }
        this.sequenceNo = (int) System.currentTimeMillis()/sequencelength;
    }
}
