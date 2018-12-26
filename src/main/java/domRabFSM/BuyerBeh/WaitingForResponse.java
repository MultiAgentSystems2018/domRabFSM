package domRabFSM.BuyerBeh;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

public class WaitingForResponse extends TickerBehaviour {
    private int counter;
    private long time;
    private Agent agent;
    private String currency;
    private List<AID> sellers;
    private AID bestSeller;
    private double bestPrice = 999999999;
    private double price;
    private boolean done;

    public WaitingForResponse(Agent a, String currency, List<AID> sellers, long time){
        super(a, time);
        this.agent = a;
        this.currency = currency;
        this.sellers = sellers;
        counter = sellers.size();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected void onTick() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("trade"), MessageTemplate.or
                (MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchPerformative(ACLMessage.CANCEL)));
        ACLMessage resp = agent.receive(mt);
        if (sellers.size() != 1){
            if(resp!=null){
                counter --;
                if (resp.getPerformative()==ACLMessage.INFORM){
                    System.out.println(agent.getLocalName() + ": 'I've received the price " + resp.getContent() + "$ for " + currency + " from " + resp.getSender().getLocalName() + "'");
                    price = Double.parseDouble(resp.getContent());
                    if (price<bestPrice){
                        bestSeller = resp.getSender();
                        bestPrice = price;

                    }
                }
                else if (resp.getPerformative() == ACLMessage.CANCEL){
                    System.out.println(agent.getLocalName() + ":'"+resp.getSender().getLocalName() + " don't want to trade anymore((('");
                    sellers.remove(resp.getSender());
                }
                if (counter == 0){
                    System.out.println(agent.getLocalName() + ": 'For now, the best price is " + bestPrice + " from " + bestSeller.getLocalName() + "'");
                    System.out.println("---------------------------------------------");
                    ACLMessage agr = resp.createReply();
                    agr.setPerformative(ACLMessage.AGREE);
                    agr.clearAllReceiver();
                    agr.addReceiver(bestSeller);
                    agr.setContent(bestPrice+"");
                    agent.send(agr);
                    ACLMessage ref = resp.createReply();
                    ref.clearAllReceiver();
                    ref.setPerformative(ACLMessage.REFUSE);
                    sellers.remove(bestSeller);
                    for (AID sel:sellers){
                        ref.addReceiver(sel);
                    }
                    sellers.add(bestSeller);
                    ref.setContent(bestPrice+"");
                    agent.send(ref);
//                    done = true;
                    counter = sellers.size();

                }
            }
            else{
                block();
            }
        }else{
            System.out.println(agent.getLocalName() + ":'In the end, I will buy " + currency + " from " + bestSeller.getLocalName() + "'");
            ACLMessage accept = resp.createReply();
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            accept.setContent(bestPrice+"");
            accept.addReceiver(bestSeller);
            agent.send(accept);
            stop();
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
