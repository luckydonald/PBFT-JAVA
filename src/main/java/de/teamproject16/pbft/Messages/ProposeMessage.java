package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static de.teamproject16.pbft.Messages.Types.PROPOSE;


/**
 * Created by IngridBoldt on 29.09.16.
 */
public class ProposeMessage extends Message {

    public Number node;
    public Number leader;
    public Number proposal;
    public List<InitMessage> value_store;

    /**
     * Propose message
     * @param sequence_no of tries
     * @param node the id of the sender
     * @param leader
     * @param proposal
     * @param value_store values from all nodes in the network
     */
    public ProposeMessage(Number sequence_no, Number node, Number leader, Number proposal, List<InitMessage> value_store) {
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
                (Number) data.get("sequence_no"), (Number) data.get("node"), (Number) data.get("leader"),
                (Number) data.get("proposal"), tmp_value_store
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
        data.put("value_store", this.value_store);
        return data;
    }
}

