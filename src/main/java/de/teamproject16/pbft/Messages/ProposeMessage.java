package de.teamproject16.pbft.Messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static de.teamproject16.pbft.Messages.Types.PROPOSE;


/**
 * Created by IngridBoldt on 29.09.16.
 */
public class ProposeMessage extends Message {

    public int node;
    public int leader;
    public double proposal;
    public List<InitMessage> value_store;

    /**
     * Propose message
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param leader
     * @param proposal
     * @param value_store values from all nodes in the network
     */
    public ProposeMessage(long sequence_no, int node, int leader, double proposal, List<InitMessage> value_store) {
        super(PROPOSE, sequence_no);
        this.node = node;
        this.leader = leader;
        this.proposal = proposal;
        this.value_store = value_store;
    }

    /**
     * Create a propose message object from the data out JSONObject.
     * @param data JSONObject
     * @return a new propose message object with the specific data.
     * @throws JSONException
     */
    public static ProposeMessage messageDecipher(JSONObject data) throws JSONException {
        int len = data.getJSONArray("value_store").length();
        ArrayList<InitMessage> tmp_value_store = new ArrayList<>(len);
        for(int i=0; i < len; i++) {
            JSONObject obj = data.getJSONArray("value_store").getJSONObject(i);
            tmp_value_store.add(InitMessage.messageDecipher(obj));
        }
        return new ProposeMessage(
                data.getLong("sequence_no"), data.getInt("node"), data.getInt("leader"),
                data.getDouble("proposal"), tmp_value_store
        );
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
        data.put("proposal", this.proposal);
        JSONArray value_store_temp = new JSONArray();
        for (InitMessage msg : this.value_store) {
            value_store_temp.put(msg.messageEncode());
        }
        data.put("value_store", value_store_temp);
        return data;
    }
}

