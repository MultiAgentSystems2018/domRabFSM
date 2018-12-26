package domRabFSM.Agents;

import domRabFSM.BuyerBeh.FSMbehBuyer;
import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class AgBuyer extends Agent {
    private String currency = "BUTER";
    private List<AID> sellers;


    @Override
    protected void setup() {
        super.setup();
        sellers = new ArrayList<AID>();
        sellers.add(new AID("Seller1",false));
        sellers.add(new AID("Seller2",false));
        sellers.add(new AID("Seller3",false));
        sellers.add(new AID("Seller4",false));
        addBehaviour(new FSMbehBuyer(this, currency, sellers));

    }
}
