package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;
import static de.teamproject16.pbft.Messages.Types.VOTE;


/**
 * Created by IngridBoldt on 06.10.16.
 */
public class VoteMessage extends Message {

    public Number node;
    public Number leader;
    public Float value;

    /**
     * Vote message
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param leader
     * @param value of the sensor
     */
    public VoteMessage(Number sequence_no, Number node, Number leader, Float value) {
        super(VOTE, sequence_no);
        this.node = node;
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
        return new VoteMessage((Number) data.get("sequence_no"), (Number) data.get("node"),
                (Number) data.get("leader"), (Float) data.get("value"));
    }

    /**
     * Create JSONObject for the network.
     * @return data JSONObject
     * @throws JSONException
     */
    public JSONObject messageEncode() throws JSONException {
        JSONObject data = super.messageEncode();
        data.put("node", this.node);
        data.put("leader", this.leader);
        data.put("value", this.value);
        return data;
    }
}
