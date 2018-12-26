package domRabFSM.SellerBeh;

import domRabFSM.Price;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingForRequest extends Behaviour {
    private Agent agent;
    private String currency;
    private double minPrice;
    private double price;
    private boolean msgSend;

    public WaitingForRequest(Agent agent, String currency, Price prices){
        super();
        this.agent = agent;
        this.currency = currency;
        this.minPrice = prices.getMinPrice();
        this.price = prices.getPrice();
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("request"),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        ACLMessage request = agent.receive(mt);
        if (request!=null){
            System.out.println(agent.getLocalName() + ":'got request for " + currency + " and will send my current price is " + price + " $'");
            ACLMessage answer = request.createReply();
            answer.setContent(price+"");
            answer.setProtocol("trade");
            answer.setPerformative(ACLMessage.INFORM);
            agent.send(answer);
            msgSend = true;
        }else{
            block();
        }
    }

    @Override
    public boolean done() {
        return msgSend;
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
