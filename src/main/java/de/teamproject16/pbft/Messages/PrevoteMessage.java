package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;
import static de.teamproject16.pbft.Messages.Types.PREVOTE;

/**
 * Created by IngridBoldt on 29.09.16.
 */
public class PrevoteMessage extends Message {

    public int node;
    public int leader;
    public double value;

    /**
     * Prevote message
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param leader the leading node
     * @param value from the node
     */
    public PrevoteMessage(long sequence_no, int node, int leader, double value) {
        super(PREVOTE, sequence_no);
        this.node = node;
        this.leader = leader;
        this.value = value;
    }

    /**
     * Create a prevote message object from the data out JSONObject.
     * @param data JSONObject
     * @return a new prevote message object with the specific data.
     * @throws JSONException
     */
    public static PrevoteMessage messageDecipher(JSONObject data) throws JSONException {
        return new PrevoteMessage(data.getLong("sequence_no"), data.getInt("node"),
                data.getInt("leader"), data.getDouble("value"));
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
