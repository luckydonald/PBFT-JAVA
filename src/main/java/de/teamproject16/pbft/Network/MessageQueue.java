package de.teamproject16.pbft.Network;

import de.teamproject16.pbft.CancelableLinkedBlockingQueue;
import de.teamproject16.pbft.Messages.*;

/**
 * Enqueue all them messages, in one queue for each type.
 * Like channels.
 */
public class MessageQueue {
    public static CancelableLinkedBlockingQueue leaderChangeM = new CancelableLinkedBlockingQueue();
    public static CancelableLinkedBlockingQueue initM = new CancelableLinkedBlockingQueue();
    public static CancelableLinkedBlockingQueue prevoteM = new CancelableLinkedBlockingQueue();
    public static CancelableLinkedBlockingQueue proposeM = new CancelableLinkedBlockingQueue();
    public static CancelableLinkedBlockingQueue voteM = new CancelableLinkedBlockingQueue();

    public static void messageQueue(Message message){
        try {
            if (message instanceof InitMessage){
                initM.put(message);
            }
            if (message instanceof LeaderChangeMessage) {
                leaderChangeM.put(message);
            }
            if (message instanceof PrevoteMessage) {
                prevoteM.put(message);
            }
            if (message instanceof ProposeMessage){
                proposeM.put(message);
            }
            if (message instanceof VoteMessage){
                voteM.put(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void cancelAll() {
        initM.cancel();
        leaderChangeM.cancel();
        prevoteM.cancel();
        proposeM.cancel();
        voteM.cancel();
    }
}


