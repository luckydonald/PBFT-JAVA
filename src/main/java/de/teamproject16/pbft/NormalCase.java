package de.teamproject16.pbft;

import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerException;
import de.luckydonald.utils.dockerus.DockerusAuto;
import de.teamproject16.pbft.Messages.*;
import de.teamproject16.pbft.Network.MessageQueue;
import de.teamproject16.pbft.Network.Receiver;
import de.teamproject16.pbft.Network.Sender;
import jnr.ffi.annotations.In;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static de.luckydonald.utils.Streams.toArrayList;
import static java.util.stream.Collectors.groupingBy;

public class NormalCase {
    public int sequencelength = 10000;

    int leader = 1;

    ArrayList<InitMessage> initStore = null;
    ArrayList<PrevoteMessage> prevoteStore = null;
    ArrayList<LeaderChangeMessage> leaderchangeStore = null;

    Receiver r = null;
    Sender sender = null;
    private long sequenceNo;

    public NormalCase(Receiver receiver) {
        this.r = receiver;
        this.sender = new Sender();
    }

    /**
     * The row 15 to 27 from the algorithm.
     * @throws DockerException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     * @throws DockerCertificateException
     */
    public double normalFunction() throws DockerException, InterruptedException, UnsupportedEncodingException, DockerCertificateException, JSONException {
        cleanUp();
        long newSeq = calculateSequenceNumber();
        if (this.sequenceNo >= newSeq) {
            o("Sequence number is equal. Old " + this.sequenceNo + " and new " + newSeq);
        }
        while (this.sequenceNo >= newSeq) {
            synchronized (this) {
                this.wait((long)((sequencelength/10)-1));
                newSeq = calculateSequenceNumber();
            }
        }
        o("Changed sequence " + this.sequenceNo + " to " + newSeq);
        this.sequenceNo = newSeq;
        System.out.println("NODE ID: " + getNumber() + " SEQ_NO: " + sequenceNo);

        sender.sendMessage(new InitMessage(this.sequenceNo, getNumber(), ToDO.getSensorValue()));
        //prevoteStore = new ArrayList<>();
        ArrayList<Message> voteStore = new ArrayList<>();
        int state = 0;
        // prevoteDone = false;
        while(true){
            synchronized (this.r) {
                if(!MessageQueue.initM.isEmpty()) {
                    System.out.println("Got InitMessage");
                    this.initStore.add((InitMessage) MessageQueue.initM.take());
                }
                if(state == 0) {
                    if(this.leader == getNumber()) {  // are we the leader?
                        System.out.println("LEADER! Got " + this.initStore.size() + " messages.");
                        if (this.initStore.size() >= (getTotalNodeCount() - getFaultyNodeCount())) {  // <---
                            o("ENOUGH INIT");
                            sender.sendMessage(
                                    new ProposeMessage(
                                            this.sequenceNo,
                                            getNumber(),
                                            this.leader,
                                            Median.calculateMedian(this.initStore),
                                            initStore
                                    )
                            );
                            state = 1;  // we send da message.
                            o("state = 1");
                        }
                    } else {
                        state = 1;
                        o("state = 1");
                    }
                }
                if (state == 1 && !MessageQueue.proposeM.isEmpty() && verifyProposal((ProposeMessage) MessageQueue.proposeM.take())) {
                    o("Got ProposeMessage");
                    sender.sendMessage(
                            new PrevoteMessage(
                                    this.sequenceNo, getNumber(), this.leader, Median.calculateMedian(this.initStore)
                            )
                    );
                    state = 2;
                    o("state = 2");
                }
                if (state == 2 && !MessageQueue.prevoteM.isEmpty()) { //abfrage das die sequenznr stimmt fehlt
                    o("Got PrevoteMessage");
                    prevoteStore.add((PrevoteMessage) MessageQueue.prevoteM.take());
                    VerifyAgreementResult agreement = checkAgreement(prevoteStore.stream().collect(toArrayList()));
                    if (agreement.bool) {
                        sender.sendMessage(new VoteMessage(this.sequenceNo,
                                getNumber(),
                                this.leader, agreement.value));
                        state=3;
                        o("state = "+ state);
                    } else {
                        o("checkAgreement failed.");
                    }
                }
                if ((state == 2 || state == 3) && !MessageQueue.voteM.isEmpty()) {//abfrage dessen das der median bei genügend node gleich ist und sequenznr stimmt fehlt
                    o("Got VoteMessage");
                    voteStore.add((VoteMessage) MessageQueue.voteM.take());
                    VerifyAgreementResult agreement = checkAgreement(voteStore);
                    if (agreement.bool) {
                        o("state = DONE! (now doing cleanup)");
                        this.cleanUp();
                        return agreement.value;
                    }
                } else {
                    this.r.wait();  // waits for a new message to allow all 3 ifs to check, but otherwise block.
                }
            }
        }
    }

