package domRabFSM;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class ENd extends OneShotBehaviour {
    private Agent agent;

    public ENd(Agent agent){
        this.agent = agent;
    }
    @Override
    public void action() {
        System.out.println(agent.getLocalName() + " FINISHED");
    }
}
