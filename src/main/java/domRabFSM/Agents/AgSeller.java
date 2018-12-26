package domRabFSM.Agents;

import domRabFSM.SellerBeh.FSMbehSeller;
import domRabFSM.Price;
import jade.core.Agent;

public class AgSeller extends Agent {
    private String currency = "BUTER";
    private Price prices;

    @Override
    protected void setup() {
        super.setup();
        createSettingForSeller();
        addBehaviour(new FSMbehSeller(this, currency, prices));
    }

    private void createSettingForSeller(){
        if (this.getLocalName().equals("Seller1")){
            prices = new Price(5000, 9900, 0.9);
        }
        else if(this.getLocalName().equals("Seller2")) {
            prices = new Price(5500, 9800, 0.8);
        }
        else if(this.getLocalName().equals("Seller3")) {
            prices = new Price(6000, 9700,0.85);
        }
        else if(this.getLocalName().equals("Seller4")) {
            prices = new Price(4500, 9500,0.75);
        }
        else{
            System.out.println("Danger! Wrong Seller name - " + this.getLocalName());
        }
    }
}
