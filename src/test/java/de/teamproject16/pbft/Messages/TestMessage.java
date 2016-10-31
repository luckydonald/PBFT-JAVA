package de.teamproject16.pbft.Messages;

import org.json.JSONObject;
import org.junit.Test;

import static de.teamproject16.pbft.Messages.Types.PROPOSE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by IngridBoldt on 24.10.16.
 */
public class TestMessage {
    @Test
    public void testInitMessageConvert() throws Exception{
        String json = "{\"node\": 1, \"value\": 5.3, \"type\": 1, \"sequence_no\": 3}";
        JSONObject jsonObj = new JSONObject(json);
        Message te = Message.messageConvert(jsonObj);
        System.out.println(te.toString());
        assertThat("InitMessage instance", te, instanceOf(InitMessage.class));
        assertEquals("InitMessage messageEncode()", jsonObj.toString(), te.messageEncode().toString());
    }

    @Test
    public void testProposeMessageConvert() throws Exception{
        String json = "{\"type\": "+ PROPOSE + ", \"sequence_no\": 3, \"node\": 1, " +
                "\"leader\": 2, \"proposal\": 3.5, \"value_store\": [" +
                "{\"node\": 2, \"value\": 0.4, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 1, \"value\": 0.6, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 3, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 4, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}" +
                "]}";
        JSONObject jsonObj = new JSONObject(json);
        Message te = Message.messageConvert(jsonObj);
        System.out.println(te.toString());
        assertThat("ProposeMessage instance", te, instanceOf(ProposeMessage.class));
        assertEquals("ProposeMessage messageEncode()", jsonObj.toString(), te.messageEncode().toString());
    }

    @Test
    public void testPrevoteMessage() throws Exception{
        String json = "{\"node\": 1, \"value\": 5.3, \"type\": 3, \"leader\": 2, \"sequence_no\": 3}";
        JSONObject jsonObj = new JSONObject(json);
        Message te = Message.messageConvert(jsonObj);
        System.out.println(te.toString());
        assertThat("PrevoteMessage instance", te, instanceOf(PrevoteMessage.class));
        assertEquals("PrevoteMessage messageEncode()", jsonObj.toString(), te.messageEncode().toString());
    }

    @Test
    public void testVoteMessage() throws Exception{
        String json = "{\"node\": 1, \"value\": 5.3, \"type\": 4, \"leader\": 2, \"sequence_no\": 3}";
        JSONObject jsonObj = new JSONObject(json);
        Message te = Message.messageConvert(jsonObj);
        System.out.println(te.toString());
        assertThat("VoteMessage instance", te, instanceOf(VoteMessage.class));
        assertEquals("VoteMessage messageEncode()", jsonObj.toString(), te.messageEncode().toString());
    }

}
