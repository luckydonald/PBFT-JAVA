package de.teamproject16.pbft;

import de.luckydonald.utils.dockerus.DockerusAuto;
import de.luckydonald.utils.dockerus.DockerusDummy;
import de.teamproject16.pbft.Messages.Message;
import de.teamproject16.pbft.Messages.PrevoteMessage;
import de.teamproject16.pbft.Messages.ProposeMessage;
import de.teamproject16.pbft.Network.Receiver;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static de.teamproject16.pbft.Messages.Types.PROPOSE;
import static org.junit.Assert.assertEquals;

/**
 * Created by IngridBoldt on 31.10.16.
 */
public class NormalCaseTest {

    @Test
    public void testVerifyProposal() throws Exception {
        String json = "{\"type\": "+ PROPOSE + ", \"sequence_no\": 3, \"node\": 1, " +
                "\"leader\": 2, \"proposal\": 3.5, \"value_store\": [" +
                "{\"node\": 2, \"value\": 0.4, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 1, \"value\": 0.6, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 3, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}, " +
                "{\"node\": 4, \"value\": 0.3, \"type\": 1, \"sequence_no\": 1}" +
                "]}";
        JSONObject json_obj = new JSONObject(json);
        ProposeMessage te = (ProposeMessage) Message.messageConvert(json_obj);
        assertEquals("VerifyProposal", NormalCase.verifyProposal(te), false);
    }

    @Test
    public void testCheckAgreement() throws Exception {
        ArrayList<Message> store = new ArrayList<>();
        PrevoteMessage pM1 = new PrevoteMessage(3,1,2,0.3);
        PrevoteMessage pM2 = new PrevoteMessage(3,2,2,0.3);
        PrevoteMessage pm3 = new PrevoteMessage(3,3,2,0.2);
        PrevoteMessage pm4 = new PrevoteMessage(3,4,2,0.3);
        store.add(pM1);
        store.add(pM2);
        store.add(pm3);
        store.add(pm4);
        NormalCase normalCase = new NormalCase(new Receiver());
        NormalCase.VerifyAgreementResult lol = normalCase.checkAgreement(store);
        if (DockerusAuto.getInstance() instanceof DockerusDummy) {
            ((DockerusDummy) DockerusAuto.getInstance()).setTotal(4);
        }

        assertEquals("checkAgreement", lol.bool, true);
        assertEquals(0.3, lol.value, 0.0);

        ArrayList<Message> store1 = new ArrayList<>();
        PrevoteMessage pM11 = new PrevoteMessage(3,1,2,0.3);
        PrevoteMessage pM21 = new PrevoteMessage(3,2,2,0.4);
        PrevoteMessage pm31 = new PrevoteMessage(3,3,2,0.2);
        PrevoteMessage pm41 = new PrevoteMessage(3,4,2,0.3);
        store1.add(pM11);
        store1.add(pM21);
        store1.add(pm31);
        store1.add(pm41);
        NormalCase normalCase1 = new NormalCase(new Receiver());
        NormalCase.VerifyAgreementResult lol1 = normalCase1.checkAgreement(store1);
        assertEquals("checkAgreement did agree", lol1.bool, false);
        assertEquals(0.3, lol1.value, 0.0);
    }

}