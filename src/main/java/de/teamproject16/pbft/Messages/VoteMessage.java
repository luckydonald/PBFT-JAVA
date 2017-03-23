package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;
import static de.teamproject16.pbft.Messages.Types.VOTE;


/**
 * Created by IngridBoldt on 06.10.16.
 */
public class VoteMessage extends Message {

    //public int node;
    public int leader;
    public double value;

    /**
     * Vote message
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param leader the leader node
     * @param value of the sensor
     */
    public VoteMessage(long sequence_no, int node, int leader, double value) {
        super(node, VOTE, sequence_no);
        //this.node = node;
        this.leader = leader;
        this.value = value;
    }

    /**
     * Create a vote message object from the data out JSONObject.
     * @param data JSONObject
     * @return a new vote message object with the specific data.
     * @throws JSONException
     */
    public static VoteMessage messageDecipher(JSONObject data) throws JSONException {
        return new VoteMessage(data.getInt("sequence_no"), data.getInt("node"),
                data.getInt("leader"), data.getDouble("value"));
    }

    /**
     * Create JSONObject for the network.
     * @return data JSONObject
     * @throws JSONException
     */
    public JSONObject messageEncode() throws JSONException {
        JSONObject data = super.messageEncode();
        //data.put("node", this.node);
        data.put("leader", this.leader);
        data.put("value", this.value);
        return data;
    }
}
