package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.teamproject16.pbft.Messages.*;
import de.teamproject16.pbft.Network.MessageQueue;
import de.teamproject16.pbft.Network.Receiver;
import de.teamproject16.pbft.Network.Sender;
import org.json.JSONException;

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
    private long sequenceNo;

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
    public double normalFunction() throws DockerException, InterruptedException, UnsupportedEncodingException, DockerCertificateException, JSONException {
        Sender sender = new Sender();
        initStore = new ArrayList<>();

        sender.sendMessage(new InitMessage(this.sequenceNo, DockerusAuto.getInstance().getNumber(), ToDO.getSensorValue()));  //UNixtimestampe

        if(this.leader == DockerusAuto.getInstance().getNumber()) {
            if (this.initStore.size() >= getFaultyCount()) {
                sender.sendMessage(
                        new ProposeMessage(
                                (int) System.currentTimeMillis() / sequencelength,
                                DockerusAuto.getInstance().getNumber(),
                                DockerusAuto.getInstance().getNumber(),
                                Median.calculateMedian(this.initStore),
                                initStore
                        )
                );
            }
        }

        ArrayList<Message> prevoteStore = new ArrayList<>();
        ArrayList<Message> voteStore = new ArrayList<>();
        boolean prevoteDone = false;
        while(true){
            synchronized (this.r) {
                this.r.wait();  // waits for a new message to allow all 3 ifs to check, but otherwise block.
                if (!MessageQueue.proposeM.isEmpty() && verifyProposal((ProposeMessage) MessageQueue.proposeM.take())) {
                    sender.sendMessage(
                            new PrevoteMessage((int) System.currentTimeMillis() / sequencelength,
                                    DockerusAuto.getInstance().getNumber(),
                                    DockerusAuto.getInstance().getNumber(), Median.calculateMedian(this.initStore)));
                }
                if (!MessageQueue.prevoteM.isEmpty() && !prevoteDone) { //abfrage das die sequenznr stimmt fehlt
                    prevoteStore.add((PrevoteMessage) MessageQueue.prevoteM.take());
                    VerifyAgreementResult agreement = checkAgreement(prevoteStore);
                    if (agreement.bool) {
                        sender.sendMessage(new VoteMessage((int) System.currentTimeMillis() / sequencelength,
                                DockerusAuto.getInstance().getNumber(),
                                DockerusAuto.getInstance().getNumber(), agreement.value));
                        prevoteDone = true;
                    }
                }
                if (!MessageQueue.voteM.isEmpty()) {//abfrage dessen das der median bei genügend node gleich ist und sequenznr stimmt fehlt
                    voteStore.add((VoteMessage) MessageQueue.voteM.take());
                    VerifyAgreementResult agreement = checkAgreement(voteStore);
                    if (agreement.bool) {
                        return agreement.value;
                    }
                }
            }
        }
    }

    /**
     * Verify the calculated median for the result.
     * @param store
     * @return a tuple as VerifyAgreementResult
     * @throws DockerException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     * @throws DockerCertificateException
     */
    private VerifyAgreementResult checkAgreement(ArrayList<Message> store) throws DockerException, InterruptedException, UnsupportedEncodingException, DockerCertificateException {
        double value = 0.0;
        for (Message e : store){
            if (e instanceof PrevoteMessage) {
                value = ((PrevoteMessage) e).value;
            } else
            if (e instanceof VoteMessage) {
                value = ((VoteMessage) e).value;
            } else {
                throw new IllegalArgumentException("Needs type PrevoteMessage or VoteMessage.");
            }
            int count = 0;
            for (Message i : store){
                if (((i instanceof PrevoteMessage && value == ((PrevoteMessage)i).value)
                  || (i instanceof VoteMessage && value == ((VoteMessage) i).value))){
                    count++;
                }
            }
            if (count >= ((DockerusAuto.getInstance().getHostnames(false).size() + getFaultyCount())/2)){
                return new VerifyAgreementResult(true, value);
            }
        }
        return new VerifyAgreementResult(false, value);
    }

    /**
     * Verify the propose message.
     * @return
     * @throws InterruptedException
     */
    public static boolean verifyProposal(ProposeMessage msg) throws InterruptedException {
        double medianS = Median.calculateMedian(msg.value_store);
        return msg.proposal == medianS;
        //fragen nach gleicher sequenznr. und warum tun wir das nicht beim initstore noch mal?
    }

    /**
     * Calculates the faulty nodes.
     * @return
     * @throws DockerException
     * @throws InterruptedException
     */
    public double getFaultyCount() throws DockerException, InterruptedException {
        return (DockerusAuto.getInstance().getHostnames(false).size() -  1)/3;
    }

    /**
     * Clean the memory for a new sequence number.
     */
    public void cleanUp() {
        if(this.initStore != null) {
            this.initStore.clear();
            this.initStore = null;
        }
        this.sequenceNo = (System.currentTimeMillis() / sequencelength);
    }


    /**
     * A class for return tupel in java.
     */
    class VerifyAgreementResult {
        private final double value;
        private final boolean bool;

        public VerifyAgreementResult(boolean bool, double value) {
            this.bool = bool;
            this.value = value;
        }
    }
}
