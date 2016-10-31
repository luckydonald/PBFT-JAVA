package de.teamproject16.pbft.Messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IngridBoldt on 29.09.16.
 */
public class Message {

    int type;
    public long sequence_no;

    /**
     * Create the basic type of message.
     * @param type messagetype
     * @param sequence_no of tries
     */
    public Message(int type, long sequence_no){
        this.type = type;
        this.sequence_no = sequence_no;
    }

    /**
     *
     * @return String with basic data from this message.
     */
    public String toString(){
        try {
            return this.messageEncode().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error toString/de.teamproject16.pbft.Messages.Message";
        }
    }

    /**
     * Encode the basic data for the network.
     * @return JSONObject data
     * @throws JSONException
     */
    public JSONObject messageEncode () throws JSONException {
        JSONObject data = new JSONObject();
        data.put("type", this.type);
        data.put("sequence_no", this.sequence_no);
        return data;
    }

    /**
     * Create a specific message from the received message.
     * @param data received message
     * @return specific message object
     * @throws JSONException
     */
    public static Message messageConvert(JSONObject data) throws JSONException {
        int type = 0;
        try {
            type = data.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (Types.INIT == type){
            return InitMessage.messageDecipher(data);
        }

        if (Types.LEADER_CHANGE == type){
            return LeaderChangeMessage.messageDecipher(data);
        }
        if (Types.PROPOSE == type){
            return ProposeMessage.messageDecipher(data);
        }
        if (Types.PREVOTE == type){
            return PrevoteMessage.messageDecipher(data);
        }
        if (Types.VOTE == type){
            return VoteMessage.messageDecipher(data);
        }
        return new Message(data.getInt("type"), data.getLong("sequence_no"));
    }
}
