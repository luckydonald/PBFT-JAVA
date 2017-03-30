package de.teamproject16.pbft.Messages;

import org.apache.commons.lang.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

import static de.teamproject16.pbft.Messages.Types.ACKNOWLEDGE;

/**
 * This is just to notify the API server that we got a message.
 */
public class Acknowledge extends Message {
    //public int node;
    public int sender;
    public JSONObject raw;

    /**
     *InitMessage
     * @param sequence_no of tries
     * @param node the id of the current node
     * @param sender the id of the other node, the sender
     */
    public Acknowledge(long sequence_no, int node, int sender) {
        super(node, ACKNOWLEDGE, sequence_no);
        //this.node = node;
        this.sender = sender;
    }

    /**
     *InitMessage
     * @param sequence_no of tries
     * @param node the id of the current node
     * @param sender the id of the other node, the sender
     * @param raw json which was received
     */
    public Acknowledge(long sequence_no, int node, int sender, JSONObject raw) {
        super(node, ACKNOWLEDGE, sequence_no);
        //this.node = node;
        this.sender = sender;
        this.raw = raw;
    }

    /**
     * Create a initmessage object from the data out JSONObject.
     * @param data JSONObject
     * @return a new InitMessage object with the specific data.
     * @throws JSONException
     */
    public static Acknowledge messageDecipher(JSONObject data) throws JSONException {
        throw new NotImplementedException();
    }

    /**
     * Create JSONObject for the network.
     * @return data JSONObject
     * @throws JSONException
     */
    public JSONObject messageEncode() throws JSONException {
        JSONObject data = super.messageEncode();
        //data.put("node", this.node);
        data.put("sender", this.sender);
        data.put("raw", this.raw);
        return data;
    }
}
