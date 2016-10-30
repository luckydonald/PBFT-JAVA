package de.teamproject16.pbft.Messages;

import org.json.JSONObject;
import static de.teamproject16.pbft.Messages.Types.LEADER_CHANGE;

/**
 * Created by IngridBoldt on 04.10.16.
 */
public class LeaderChangeMessage extends Message {

    Number node_num;
    Number leader;
    Number P;

    public LeaderChangeMessage(long sequence_no, Number node_num, Number leader, Number P) {
        super(LEADER_CHANGE, sequence_no);
        this.node_num = node_num;
        this.leader = leader;
        this.P = P;
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