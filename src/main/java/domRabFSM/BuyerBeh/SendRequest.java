package domRabFSM.BuyerBeh;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

public class SendRequest extends OneShotBehaviour {
    private Agent agent;
    private List<AID> sellers;
    private String currency;

    public SendRequest(Agent agent, String currency, List<AID> sellers) {
        this.agent = agent;
        this.currency = currency;
        this.sellers = sellers;
    }
    @Override
    public void action(){
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        System.out.println(agent.getLocalName() + ": 'I want to buy " + currency + "'");
        msg.setContent(currency);
        for (AID rec: sellers){
            msg.addReceiver(rec);
        }
        msg.setProtocol("request");
        agent.send(msg);
    }
}
