package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;
import static de.teamproject16.pbft.Messages.Types.INIT;

/**
 * Created by IngridBoldt on 29.09.16.
 */
public class InitMessage extends Message {
    public double value;
    //public int node;

    /**
     *InitMessage
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param value the value of the sensor from the node
     */
    public InitMessage(long sequence_no, int node, double value) {
        super(node, INIT, sequence_no);
        //this.node = node;
        this.value = value;
    }

    /**
     * Create a initmessage object from the data out JSONObject.
     * @param data JSONObject
     * @return a new InitMessage object with the specific data.
     * @throws JSONException
     */
    public static InitMessage messageDecipher(JSONObject data) throws JSONException {
        return new InitMessage(data.getLong("sequence_no"), data.getInt("node"),
                data.getDouble("value"));
    }

    /**
     * Create JSONObject for the network.
     * @return data JSONObject
     * @throws JSONException
     */
    public JSONObject messageEncode() throws JSONException {
        JSONObject data = super.messageEncode();
        data.put("value", this.value);
        return data;
    }
}
