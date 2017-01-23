package de.teamproject16.pbft.Messages;

import org.json.JSONObject;

import java.util.ArrayList;

import static de.teamproject16.pbft.Messages.Types.LEADER_CHANGE;

/**
 * Created by IngridBoldt on 04.10.16.
 */
public class LeaderChangeMessage extends Message {

    public int node;
    public int leader;
    public ArrayList<PrevoteMessage> prevoteList;

    public LeaderChangeMessage(long sequence_no, int node, int leader, ArrayList<PrevoteMessage> prevoteList) {
        super(LEADER_CHANGE, sequence_no);
        this.node = node;
        this.leader = leader;
        this.prevoteList = prevoteList;
    }

    public static LeaderChangeMessage messageDecipher(JSONObject data) {
        return null;
    }
}
