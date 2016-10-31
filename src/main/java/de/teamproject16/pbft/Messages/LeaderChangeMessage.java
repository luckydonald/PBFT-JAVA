package de.teamproject16.pbft.Messages;

import org.json.JSONObject;

import java.util.ArrayList;

import static de.teamproject16.pbft.Messages.Types.LEADER_CHANGE;

/**
 * Created by IngridBoldt on 04.10.16.
 */
public class LeaderChangeMessage extends Message {

    public int node;
    public int leader;
    public ArrayList<PrevoteMessage> prevoteList;

    public LeaderChangeMessage(long sequence_no, int node, int leader, ArrayList<PrevoteMessage> prevoteList) {
        super(LEADER_CHANGE, sequence_no);
        this.node = node;
        this.leader = leader;
        this.prevoteList = prevoteList;
    }

    public static LeaderChangeMessage messageDecipher(JSONObject data) {
        return null;
    }
}
/**
class de.teamproject16.pbft.Messages.LeaderChangeMessage(de.teamproject16.pbft.Messages.Message):
        def __init__(self, sequence_no, node_num, leader, P):
        raise NotImplementedError("lel")
        super(de.teamproject16.pbft.Messages.LeaderChangeMessage, self).__init__(LEADER_CHANGE, sequence_no)
        # end def

@staticmethod
def from_dict(data):
        raise NotImplementedError("lel")
        kwargs = {
        "type": data["type"],
        "sequence_no": data["sequence_no"],
        }
        return de.teamproject16.pbft.Messages.LeaderChangeMessage(**kwargs)
        # end def

        def to_dict(self):
        raise NotImplementedError("lel")
        return {
        "type": self.type,
        "sequence_no": self.sequence_no,
        }**/