    public long calculateSequenceNumber() {
        return System.currentTimeMillis() / sequencelength;
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
    public VerifyAgreementResult checkAgreement(List<Message> store) throws DockerException, InterruptedException, UnsupportedEncodingException, DockerCertificateException {
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
            if (count >= muchMoreThenHalf()){
                return new VerifyAgreementResult(true, value);
            }
        }
        return new VerifyAgreementResult(false, value);
    }

    double muchMoreThenHalf() throws DockerException, InterruptedException {
        return (this.getTotalNodeCount() + getFaultyNodeCount())/2;
    }

    public void leaderChange() throws DockerException, InterruptedException, JSONException, UnsupportedEncodingException, DockerCertificateException {
        this.incrementLeader();
        sender.sendMessage(new LeaderChangeMessage(this.sequenceNo, getNumber(), this.leader, prevoteStore));
        if(this.leader == getNumber()) {
            if(this.leaderchangeStore.size() > muchMoreThenHalf()) {
                // DO STUFF
            }
        }
    }

    public void getLastPrepared(List<LeaderChangeMessage> leaderChangeMessageList) {
        LeaderChangeMessage lastPreparedTemp = null;
        // Tuple: Round number, prevoted value
        int roundNumber;
        double prevoteValue;
        for (LeaderChangeMessage msg : leaderChangeMessageList) {
            Map<Integer, List<PrevoteMessage>> tmp = msg.prevoteList.stream().collect(groupingBy(prev_msg -> prev_msg.leader));
            // leader: [PrevoteMessage, PrevoteMessage, PrevoteMessage]
            for (Integer leader : tmp.keySet()) {
                Map<Double, Integer> count_map = new HashMap<>();
                //Map<Double, Integer> tmp2 = tmp.get(leader).stream().flatMapToLong();
                // value: [PrevoteMessage, PrevoteMessage, PrevoteMessage]
                //for (Double value : tmp2.keySet()) {
                //    count =
                //}

            }
            // value: list of messages
            for (PrevoteMessage pre : msg.prevoteList) {

            }
        }

    }

    public PrevoteMessage getMostValue(List<PrevoteMessage> list) {
        return list.stream().sorted((a, b)->Double.compare(a.value, b.value)).collect(groupingBy(m -> m.value)).entrySet().stream().reduce((l1, l2) -> l1.getValue().size() > l2.getValue().size() ? l1 : l2).get().getValue().stream().findAny().orElseGet(null);
    }

    /**
     * Retrieves the number this node has.
     *
     * @return number of the node
     * @throws DockerException
     * @throws InterruptedException
     */
    int getNumber() throws DockerException, InterruptedException {
        return DockerusAuto.getInstance().getNumber();
    }

    /**
     * Selectes the next leader.
     *
     * @throws DockerException
     * @throws InterruptedException
     */
    public void incrementLeader() throws DockerException, InterruptedException {
        this.leader = (int) ((this.leader + 1) % this.getTotalNodeCount());
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
     *
     * @return count of possible faulty nodes.
     * @throws DockerException
     * @throws InterruptedException
     */
    public double getFaultyNodeCount() throws DockerException, InterruptedException {
        return (this.getTotalNodeCount() -  1)/3;
    }

    /**
     * Retrieves the total amount of nodes.
     *
     * @return count of total nodes in the system.
     * @throws DockerException
     * @throws InterruptedException
     */
    public double getTotalNodeCount() throws DockerException, InterruptedException {
        return DockerusAuto.getInstance().getTotal(false);
    }

    /**
     * Clean the memory for a new sequence number.
     */
    public void cleanUp() {
        this.sequenceNo = calculateSequenceNumber();
        if(this.initStore != null) {
            this.initStore = this.initStore.stream()
                .filter(i-> i.sequence_no >= this.sequenceNo)
                .collect(toArrayList());
        } else {
            this.initStore = new ArrayList<>();
        }
        if (this.prevoteStore != null) {
            this.prevoteStore = this.prevoteStore.stream()
                .filter(i -> i.sequence_no >= this.sequenceNo)
                .collect(toArrayList());
        } else {
            this.prevoteStore = new ArrayList<>();
        }
    }



    /**
     * A class for return tuple in java.
     */
    class VerifyAgreementResult {
        public final double value;
        public final boolean bool;

        public VerifyAgreementResult(boolean bool, double value) {
            this.bool = bool;
            this.value = value;
        }
    }
    static void o(String s) {
        System.out.println(s);
    }
}
