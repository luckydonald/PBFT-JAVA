package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;
import static de.teamproject16.pbft.Messages.Types.PREVOTE;

/**
 * Created by IngridBoldt on 29.09.16.
 */
public class PrevoteMessage extends Message {

    Number node;
    Number leader;
    Float value;

    /**
     * Prevote message
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param leader
     * @param value from the node
     */
    public PrevoteMessage(Number sequence_no, Number node, Number leader, Float value) {
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
        return new PrevoteMessage((Number) data.get("sequence_no"), (Number) data.get("node"),
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
