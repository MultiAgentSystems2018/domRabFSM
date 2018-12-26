package domRabFSM.SellerBeh;

import domRabFSM.Price;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingForAnswer extends TickerBehaviour {
    private Price prices;
    private Agent agent;
    private long time;
    private double bestPrice;
//    private AID bestSeller;
    private double koef;
    private double currentPrice;
    private boolean msgRcvd = false;
    private int result;
    private String currency;

    public WaitingForAnswer(Agent a, long time, Price prices, String currency){
        super(a, time);
        this.agent = a;
        this.time = time;
        this.prices = prices;
        this.koef = prices.getKoef();
        this.currency = currency;

    }

    protected void onTick() {
        msgRcvd = false;
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("trade"), MessageTemplate.or
                (MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE),
                        MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL)),
                        MessageTemplate.MatchPerformative(ACLMessage.REFUSE)));
        ACLMessage resp = agent.receive(mt);
        if(resp!=null && msgRcvd == false){
            msgRcvd = true;
            bestPrice = Double.parseDouble(resp.getContent());
            ACLMessage info = resp.createReply();
            if (resp.getPerformative() == 1){
                System.out.println(agent.getLocalName() + ": I'm the best'");
                currentPrice = bestPrice;
            }
            else if (resp.getPerformative() == 14){
                System.out.println(agent.getLocalName() + ": 'I'm NOT the best. WHYYYYYYYYYYY'");
                currentPrice = bestPrice * koef;
            }
            else if (resp.getPerformative() == 0){
                System.out.println(agent.getLocalName() + ": 'I sold " + currency + " for " + bestPrice + "$'");
                result = 1;
                stop();
            }
            if (currentPrice < prices.getMinPrice()){
                System.out.println(agent.getLocalName() + ": 'I'M DONE WITH TRADING!!!'");
                info.setPerformative(ACLMessage.CANCEL);
                agent.send(info);
                result = 2;
                stop();
            }
            else{
                prices.setPrice(currentPrice);
                info.setPerformative(ACLMessage.INFORM);
                info.setContent(currentPrice+"");
                System.out.println(agent.getLocalName() + ": 'new price = " + currentPrice);
                agent.send(info);
                restart();
            }

        }
        else{
            block();
        }
    }

    @Override
    public void stop() {
        onEnd();
        super.stop();
    }
    @Override
    public int onEnd() {
        return result;
    }
}
