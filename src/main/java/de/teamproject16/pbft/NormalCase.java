package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.teamproject16.pbft.Messages.*;
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
    public double normalFunction() throws DockerException, InterruptedException, UnsupportedEncodingException, DockerCertificateException {
        Sender sender = new Sender();
        initStore = new ArrayList<>();

        sender.sendMessage(new InitMessage(this.sequenceNo, DockerusAuto.getInstance().getNumber(), ToDO.getSensorValue()));  //UNixtimestampe

        if(this.leader == DockerusAuto.getInstance().getNumber()){
            if(this.initStore.size() >= getFaultyCount()){

                sender.sendMessage(
                        new ProposeMessage(
                                (int) System.currentTimeMillis()/sequencelength,
                                DockerusAuto.getInstance().getNumber(),
                                DockerusAuto.getInstance().getNumber(),
                                Median.calculateMedian(this.initStore),
                                initStore
                        )
                );
            }
            //wait mit while um die drei if. timeout
            this.r.wait();// jetzt noch auf der receiver seite
            ArrayList<Message> prevotStore = new ArrayList<>();
            ArrayList<Message> voteStore = new ArrayList<>();
            boolean prevoteDone = false;
            while(true){
                if(!MessageQueue.proposeM.isEmpty() && verifyProposal()){
                    sender.sendMessage(
                            new PrevoteMessage((int) System.currentTimeMillis()/sequencelength,
                            DockerusAuto.getInstance().getNumber(),
                            DockerusAuto.getInstance().getNumber(), Median.calculateMedian(this.initStore)));
                }
                if(!MessageQueue.prevoteM.isEmpty() && !prevoteDone){ //abfrage das die sequenznr stimmt fehlt
                    prevotStore.add((PrevoteMessage) MessageQueue.prevoteM.take());
                    VerifyAgreementResult agreement = checkAgreement(prevotStore);
                    if(agreement.bool){
                    sender.sendMessage(new VoteMessage((int) System.currentTimeMillis()/sequencelength,
                            DockerusAuto.getInstance().getNumber(),
                            DockerusAuto.getInstance().getNumber(), agreement.value));
                        prevoteDone = true;
                    }
                }
                if(!MessageQueue.voteM.isEmpty()){//abfrage dessen das der median bei genügend node gleich ist und sequenznr stimmt fehlt
                    voteStore.add((VoteMessage) MessageQueue.voteM.take());
                    VerifyAgreementResult agreement = checkAgreement(prevotStore);
                    if(agreement.bool){
                        return (double) agreement.value;
                    }
                }
            }
        }
        return 0;
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
        float value = 0;
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
            if (count >= ((DockerusAuto.getInstance().getHostnames(true).size() + getFaultyCount())/2)){
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
    public boolean verifyProposal() throws InterruptedException {
        ProposeMessage pM = (ProposeMessage) MessageQueue.proposeM.take();
        double median = pM.proposal;
        double medianS = Median.calculateMedian(pM.value_store);
        return median == medianS ? true : false;
            //fragen nach gleicher sequenznr. und warum tun wir das nicht beim initstore noch mal?
    }

    /**
     * Calculates the faulty nodes.
     * @return
     * @throws DockerException
     * @throws InterruptedException
     */
    public double getFaultyCount() throws DockerException, InterruptedException {
        return (DockerusAuto.getInstance().getHostnames(true).size() -  1)/3;
    }

    /**
     * Clean the memory for a new sequence number.
     */
    public void cleanUp() {
        if(this.initStore != null) {
            this.initStore.clear();
            this.initStore = null;
        }
        this.sequenceNo = (int) System.currentTimeMillis()/sequencelength;
    }


    /**
     * A class for return tupel in java.
     */
    class VerifyAgreementResult {
        private final float value;
        private final boolean bool;

        public VerifyAgreementResult(boolean bool, float value) {
            this.bool = bool;
            this.value = value;
        }
    }
}
